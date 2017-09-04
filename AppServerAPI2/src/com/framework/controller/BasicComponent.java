package com.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.framework.dao.SqlDao;
import com.framework.mapping.BaseMapping;
import com.framework.mapping.system.CdsMember;
import com.framework.mapping.system.SysFiles;
import com.framework.system.SystemConfig;
import com.framework.system.SystemDictionary;
import com.framework.util.BeanUtil;
import com.framework.util.WebUtil;

import com.opensdk.eleme2.api.base.BaseNopService;
import com.opensdk.eleme2.config.Config;
import com.opensdk.eleme2.oauth.OAuthClient;
import com.opensdk.eleme2.oauth.response.Token;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.util.FileUtil;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 基本控制器，所有控制器必须继承此类.
 * User: chenbin
 * Date: 12-12-24
 * Time: 下午5:22
 * To change this template use File | Settings | File Templates.
 */

public abstract class BasicComponent {

    @Resource(name = "sqlDao")
    protected SqlDao sqlDao;  //数据库访问操作对象


    @Resource(name = "jmsQueueTemplate")
    protected JmsTemplate jmsTemplate;

    @Resource(name = "jmsTopicTemplate")
    protected JmsTemplate jmsTopicTemplate;


    @Resource(name = "redisTemplate")
    protected RedisTemplate redisTemplate;

    protected final Log logger = LogFactory.getLog(getClass());  //日志访问对象

    /**
     * 文件保存
     *
     * @param files
     * @param transaction
     * @throws Exception
     */
    public void fileSave(String files, String transaction) throws Exception {
        if (BeanUtil.notNullAndEmpty(files) && BeanUtil.notNullAndEmpty(transaction)) {
            String[] fileArg = files.split(";");
            String tempDir = WebUtil.getServletContext().getRealPath("/WEB-INF/tempDir/");
            String fileDirPath = SystemDictionary.getInstance().getProperty("fileDirPath");
            for (String filename : fileArg) {
                SysFiles sysFiles = WebUtil.getSession(filename);
                File fileForm = new File(tempDir + "/" + filename);
                File fileTo = new File(fileDirPath + "/" + filename + sysFiles.getSf_type());
                FileUtil.copyFile(fileForm, fileTo);
                fileForm.delete();
                sysFiles.setSf_date(new Date());
                sysFiles.setSf_transaction(transaction);
                sysFiles.addParamFields();
                sqlDao.insertRecord(sysFiles);
            }
        }
    }

    /**
     * 文件修改
     *
     * @param files
     * @param transaction
     * @throws Exception
     */
    public void fileUpdate(String files, String transaction) throws Exception {
        List<SysFiles> filesList = fileList(transaction);
        if (!filesList.isEmpty()) {
            for (SysFiles sf : filesList) {
                //包含不做处理，同时从files中删除
                if (files.contains(sf.getSf_id()))
                    files = files.replace(sf.getSf_id() + ";", "");
                    //不包含则删除
                else
                    fileDelete(sf);
            }
            fileSave(files, transaction);
        }
    }

    /**
     * 通过业务键值获取文件列信息
     *
     * @param transaction
     * @return
     * @throws Exception
     */
    public List<SysFiles> fileList(String transaction) throws Exception {
        List<SysFiles> filesList = new ArrayList<SysFiles>();
        if (BeanUtil.notNullAndEmpty(transaction)) {
            SysFiles sysFiles = new SysFiles();
            sysFiles.setSf_transaction(transaction);
            sysFiles.addConditionField("sf_transaction");
            filesList = sqlDao.getRecordList(sysFiles);
        }
        return filesList;
    }

