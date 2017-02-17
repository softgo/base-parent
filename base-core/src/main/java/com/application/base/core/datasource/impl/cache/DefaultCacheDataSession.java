package com.application.base.core.datasource.impl.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.StringUtils;

import com.application.base.core.constant.Constants;
import com.application.base.core.datasource.impl.common.DefaultDataSession;
import com.application.base.core.datasource.param.CustomSQL;
import com.application.base.core.datasource.param.ESQL;
import com.application.base.core.datasource.param.Param;
import com.application.base.core.datasource.param.SQLCreator;
import com.application.base.core.datasource.session.CacheDataSession;
import com.application.base.core.exception.DataAccessException;
import com.application.base.core.utils.CacheKeyUtils;
import com.application.base.core.utils.SQLUtil;
import com.application.base.redis.api.MulitiRedisSession;
import com.application.base.redis.api.RedisSession;
import com.application.base.redis.exception.RedisException;
import com.application.base.redis.factory.RedisSessionFactory;
import com.application.base.utils.common.JSONUtils;
import com.application.base.utils.common.UUIDProvider;

/**
 * 默认的缓存 Session 数据源.
 */
public class DefaultCacheDataSession extends DefaultDataSession implements CacheDataSession {

    
    private SqlSessionFactory sqlSessionFactory;
    
    private RedisSessionFactory redisSessionFactory;
    
    private SqlSession session;

    public DefaultCacheDataSession() {
    	super();
    }

    public DefaultCacheDataSession(SqlSessionFactory sqlSessionFactory, RedisSessionFactory redisSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.redisSessionFactory = redisSessionFactory;
    }
    
    @Override
    public RedisSession getRedisSession() {
        return redisSessionFactory.getSession();
    }
    
    @Override
	public MulitiRedisSession getMulitiRedisSession() {
		return redisSessionFactory.getMulitiSession();
	}
	
