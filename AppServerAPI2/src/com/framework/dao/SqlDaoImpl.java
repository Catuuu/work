package com.framework.dao;

import com.framework.annotation.resolution.RsTableFieldResolving;
import com.framework.annotation.resolution.RsTableResolving;
import com.framework.mapping.BaseMapping;
import com.framework.mapping.PageParam;
import com.framework.util.BeanUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据访问对象的实现
 * User: Administrator
 * Date: 12-12-25
 * Time: 上午11:15
 * To change this template use File | Settings | File Templates.
 */
@Repository("sqlDao")
public class SqlDaoImpl implements SqlDao {
    private Log logger = LogFactory.getLog(getClass());

    @Resource(name = "sqlSessionTemplate")
    SqlSessionTemplate sqlSession; //mybatais会话

    @Resource(name = "dataSource")
    private BasicDataSource dataSource;//数据源

    public SqlSessionTemplate getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    public BasicDataSource getDataSource() throws Exception {
        return dataSource;
    }


    /**
     * 分页查询
     *
     * @param pageParam
     * @return
     * @throws Exception
     */
    public <T> List<T> queryPageList(PageParam pageParam) throws Exception {
        return sqlSession.selectList(pageParam.getPageInfo().getRecordSql(), pageParam);
    }


    /**
     * 获得记录集
     *
     * @param sqlID  sql语句id
     * @param object 查询参数
     * @return List
     * @throws DataAccessException 数据库访问异常
     */
    public <T> List<T> getRecordList(String sqlID, Object object) throws DataAccessException {
        return sqlSession.selectList(sqlID, object);
    }

    /**
     * 获得记录集（无参数）
     *
     * @param sqlID sql语句id
     * @return List
     * @throws org.springframework.dao.DataAccessException
     *          数据库访问异常
     */
    public <T> List<T> getRecordList(String sqlID) throws DataAccessException {
        return sqlSession.selectList(sqlID);
    }

    /**
     * 获得记录集
     *
     * @param baseMapping 查询参数
     * @return
     * @throws DataAccessException 数据库访问异常
     */
    public List<BaseMapping> getRecordList(BaseMapping baseMapping) throws DataAccessException {
        //获取表信息
        Map tab = RsTableResolving.getTable(baseMapping);
        if (tab.isEmpty())
            return null;
        //获取表字段信息
        Map field = RsTableFieldResolving.getTableField(baseMapping);
        if (field.isEmpty())
            return null;

        RsTableFieldResolving.orderHandle(field);
        tab.putAll(field);

        List<Map> lstMap = sqlSession.selectList("baseSqlMap.getRecord", tab);
        if (BeanUtil.isNullAndEmpty(lstMap)) return null;
        else {
            List<BaseMapping> lstBaseMapping = new ArrayList<BaseMapping>();
            for (Map m : lstMap) {
                lstBaseMapping.add(BeanUtil.createBean(BeanUtil.mapKeyToLowerCase(m), baseMapping.getClass()));
            }
            return lstBaseMapping;
        }
    }

    /**
     * 获得单个记录
     *
     * @param sqlID  sql语句id
     * @param object 查询参数
     * @return Object
     * @throws org.springframework.dao.DataAccessException
     *          数据库访问异常
     */

    public <T> T getRecord(String sqlID, Object object) throws DataAccessException {
        return (T) sqlSession.selectOne(sqlID, object);
    }

    /**
     * 获得单个记录（无参数）
     *
     * @param sqlID sql语句id
     * @return Object
     * @throws DataAccessException 数据库访问异常
     */
    public <T> T getRecord(String sqlID) throws DataAccessException {
        return (T) sqlSession.selectOne(sqlID);
    }

    /**
     * 获得单个记录
     *
     * @param baseMapping 查询参数
     * @return
     * @throws DataAccessException 数据库访问异常
     */
    public BaseMapping getRecord(BaseMapping baseMapping) throws DataAccessException {
        //获取表信息
        Map tab = RsTableResolving.getTable(baseMapping);
        if (tab.isEmpty())
            return null;
        //获取表字段信息
        Map field = RsTableFieldResolving.getTableField(baseMapping);
        if (field.isEmpty())
            return null;
        if (!RsTableFieldResolving.conditionHandle(field))
            return null;

        tab.putAll(field);

        Map resultMap = sqlSession.selectOne("baseSqlMap.getRecord", tab);
        if (null == resultMap) return null;
        else return BeanUtil.createBean(BeanUtil.mapKeyToLowerCase(resultMap), baseMapping.getClass());
    }