    /**
     * 获取上传的文件对象集
     *
     * @param files
     * @return
     * @throws Exception
     */
    public List<File> fileLoad(String files) throws Exception {
        List<File> fileList = new ArrayList<File>();
        if (BeanUtil.notNullAndEmpty(files)) {
            String[] fileArg = files.split(";");
            String tempDir = WebUtil.getServletContext().getRealPath("/WEB-INF/tempDir/");
            for (String filename : fileArg) {
                File file = new File(tempDir + "/" + filename);
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 文件删除
     *
     * @param sf
     * @throws Exception
     */
    private void fileDelete(SysFiles sf) throws Exception {
        //从数据库中删除
        sqlDao.deleteRecord(sf);
        //从文件列中删除文件
        String fileDirPath = SystemDictionary.getInstance().getProperty("fileDirPath") + "/" + sf.getSf_id() + sf.getSf_type();
        File file = new File(fileDirPath);
        file.delete();
    }

    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }


    /**
     * Redis 添加锁
     *
     * @param key
     * @return
     */
    protected boolean lock(String key) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize("1");
                    if (connection.setNX(keyByte, name)) {
                        connection.expire(keyByte, 10);
                        return true;
                    }
                    return false;
                }
            });
            return result;
        } catch (Exception e) {
            return lock(key);
        }

    }

    /**
     * Redis 删除锁
     *
     * @param key
     */
    public void unlock(String key) {
        this.deleteRedis(key);
    }


    /**
     * Redis 添加对象
     *
     * @param key
     * @param content
     * @return
     */
    protected boolean addRedis(String key, String content) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(content);

                    return connection.setNX(keyByte, name);
                }
            });

            return result;
        } catch (Exception e) {
            return addRedis(key, content);
        }
    }

    /**
     * Redis 添加对象
     *
     * @param key
     * @param content
     * @param db      对应的库名 0-15
     * @return
     */
    protected boolean addRedis(int db, String key, String content) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(content);
                    return connection.setNX(keyByte, name);
                }
            });

            return result;
        } catch (Exception e) {
            return addRedis(db, key, content);
        }
    }

    /**
     * Redis 添加对象
     *
     * @param key
     * @param content
     * @param liveTime 单位秒
     * @return
     */
    protected boolean addRedis(String key, String content, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(content);
                    if (connection.setNX(keyByte, name)) {
                        connection.expire(keyByte, liveTime);
                        return true;
                    }
                    return false;
                }
            });

            return result;
        } catch (Exception e) {
            return addRedis(key, content, liveTime);
        }
    }

    /**
     * Redis 添加对象
     *
     * @param key
     * @param content
     * @param liveTime 单位秒
     * @param db       对应的库名 0-15
     * @return
     */
    protected boolean addRedis(int db, String key, String content, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(content);
                    if (connection.setNX(keyByte, name)) {
                        connection.expire(keyByte, liveTime);
                        return true;
                    }
                    return false;
                }
            });

            return result;
        } catch (Exception e) {
            return addRedis(db, key, content, liveTime);
        }

    }


    /**
     * Redis 添加对象
     *
     * @param key
     * @param baseMapping
     * @return
     */
    protected boolean addRedis(String key, BaseMapping baseMapping) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(baseMapping));
                    return connection.setNX(keyByte, name);
                }
            });
            return result;
        } catch (Exception e) {
            return addRedis(key, baseMapping);
        }
    }

    /**
     * Redis 添加对象
     *
     * @param key
     * @param baseMapping
     * @return
     */
    protected boolean addRedis(int db, String key, BaseMapping baseMapping) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(baseMapping));
                    return connection.setNX(keyByte, name);
                }
            });
            return result;
        } catch (Exception e) {
            return addRedis(db, key, baseMapping);
        }
    }

    /**
     * Redis 添加对象
     *
     * @param key
     * @param baseMapping
     * @return
     */
    protected boolean addRedis(String key, BaseMapping baseMapping, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(baseMapping));
                    if (connection.setNX(keyByte, name)) {
                        connection.expire(keyByte, liveTime);
                        return true;
                    }
                    return false;
                }
            });
            return result;
        } catch (Exception e) {
            return addRedis(key, baseMapping, liveTime);
        }
    }

    /**
     * Redis 添加对象
     *
     * @param key
     * @param baseMapping
     * @return
     */
    protected boolean addRedis(int db, String key, BaseMapping baseMapping, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(baseMapping));

                    if (connection.setNX(keyByte, name)) {
                        connection.expire(keyByte, liveTime);
                        return true;
                    }
                    return false;
                }
            });
            return result;
        } catch (Exception e) {
            return addRedis(db, key, baseMapping, liveTime);
        }
    }


    /**
     * Redis 添加对象
     *
     * @param key
     * @param map
     * @return
     */
    protected boolean addRedis(String key, Map map) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(map));
                    return connection.setNX(keyByte, name);
                }
            });
            return result;
        } catch (Exception e) {
            return addRedis(key, map);
        }
    }

    /**
     * Redis 添加对象
     *
     * @param key
     * @param map
     * @return
     */
    protected boolean addRedis(int db, String key, Map map) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(map));

                    return connection.setNX(keyByte, name);
                }
            });
            return result;
        } catch (Exception e) {
            return addRedis(db, key, map);
        }
    }

    /**
     * Redis 添加对象
     *
     * @param key
     * @param map
     * @return
     */
    protected boolean addRedis(String key, Map map, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(map));
                    if (connection.setNX(keyByte, name)) {
                        connection.expire(keyByte, liveTime);
                        return true;
                    }
                    return false;
                }
            });
            return result;
        } catch (Exception e) {
            return addRedis(key, map, liveTime);
        }
    }

    /**
     * Redis 添加对象
     *
     * @param key
     * @param map
     * @return
     */
    protected boolean addRedis(int db, String key, Map map, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(map));

                    if (connection.setNX(keyByte, name)) {
                        connection.expire(keyByte, liveTime);
                        return true;
                    }
                    return false;
                }
            });
            return result;
        } catch (Exception e) {
            return addRedis(db, key, map, liveTime);
        }

    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param content
     * @return
     */
    public boolean updateRedis(String key, String content) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(content);
                    connection.set(keyByte, name);
                    return true;
                }
            });
            return result;
        } catch (Exception e) {
            return updateRedis(key, content);
        }
    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param content
     * @return
     */
    public boolean updateRedis(int db, String key, String content) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(content);
                    connection.set(keyByte, name);
                    return true;
                }
            });
            return result;
        } catch (Exception e) {
            return updateRedis(db, key, content);
        }

    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param content
     * @return
     */
    public boolean updateRedis(String key, String content, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(content);
                    connection.set(keyByte, name);
                    connection.expire(keyByte, liveTime);
                    return true;
                }
            });
            return result;
        } catch (Exception e) {
            return updateRedis(key, content, liveTime);
        }
    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param content
     * @return
     */
    public boolean updateRedis(int db, String key, String content, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(content);

                    connection.set(keyByte, name);
                    connection.expire(keyByte, liveTime);
                    return true;
                }
            });
            return result;
        } catch (Exception e) {
            return updateRedis(db, key, content, liveTime);
        }
    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param baseMapping
     * @return
     */
    public boolean updateRedis(String key, BaseMapping baseMapping) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(baseMapping));
                    connection.set(keyByte, name);
                    return true;
                }
            });
            return result;
        }catch (Exception e){
            return updateRedis(key,baseMapping);
        }
    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param baseMapping
     * @return
     */
    public boolean updateRedis(int db, String key, BaseMapping baseMapping) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(baseMapping));

                    connection.set(keyByte, name);
                    return true;
                }
            });
            return result;
        }catch (Exception e){
            return updateRedis(db,key,baseMapping);
        }
    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param baseMapping
     * @return
     */
    public boolean updateRedis(String key, BaseMapping baseMapping, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(baseMapping));
                    connection.set(keyByte, name);
                    connection.expire(keyByte, liveTime);
                    return true;
                }
            });
            return result;
        }catch (Exception e){
           return updateRedis(key,baseMapping,liveTime);
        }
    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param baseMapping
     * @return
     */
    public boolean updateRedis(int db, String key, BaseMapping baseMapping, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(baseMapping));

                    connection.set(keyByte, name);
                    connection.expire(keyByte, liveTime);
                    return true;
                }
            });
            return result;
        }catch (Exception e){
            return updateRedis(db,key,baseMapping,liveTime);
        }
    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param map
     * @return
     */
    public boolean updateRedis(String key, Map map) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(map));
                    connection.set(keyByte, name);
                    return true;
                }
            });
            return result;
        }catch (Exception e){
            return updateRedis(key,map);
        }
    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param map
     * @return
     */
    public boolean updateRedis(int db, String key, Map map) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(map));

                    connection.set(keyByte, name);
                    return true;
                }
            });
            return result;
        }catch (Exception e){
            return updateRedis(db,key,map);
        }
    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param map
     * @return
     */
    public boolean updateRedis(String key, Map map, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(map));
                    connection.set(keyByte, name);
                    connection.expire(keyByte, liveTime);
                    return true;
                }
            });
            return result;
        }catch (Exception e){
            return updateRedis(key,map,liveTime);
        }
    }

    /**
     * Redis 修改对象
     *
     * @param key
     * @param map
     * @return
     */
    public boolean updateRedis(int db, String key, Map map, final long liveTime) {
        try {
            boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);
                    byte[] name = serializer.serialize(BeanUtil.toJsonString(map));

                    connection.set(keyByte, name);
                    connection.expire(keyByte, liveTime);
                    return true;
                }
            });
            return result;
        }catch (Exception e){
            return updateRedis(db,key,map,liveTime);
        }
    }

    /**
     * 删除对象 ,依赖key
     */
    public void deleteRedis(String key) {
        try {
            List<String> list = new ArrayList<String>();
            list.add(key);
            deleteRedis(list);
        } catch (Exception e) {
            deleteRedis(key);
        }
    }

    /**
     * 删除对象 ,依赖key
     */
    public void deleteRedis(int db, String key) {
        try {
            List<String> list = new ArrayList<String>();
            list.add(key);
            deleteRedis(db, list);
        } catch (Exception e) {
            deleteRedis(db, key);
        }
    }

    /**
     * 删除集合 ,依赖key集合
     */
    public void deleteRedis(List<String> keys) {
        try {
            redisTemplate.delete(keys);
        } catch (Exception e) {
            deleteRedis(keys);
        }
    }

    /**
     * 删除集合 ,依赖key集合
     */
    public void deleteRedis(int db, List<String> keys) {
        try {
            redisTemplate.getConnectionFactory().getConnection().select(db);
            redisTemplate.delete(keys);
        }catch (Exception e){
            deleteRedis(db,keys);
        }
    }


    /**
     * 根据key获取对象
     */
    public String getRedisString(final String keyId) {
        try {
            String result = (String) redisTemplate.execute(new RedisCallback<String>() {
                public String doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] key = serializer.serialize(keyId);
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    String content = serializer.deserialize(value);
                    return content;
                }
            });
            return result;
        }catch (Exception e){
            return getRedisString(keyId);
        }
    }

    /**
     * 根据key获取对象
     */
    public String getRedisString(int db, final String keyId) {
        try {
            String result = (String) redisTemplate.execute(new RedisCallback<String>() {
                public String doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] key = serializer.serialize(keyId);
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    String content = serializer.deserialize(value);
                    return content;
                }
            });
            return result;
        } catch (Exception e) {
            return getRedisString(db, keyId);
        }
    }

    /**
     * 根据key获取对象
     */
    public <T> T getRedisBean(final String keyId, Class<T> cls) {
        try {
            T result = (T) redisTemplate.execute(new RedisCallback<T>() {
                public T doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] key = serializer.serialize(keyId);
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    String content = serializer.deserialize(value);
                    T contentMap = BeanUtil.createBean(content, cls);
                    return contentMap;
                }
            });
            return result;
        } catch (Exception e) {
            return getRedisBean(keyId, cls);
        }
    }

    /**
     * 根据key获取对象
     */
    public <T> T getRedisBean(final String keyId, Class<T> cls, int db) {
        try {
            T result = (T) redisTemplate.execute(new RedisCallback<T>() {
                public T doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] key = serializer.serialize(keyId);
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    String content = serializer.deserialize(value);
                    T contentMap = BeanUtil.createBean(content, cls);
                    return contentMap;
                }
            });
            return result;
        }catch (Exception e){
            return getRedisBean(keyId,cls,db);
        }
    }

    /**
     * 根据key获取对象
     */
    public Map getRedisMap(final String keyId) {
        try {
            Map result = (Map) redisTemplate.execute(new RedisCallback<Map>() {
                public Map doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] key = serializer.serialize(keyId);
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    String content = serializer.deserialize(value);
                    Map contentMap = BeanUtil.createBean(content, HashMap.class);
                    return contentMap;
                }
            });
            return result;
        }catch (Exception e){
            return getRedisMap(keyId);
        }
    }

    /**
     * 根据key获取对象
     */
    public Map getRedisMap(int db, final String keyId) {
        try {
            Map result = (Map) redisTemplate.execute(new RedisCallback<Map>() {
                public Map doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.select(db);
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] key = serializer.serialize(keyId);
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    String content = serializer.deserialize(value);
                    Map contentMap = BeanUtil.createBean(content, HashMap.class);
                    return contentMap;
                }
            });
            return result;
        }catch (Exception e){
            return getRedisMap(db,keyId);
        }
    }


    /**
     * 饿了么获取Token
     * @return Token
     */
    public Token getElemeToken() {
        String tokenstr = getRedisString("eleme_token");
        Token token = null;
        OAuthClient oc = new OAuthClient(SystemConfig.GetEleme2Config());
        if (!StringUtils.isEmpty(tokenstr)&&tokenstr!="null"){
            token= JSONObject.parseObject(tokenstr,Token.class);
//            token = oc.getTokenByRefreshToken(token.getRefreshToken());
        }
        if (token==null){
            token = oc.getTokenInClientCredentials();
            tokenstr = JSONObject.toJSONString(token);
            addRedis("eleme_token",tokenstr,25*60);
        }
        return token;
    }
}


