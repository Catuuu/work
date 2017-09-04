package com.framework.dao;

import com.framework.mapping.BaseMapping;
import com.framework.mapping.PageParam;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.dao.DataAccessException;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * 数据访问对象的接口
 * User: chenbin
 * Date: 12-12-25
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
public interface SqlDao {

    //获得数据库连接对象
    public Connection getConnection() throws Exception;
    //获得连接池
    public BasicDataSource getDataSource() throws Exception;

    //分页查询Dao
    public <T> List<T> queryPageList(PageParam pageParam) throws Exception;

    /**
     * 获得记录集
     *
     * @param sqlID  sql语句id
     * @param object 查询参数
     * @return List
     * @throws DataAccessException 数据库访问异常
     */
    public <T> List<T> getRecordList(String sqlID, Object object) throws DataAccessException;

    /**
     * 获得记录集（无参数）
     *
     * @param sqlID sql语句id
     * @return List
     * @throws DataAccessException 数据库访问异常
     */
    public <T> List<T> getRecordList(String sqlID) throws DataAccessException;

    /**
     * 获得记录集
     *
     * @param baseMapping 查询参数
     * @return
     * @throws DataAccessException 数据库访问异常
     */
    public <T> List<T> getRecordList(BaseMapping baseMapping) throws DataAccessException;

    /**
     * 获得单个记录
     *
     * @param sqlID  sql语句id
     * @param object 查询参数
     * @return Object
     * @throws org.springframework.dao.DataAccessException
     *          数据库访问异常
     */
    public <T> T getRecord(String sqlID, Object object) throws DataAccessException;

    /**
     * 获得单个记录（无参数）
     *
     * @param sqlID sql语句id
     * @return Object
     * @throws DataAccessException 数据库访问异常
     */
    public <T> T getRecord(String sqlID) throws DataAccessException;

    /**
     * 获得单个记录
     *
     * @param baseMapping 查询参数
     * @return
     * @throws DataAccessException 数据库访问异常
     */
    public <T> T getRecord(BaseMapping baseMapping) throws DataAccessException;

    /**
     * 以Map的形式返回结果集
     *
     * @param sqlID       sql语句id
     * @param object      查询参数
     * @param keyProperty map的key值，对应于结果集中的字段
     * @return Map
     * @throws DataAccessException
     */
    public Map getRecord(String sqlID, Object object, String keyProperty) throws DataAccessException;

    /**
     * 以Map的形式返回结果集
     *
     * @param sqlID       sql语句id
     * @param keyProperty map的key值，对应于结果集中的字段
     * @return Map
     * @throws DataAccessException
     */
    public Map getRecord(String sqlID, String keyProperty) throws DataAccessException;

    /**
     * 插入记录
     *
     * @param sqlID  sql语句id
     * @param object 插入对象
     * @return int 插入影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int insertRecord(String sqlID, Object object) throws DataAccessException;

    /**
     * 插入记录(无参数)
     *
     * @param sqlID sql语句id
     * @return int 插入影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int insertRecord(String sqlID) throws DataAccessException;

    /**
     * 插入记录
     *
     * @param baseMapping sql语句id
     * @return int 插入影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int insertRecord(BaseMapping baseMapping) throws DataAccessException;

    /**
     * 更新记录
     *
     * @param sqlID  sql语句id
     * @param object 更新对象
     * @return int 更新影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int updateRecord(String sqlID, Object object) throws DataAccessException;

    /**
     * 更新记录(无参数)
     *
     * @param sqlID sql语句id
     * @return int 更新影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int updateRecord(String sqlID) throws DataAccessException;


    /**
     * 更新记录
     *
     * @param baseMapping sql语句id
     * @return int 更新影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int updateRecord(BaseMapping baseMapping) throws DataAccessException;

    /**
     * 删除记录
     *
     * @param sqlID  sql语句id
     * @param object 删除对象
     * @return int 删除影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int deleteRecord(String sqlID, Object object) throws DataAccessException;

    /**
     * 删除记录(无参数)
     *
     * @param sqlID sql语句id
     * @return int 删除影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int deleteRecord(String sqlID) throws DataAccessException;

    /**
     * 删除记录
     *
     * @param baseMapping sql语句id
     * @return int 删除影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int deleteRecord(BaseMapping baseMapping) throws DataAccessException;


}