    /**
     * 以Map的形式返回结果集
     *
     * @param sqlID       sql语句id
     * @param object      查询参数
     * @param keyProperty map的key值，对应于结果集中的字段
     * @return Map
     * @throws DataAccessException
     */
    public Map getRecord(String sqlID, Object object, String keyProperty) throws DataAccessException {
        return sqlSession.selectMap(sqlID, object, keyProperty);
    }

    /**
     * 以Map的形式返回结果集（无参数）
     *
     * @param sqlID       sql语句id
     * @param keyProperty map的key值，对应于结果集中的字段
     * @return Map
     * @throws DataAccessException
     */
    public Map getRecord(String sqlID, String keyProperty) throws DataAccessException {
        return sqlSession.selectMap(sqlID, keyProperty);
    }

    /**
     * 插入记录
     *
     * @param sqlID  sql语句id
     * @param object 插入对象
     * @return int 插入影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int insertRecord(String sqlID, Object object) throws DataAccessException {
        return sqlSession.insert(sqlID, object);
    }

    /**
     * 插入记录(无参数)
     *
     * @param sqlID sql语句id
     * @return int 插入影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int insertRecord(String sqlID) throws DataAccessException {
        return sqlSession.insert(sqlID);
    }

    /**
     * 插入记录
     *
     * @param baseMapping sql语句id
     * @return int 插入影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int insertRecord(BaseMapping baseMapping) throws DataAccessException {
        //获取表信息
        Map tab = RsTableResolving.getTable(baseMapping);
        if (tab.isEmpty())
            return 0;
        //获取表字段信息
        Map field = RsTableFieldResolving.getTableField(baseMapping);
        if (field.isEmpty())
            return 0;

        if (!RsTableFieldResolving.primaryHandle(field, baseMapping))
            return 0;

        tab.putAll(field);
        return sqlSession.insert("baseSqlMap.insertRecord", tab);
    }

    /**
     * 更新记录
     *
     * @param sqlID  sql语句id
     * @param object 更新对象
     * @return int 更新影响的记录数
     * @throws DataAccessException 数据库访问异常
     */

    public int updateRecord(String sqlID, Object object) throws DataAccessException {
        return sqlSession.update(sqlID, object);
    }

    /**
     * 更新记录(无参数)
     *
     * @param sqlID sql语句id
     * @return int 更新影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int updateRecord(String sqlID) throws DataAccessException {
        return sqlSession.update(sqlID);
    }

    /**
     * 更新记录
     *
     * @param baseMapping sql语句id
     * @return int 更新影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int updateRecord(BaseMapping baseMapping) throws DataAccessException {
        //获取表信息
        Map tab = RsTableResolving.getTable(baseMapping);
        if (tab.isEmpty())
            return 0;
        //获取表字段信息
        Map field = RsTableFieldResolving.getTableField(baseMapping);
        if (field.isEmpty())
            return 0;

        List<Map> paramsList = (List<Map>) field.get("paramsList");
        if (paramsList.isEmpty())
            return 0;
        if (!RsTableFieldResolving.conditionHandle(field))
            return 0;

        tab.putAll(field);
        return sqlSession.update("baseSqlMap.updateRecord", tab);
    }

    /**
     * 删除记录
     *
     * @param sqlID  sql语句id
     * @param object 删除对象
     * @return int 删除影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int deleteRecord(String sqlID, Object object) throws DataAccessException {
        return sqlSession.delete(sqlID, object);
    }

    /**
     * 删除记录(无参数)
     *
     * @param sqlID sql语句id
     * @return int 删除影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int deleteRecord(String sqlID) throws DataAccessException {
        return sqlSession.delete(sqlID);
    }

    /**
     * 删除记录 whereList条件优先 如果没有值则按主键
     *
     * @param baseMapping sql语句id
     * @return int 删除影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int deleteRecord(BaseMapping baseMapping) throws DataAccessException {
        //获取表信息
        Map tab = RsTableResolving.getTable(baseMapping);
        if (tab.isEmpty())
            return 0;
        //获取表字段信息
        Map field = RsTableFieldResolving.getTableField(baseMapping);
        if (field.isEmpty())
            return 0;

        if (!RsTableFieldResolving.conditionHandle(field))
            return 0;

        tab.putAll(field);
        return sqlSession.delete("baseSqlMap.deleteRecord", tab);
    }
}
