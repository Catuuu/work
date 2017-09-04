package com.framework.inteceptor;

import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.PageParam;
import com.framework.mapping.system.SqlLog;
import com.framework.system.PageService;
import com.framework.system.PageServiceMySqlImpl;
import com.framework.system.PageServiceOracleImpl;
import com.framework.system.PageServiceSQLServerImpl;
import com.framework.util.BeanUtil;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Mybaits 分页拦截器
 * User: Administrator
 * Date: 12-12-26
 * Time: 下午12:09
 * To change this template use File | Settings | File Templates.
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class MybatisPaginationInterceptor implements Interceptor {
    private Log logger = LogFactory.getLog(getClass());
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object intercept(Invocation ivk) throws Throwable {
        long startTime = System.currentTimeMillis();
        StringBuffer sqlStr = new StringBuffer("");

        StatementHandler statementHandler = (StatementHandler) ivk.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);
        ParameterHandler parameterHandler = statementHandler.getParameterHandler();
        BoundSql boundSql = statementHandler.getBoundSql();
        //进行分页查询
        if (parameterHandler.getParameterObject() instanceof PageParam) {
            Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

            String sql = boundSql.getSql();

            PageParam pageParam = (PageParam) parameterHandler.getParameterObject();

            BoundSql countBS;
            PreparedStatement countStmt;
            Connection connection = (Connection) ivk.getArgs()[0];
            String connStr = connection.toString();
            //判断是否提供CountSql属性
            if (BeanUtil.isNullAndEmpty(pageParam.getPageInfo().getCountSql())) {
                String countSql = "select count(0) from (" + sql + ")  tmp_count"; // 记录统计
                countStmt = connection.prepareStatement(countSql);
                countBS = new BoundSql(configuration, countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
                setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            } else {
                countBS = configuration.getMappedStatement(pageParam.getPageInfo().getCountSql()).getBoundSql(boundSql.getParameterObject());
                countStmt = connection.prepareStatement(countBS.getSql());
                setParameters(countStmt, mappedStatement, countBS, countBS.getParameterObject());
            }

            sqlStr.append(printSql(metaStatementHandler, countBS));
            ResultSet rs = countStmt.executeQuery();
            if (rs.next()) {
                pageParam.getPageInfo().setRecordCount(rs.getLong(1));
            }
            rs.close();
            countStmt.close();

            PageService pageService = new PageServiceSQLServerImpl();

            metaStatementHandler.setValue("delegate.boundSql.sql", pageService.getLimitString(sql, pageParam));
            metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
            metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        }
        Object retVal = ivk.proceed();

        sqlStr.append(printSql(metaStatementHandler, boundSql));
        try {
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
            String mappedId = (String) metaStatementHandler.getValue("delegate.mappedStatement.id");

            SqlLog sqlLog = new SqlLog();
            sqlLog.setSqlLogKey(StringUtil.getPrimaryKey());
            sqlLog.setMappedId(mappedId);
            sqlLog.setStartTime(new Date(startTime));
            sqlLog.setEndTime(new java.sql.Date(endTime));
            sqlLog.setExecuteTime(executeTime);
            sqlLog.setSqlStr(sqlStr.toString());

            if (WebUtil.getRequest() != null && WebUtil.getRequest("request_primary_key")!=null) {
                sqlLog.setRequestLogKey(WebUtil.getRequest("request_primary_key"));
            }

            //发送日志信息
            WebUtil.getJmsTemplate().send("sql.log", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(JSONObject.toJSONString(sqlLog));
                }
            });
        }catch (Exception e){
        }

        return retVal;
    }


    /* (non-Javadoc)
    * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
    */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /* (non-Javadoc)
    * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
    */
    @Override
    public void setProperties(Properties arg0) {

    }

    //Sqlr日志打印
    private String printSql(MetaObject metaStatementHandler, BoundSql boundSql) {
        String sqlResult = "";
        String mappedId = (String) metaStatementHandler.getValue("delegate.mappedStatement.id");
        if (logger.isInfoEnabled()) {
            logger.info(mappedId + ": \n" + boundSql.getSql());
        }
        sqlResult += mappedId + ": \n" + boundSql.getSql();

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            StringBuffer param = new StringBuffer("");
            String value = "";
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            Object parameterObject = boundSql.getParameterObject();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object obj;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        obj = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        obj = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        obj = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
                        obj = boundSql.getAdditionalParameter(prop.getName());
                        if (obj != null) {
                            obj = configuration.newMetaObject(obj).getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        obj = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    if (obj == null) {
                        value = "null";
                    } else if (obj instanceof java.util.Date) {
                        value = dateFormat.format(obj);
                    } else {
                        value = obj.toString();
                    }
                    param.append("参数" + (i + 1) + ":" + value + "\n");
                }
            }

            sqlResult += param.toString();
            if (logger.isInfoEnabled()) {
                logger.info(param);
            }
        }
        return sqlResult;
    }

    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.
     * DefaultParameterHandler
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps,
                               MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry
                            .hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName
                            .startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value)
                                    .getValue(
                                            propertyName.substring(prop
                                                    .getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject
                                .getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException(
                                "There was no TypeHandler found for parameter "
                                        + propertyName + " of statement "
                                        + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value,
                            parameterMapping.getJdbcType());
                }
            }
        }
    }
}