    public SqlSession getCurrentSession() {
        if (session == null){
        	session = new SqlSessionTemplate(sqlSessionFactory);
        }
        return session;
    }

    
    /**
     * 存储对象。
     */
    public <T> T saveObject(Class<T> clazz, T object) throws DataAccessException {
        T result;
        try {
            result = super.saveObject(clazz, object);
            updateTableKey(clazz); 
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    
    /**
     * 批量存储对象。
     */
    public <T> boolean saveBatchObject(Class<T> clazz, List<T> objs) throws DataAccessException {
        boolean result = false;
        try {
            result = super.saveBatchObject(clazz, objs);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    
    /**
     * 修改对象。
     */
    public <T> int updateObjectByID(Class<T> clazz, T obj) throws DataAccessException {
        int result;
        try {
            result = super.updateObjectByID(clazz, obj);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    /**
     * 修改对象。
     */
    public <T> int updateObjectByUUID(Class<T> clazz, T obj) throws DataAccessException {
        int result;
        try {
            result = super.updateObjectByUUID(clazz, obj);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    /**
     * 通过条件修改对象。
     */
    public <T> int updateCustomColumnByWhere(Class<T> clazz, Param param, CustomSQL whereSql) throws DataAccessException {
        int result;
        try {
            result = super.updateCustomColumnByWhere(clazz, param, whereSql);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    
    /**
     * 逻辑删除对象。
     */
    public <T> int logicDelete(Class<T> clazz, Param param) throws DataAccessException {
        int result;
        try {
            result = super.logicDelete(clazz, param);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    
    /**
     * 逻辑删除对象。
     */
    public <T> int logicWhereDelete(Class<T> clazz, CustomSQL whereSql) throws DataAccessException {
        int result;
        try {
            result = super.logicWhereDelete(clazz, whereSql);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    
    /**
     * 物理删除对象。
     */
    public <T> int physicalDelete(Class<T> clazz,String primaryKey, Object objId) throws DataAccessException {
        int result;
        try {
            result = super.physicalDelete(clazz, primaryKey,objId);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    
    /**
     * 物理删除对象。
     */
    public <T> int physicalWhereDelete(Class<T> clazz, CustomSQL whereSql) throws DataAccessException {
        int result;
        try {
            result = super.physicalWhereDelete(clazz, whereSql);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    
    /**
     * 通过id查找对象。
     */
    public <T> T querySingleResultById(Class<T> clazz, String primaryKey, Object objId) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultById(clazz, primaryKey,objId);
            } else {
                //如果是行ID缓存行,则将行缓存的表对应的类名做前缀拼接
                String tableCacheKey = redis.getData(CacheKeyUtils.createTableKey(clazz));
                String rowKey = CacheKeyUtils.createRowResultCacheKey(tableCacheKey, String.valueOf(objId),clazz.getSimpleName());
                obj = redis.getTypeObject(clazz, rowKey);
                //如果getTypeObject方法中转化错误,返回NULL,则删除redis中的缓存
                if (obj == null) {
                    obj = super.querySingleResultById(clazz, primaryKey,objId);
                    if (obj == null)
                        redis.delete(rowKey);
                    else
                        redis.setData(rowKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
                }
                logger.info("[根据rowKey:{},获取值:{}]", rowKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
            }
        } catch (RedisException e) {
            logger.error("querySingleResultById:RedisException:{}", e);
            obj = super.querySingleResultById(clazz, primaryKey, objId);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    /**
     * 通过uuid查找对象。
     */
    @Override
    public <T> T querySingleResultByUUID(Class<T> clazz, String uuid) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultByUUID(clazz, uuid);
            } else {
                //如果是UUID缓存行,则直接用数据行的UUID作为缓存KEY
                String tableCacheKey = redis.getData(CacheKeyUtils.createTableKey(clazz));
                String rowKey = CacheKeyUtils.createRowResultCacheKey(tableCacheKey, uuid,clazz.getSimpleName());
                obj = redis.getTypeObject(clazz, rowKey);
                if (obj == null) {
                    obj = super.querySingleResultByUUID(clazz, uuid);
                    if (obj == null)
                        redis.delete(rowKey);
                    else
                        redis.setData(rowKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
                }
                logger.info("[根据rowKey:{},获取值:{}]", rowKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
            }
           } catch (RedisException e) {
            logger.error("querySingleResultByUUID:RedisException:{}", e);
            obj = super.querySingleResultByUUID(clazz, uuid);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }
    
   
    /**
     * 通过属性查找对象。
     */
    public <T> T querySingleResultByParams(Class<T> clazz, Param param) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultByParams(clazz, param);
            } else {
                //表缓存的KEY由两部分数据组成  表缓存key=md5(前缀+表缓存所在表的缓存key+_+sql)
                String resultKey = createTableResultKey(clazz, ESQL.QUERYSINGLERESULTBYPARAMS, param.get(), redis);
                obj = redis.getTypeObject(clazz, resultKey);
                if (obj == null) {
                    obj = super.querySingleResultByParams(clazz, param);
                    if (obj == null)
                        redis.delete(resultKey);
                    else
                        redis.setData(resultKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, JSONUtils.toJson(obj));
            }

        } catch (RedisException e) {
            logger.error("querySingleResultByParams:RedisException:{}", e);
            obj = super.querySingleResultByParams(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    /**
     * 查找对象的集合。
     */
    public <T> List<T> queryListResult(Class<T> clazz, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryListResult(clazz, param);
            } else {
                String resultKey = createTableResultKey(clazz, ESQL.QUERYLISTRESULT, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    list = super.queryListResult(clazz, param);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(list, JSONUtils.DEFAULT_DATE_TIME_PATTERN);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    list = JSONUtils.fromListJson(jsonResult, clazz, false);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryListResult:RedisException:{}", e);
            list = super.queryListResult(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    

    /**
     * 查找对象的集合。
     */
    public <T> List<T> queryAllListResult(Class<T> clazz, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryAllListResult(clazz, param);
            } else {
                String resultKey = createTableResultKey(clazz, ESQL.QUERYALLLISTRESULT, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    list = super.queryListResult(clazz, param);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(list, JSONUtils.DEFAULT_DATE_TIME_PATTERN);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    list = JSONUtils.fromListJson(jsonResult, clazz, false);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryAllListResult:RedisException:{}", e);
            list = super.queryListResult(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    
    /**
     * 通过条件查找集合。
     */
    public <T> List<T> queryListResultByWhere(Class<T> clazz, CustomSQL whereSQL) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryListResultByWhere(clazz, whereSQL);
            } else {
                String whereSqlStr = whereSQL.toString();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("whereSqlStr", whereSqlStr);
                String resultKey = createTableResultKey(clazz, ESQL.QUERYLISTRESULTBYWHERE, map, redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    list = super.queryListResultByWhere(clazz, whereSQL);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(list, JSONUtils.DEFAULT_DATE_TIME_PATTERN);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    list = JSONUtils.fromListJson(jsonResult, clazz, false);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryListResultByWhere:RedisException:{}", e);
            list = super.queryListResultByWhere(clazz, whereSQL);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    
    /**
     * 获得集合的大小。
     */
    public <T> int queryListResultCount(Class<T> clazz, Param param) throws DataAccessException {
        int count = 0 ;
    	try {
            count = super.queryListResultCount(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return count;
    }

    
    /**
     * 获得集合的大小。
     */
    public <T> int queryListResultCountByWhere(Class<T> clazz, CustomSQL whereSQL) {
        int count = 0 ;
    	try {
            count = super.queryListResultCountByWhere(clazz, whereSQL);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return count;
    }

    
    /*************************************************************************** VO对象操作 **************************************************************************/
    
    
    /**
     * 查找 VO 对象。
     */
    public <T> T querySingleVO(Class<T> formater,Throwable able, Param param) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleVO(formater,able, param);
            } else {
                String resultKey = createDatabaseResultKey(able, null, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    obj = super.querySingleVO(formater,able, param);
                    if (obj == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(obj);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    obj = JSONUtils.fromJson(jsonResult,formater);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("querySingleVO:RedisException:{}", e);
            obj = super.querySingleVO(formater,able, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    
    /**
     * 通过customsql 的 id 查找对象
     */
    public <T> T querySingleVOByCustomElementName(Class<T> formater,String elementId, Param param) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleVOByCustomElementName(formater,elementId, param);
            } else {
                String resultKey = createDatabaseResultKey(null, elementId, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    obj = super.querySingleVOByCustomElementName(formater,elementId, param);
                    if (obj == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(obj);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    obj = JSONUtils.fromJson(jsonResult,formater);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("querySingleVOByCustomElementName:RedisException:{}", e);
            obj = super.querySingleVOByCustomElementName(formater,elementId, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    
    /**
     * 通过customsql 的 id 查找对象
     */
    public <T> List<T> queryVOList(Class<T> clazz, Throwable able, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryVOList(clazz, able, param);
            } else {
                String resultKey = createDatabaseResultKey(able, null, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    list = super.queryVOList(clazz, able, param);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(list, JSONUtils.DEFAULT_DATE_TIME_PATTERN);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    list = JSONUtils.fromListJson(jsonResult, clazz, false);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryVOList:RedisException:{}", e);
            list = super.queryVOList(clazz, able, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }


    /**
     * 通过customsql 的 id 查找对象
     */
    public <T> List<T> queryVOListByCustomElementName(Class<T> clazz, String elementId, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryVOListByCustomElementName(clazz, elementId, param);
            } else {
                String resultKey = createDatabaseResultKey(null, elementId, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    list = super.queryVOListByCustomElementName(clazz, elementId, param);
                    if (list != null) {
                        jsonResult = JSONUtils.toJson(list, JSONUtils.DEFAULT_DATE_TIME_PATTERN);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    list = JSONUtils.fromListJson(jsonResult, clazz, false);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryVOListByCustomElementName:RedisException:{}", e);
            list = super.queryVOListByCustomElementName(clazz, elementId, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    /**
     * 更新table Key
     *
     * @param clazz
     */
    private <T> void updateTableKey(Class<T> clazz) {
        String tableKey = null;
        try {
            //表进行添加操作,需要让表KEY失效
            RedisSession redis = redisSessionFactory.getSession();
            if (redis != null) {
                //更新表Key
                String cacheTableKey = CacheKeyUtils.createTableKey(clazz);
                tableKey = UUIDProvider.uuid();
                redis.setData(cacheTableKey, tableKey);
                logger.info("更新表:{},key:{}", cacheTableKey, tableKey);
            }
        } catch (RedisException e) {
            //处理了RedisException,不向上抛
            logger.debug("更新表Key异常:{}", e);
        }
    }

    /**
     * 更新库级缓存
     *
     * @return
     */
    private void updateDatabaseKey() {
        String databaseKey = null;
        try {
            RedisSession redis = redisSessionFactory.getSession();
            if (redis != null) {
                //更新表Key
                databaseKey = UUIDProvider.uuid();
                redis.setData(Constants.SQLConstants.DATABASE_KEY, databaseKey);
                logger.info("更新库:{},key:{}", Constants.SQLConstants.DATABASE_KEY, databaseKey);
            }
        } catch (RedisException e) {
            //处理了RedisException,不向上抛
            logger.debug("更新表Key异常:{}", e);
        }
    }

    /**
     * 获取表缓存结果的KEY
     *
     * @param clazz 表po
     * @param map   查询参数
     * @param redis
     * @param <T>
     * @return
     */
    private <T> String createTableResultKey(Class<T> clazz, ESQL esql, Map<String, Object> map, RedisSession redis) {
        try {
            String sql = SQLUtil.getSql(getCurrentSession(), SQLCreator.set(clazz, esql).get(), map);
            String tableCacheKey = redis.getData(CacheKeyUtils.createTableKey(clazz));
            return CacheKeyUtils.createTableResultCacheKey(tableCacheKey, sql);
        } catch (RedisException e) {
            //处理了RedisException,不向上抛e
            logger.debug("更新表Key异常:{}", e);
        } catch (Exception e) {
            logger.error("创建表级结果key异常:{}", e);
            throw new DataAccessException(e);
        }
        return null;
    }

    /**
     * 创建库级缓存结果KEY
     *
     * @param able
     * @param sqlId
     * @param map
     * @param redis
     * @return
     */
    private String createDatabaseResultKey(Throwable able, String sqlId, Map<String, Object> map, RedisSession redis) {
        try {
            if (able == null && StringUtils.isEmpty(sqlId))
                return null;
            if (able != null) {
                String sql = SQLUtil.getSql(getCurrentSession(), SQLCreator.set(Constants.SQLConstants.CUSTOMER_SQL, able).get(), map);
                String databaseCacheKey = redis.getData(Constants.SQLConstants.DATABASE_KEY);
                return CacheKeyUtils.createDatabaseResultCacheKey(databaseCacheKey, sql);
            } else if (!StringUtils.isEmpty(sqlId)) {
                String sql = SQLUtil.getSql(getCurrentSession(), sqlId, map);
                String databaseCacheKey = redis.getData(Constants.SQLConstants.DATABASE_KEY);
                return CacheKeyUtils.createDatabaseResultCacheKey(databaseCacheKey, sql);
            }
        } catch (RedisException e) {
            //处理了RedisException,不向上抛e
            logger.debug("更新表Key异常:{}", e);
        }
        return null;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    /**--------------------------------------------------------------------------------选择redis的库进行存储数据.----------------------------------------------------------------------------------------------------**/
    
    
    
    
    
    
    
    
    
    
    /**
     * 通过id查找对象。
     */
    public <T> T querySingleResultByIdByDB(int index,Class<T> clazz, String primaryKey, Object objId) throws DataAccessException {
        T obj;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultById(clazz, primaryKey,objId);
            } else {
                //如果是行ID缓存行,则将行缓存的表对应的类名做前缀拼接
                String tableCacheKey = redis.getData(CacheKeyUtils.createTableKey(clazz));
                String rowKey = CacheKeyUtils.createRowResultCacheKey(tableCacheKey, String.valueOf(objId),clazz.getSimpleName());
                obj = redis.getTypeObject(clazz, rowKey);
                //如果getTypeObject方法中转化错误,返回NULL,则删除redis中的缓存
                if (obj == null) {
                    obj = super.querySingleResultById(clazz, primaryKey,objId);
                    if (obj == null)
                        redis.delete(rowKey);
                    else
                        //redis.setData(rowKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
                    	redis.setData(index,rowKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
                }
                logger.info("[根据rowKey:{},获取值:{}]", rowKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
            }
        } catch (RedisException e) {
            logger.error("querySingleResultById:RedisException:{}", e);
            obj = super.querySingleResultById(clazz, primaryKey, objId);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    /**
     * 通过uuid查找对象。
     */
    public <T> T querySingleResultByUUIDByDB(int index, Class<T> clazz, String uuid) throws DataAccessException {
        T obj;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
        	 //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultByUUID(clazz, uuid);
            } else {
                //如果是UUID缓存行,则直接用数据行的UUID作为缓存KEY
                String tableCacheKey = redis.getData(CacheKeyUtils.createTableKey(clazz));
                String rowKey = CacheKeyUtils.createRowResultCacheKey(tableCacheKey, uuid,clazz.getSimpleName());
                obj = redis.getTypeObject(clazz, rowKey);
                if (obj == null) {
                    obj = super.querySingleResultByUUID(clazz, uuid);
                    if (obj == null)
                        redis.delete(rowKey);
                    else
                        //redis.setData(rowKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
                    	redis.setData(index,rowKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));

                }
                logger.info("[根据rowKey:{},获取值:{}]", rowKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
            }
           } catch (RedisException e) {
            logger.error("querySingleResultByUUID:RedisException:{}", e);
            obj = super.querySingleResultByUUID(clazz, uuid);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }
    
   
    /**
     * 通过属性查找对象。
     */
    public <T> T querySingleResultByParamsByDB(int index,Class<T> clazz, Param param) throws DataAccessException {
        T obj;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultByParams(clazz, param);
            } else {
                //表缓存的KEY由两部分数据组成  表缓存key=md5(前缀+表缓存所在表的缓存key+_+sql)
                String resultKey = createTableResultKey(clazz, ESQL.QUERYSINGLERESULTBYPARAMS, param.get(), redis);
                obj = redis.getTypeObject(clazz, resultKey);
                if (obj == null) {
                    obj = super.querySingleResultByParams(clazz, param);
                    if (obj == null)
                        redis.delete(resultKey);
                    else
                        //redis.setData(resultKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
                    	redis.setData(index,resultKey, JSONUtils.toJson(obj, JSONUtils.DEFAULT_DATE_TIME_PATTERN));
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, JSONUtils.toJson(obj));
            }

        } catch (RedisException e) {
            logger.error("querySingleResultByParams:RedisException:{}", e);
            obj = super.querySingleResultByParams(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    /**
     * 查找对象的集合。
     */
    public <T> List<T> queryListResultByDB(int index,Class<T> clazz, Param param) throws DataAccessException {
        List<T> list;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryListResult(clazz, param);
            } else {
                String resultKey = createTableResultKey(clazz, ESQL.QUERYLISTRESULT, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    list = super.queryListResult(clazz, param);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(list, JSONUtils.DEFAULT_DATE_TIME_PATTERN);
                        //redis.setData(resultKey, jsonResult);
                        redis.setData(index, resultKey, jsonResult);
                    }
                } else {
                    list = JSONUtils.fromListJson(jsonResult, clazz, false);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryListResult:RedisException:{}", e);
            list = super.queryListResult(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    

    /**
     * 查找对象的集合。
     */
    public <T> List<T> queryAllListResultByDB(int index,Class<T> clazz, Param param) throws DataAccessException {
        List<T> list;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryListResult(clazz, param);
            } else {
                String resultKey = createTableResultKey(clazz, ESQL.QUERYALLLISTRESULT, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    list = super.queryListResult(clazz, param);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(list, JSONUtils.DEFAULT_DATE_TIME_PATTERN);
                        //redis.setData(resultKey, jsonResult);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    list = JSONUtils.fromListJson(jsonResult, clazz, false);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryAllListResult:RedisException:{}", e);
            list = super.queryListResult(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    
    /**
     * 通过条件查找集合。
     */
    public <T> List<T> queryListResultByWhereByDB(int index,Class<T> clazz, CustomSQL whereSQL) throws DataAccessException {
        List<T> list;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryListResultByWhere(clazz, whereSQL);
            } else {
                String whereSqlStr = whereSQL.toString();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("whereSqlStr", whereSqlStr);
                String resultKey = createTableResultKey(clazz, ESQL.QUERYLISTRESULTBYWHERE, map, redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    list = super.queryListResultByWhere(clazz, whereSQL);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(list, JSONUtils.DEFAULT_DATE_TIME_PATTERN);
                        //redis.setData(resultKey, jsonResult);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    list = JSONUtils.fromListJson(jsonResult, clazz, false);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryListResultByWhere:RedisException:{}", e);
            list = super.queryListResultByWhere(clazz, whereSQL);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }
    
    
    /**
     * 查找 VO 对象。
     */
    public <T> T querySingleVOByDB(int index,Class<T> formater,Throwable able, Param param) throws DataAccessException {
        T obj;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleVO(formater,able, param);
            } else {
                String resultKey = createDatabaseResultKey(able, null, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    obj = super.querySingleVO(formater,able, param);
                    if (obj == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(obj);
                        //redis.setData(resultKey, jsonResult);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    obj = JSONUtils.fromJson(jsonResult,formater);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("querySingleVO:RedisException:{}", e);
            obj = super.querySingleVO(formater,able, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    
    /**
     * 通过customsql 的 id 查找对象
     */
    public <T> T querySingleVOByCustomElementNameByDB(int index,Class<T> formater,String elementId, Param param) throws DataAccessException {
        T obj;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleVOByCustomElementName(formater,elementId, param);
            } else {
                String resultKey = createDatabaseResultKey(null, elementId, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    obj = super.querySingleVOByCustomElementName(formater,elementId, param);
                    if (obj == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(obj);
                        //redis.setData(resultKey, jsonResult);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    obj = JSONUtils.fromJson(jsonResult,formater);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("querySingleVOByCustomElementName:RedisException:{}", e);
            obj = super.querySingleVOByCustomElementName(formater,elementId, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    
    /**
     * 通过customsql 的 id 查找对象
     */
    public <T> List<T> queryVOListByDB(int index,Class<T> clazz, Throwable able, Param param) throws DataAccessException {
        List<T> list;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryVOList(clazz, able, param);
            } else {
                String resultKey = createDatabaseResultKey(able, null, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    list = super.queryVOList(clazz, able, param);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JSONUtils.toJson(list, JSONUtils.DEFAULT_DATE_TIME_PATTERN);
                        //redis.setData(resultKey, jsonResult);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    list = JSONUtils.fromListJson(jsonResult, clazz, false);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryVOList:RedisException:{}", e);
            list = super.queryVOList(clazz, able, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }


    /**
     * 通过customsql 的 id 查找对象
     */
    public <T> List<T> queryVOListByCustomElementNameByDB(int index,Class<T> clazz, String elementId, Param param) throws DataAccessException {
        List<T> list;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryVOListByCustomElementName(clazz, elementId, param);
            } else {
                String resultKey = createDatabaseResultKey(null, elementId, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (StringUtils.isEmpty(jsonResult)) {
                    list = super.queryVOListByCustomElementName(clazz, elementId, param);
                    if (list != null) {
                        jsonResult = JSONUtils.toJson(list, JSONUtils.DEFAULT_DATE_TIME_PATTERN);
                        //redis.setData(resultKey, jsonResult);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    list = JSONUtils.fromListJson(jsonResult, clazz, false);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryVOListByCustomElementName:RedisException:{}", e);
            list = super.queryVOListByCustomElementName(clazz, elementId, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    /**
     * 更新table Key
     *
     * @param clazz
     */
    private <T> void updateTableKeyByDB(int index,Class<T> clazz) {
        String tableKey = null;
        try {
            //表进行添加操作,需要让表KEY失效
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            if (redis != null) {
                //更新表Key
                String cacheTableKey = CacheKeyUtils.createTableKey(clazz);
                tableKey = UUIDProvider.uuid();
                //redis.setData(cacheTableKey, tableKey);
                redis.setData(index,cacheTableKey, tableKey);
                logger.info("更新表:{},key:{}", cacheTableKey, tableKey);
            }
        } catch (RedisException e) {
            //处理了RedisException,不向上抛
            logger.debug("更新表Key异常:{}", e);
        }
    }

    /**
     * 更新库级缓存
     *
     * @return
     */
    private void updateDatabaseKeyByDB(int index) {
        String databaseKey = null;
        try {
            MulitiRedisSession redis = redisSessionFactory.getMulitiSession();
            if (redis != null) {
                //更新表Key
                databaseKey = UUIDProvider.uuid();
                //redis.setData(Constants.SQLConstants.DATABASE_KEY, databaseKey);
                redis.setData(index,Constants.SQLConstants.DATABASE_KEY, databaseKey);
                logger.info("更新库:{},key:{}", Constants.SQLConstants.DATABASE_KEY, databaseKey);
            }
        } catch (RedisException e) {
            //处理了RedisException,不向上抛
            logger.debug("更新表Key异常:{}", e);
        }
    }

}
