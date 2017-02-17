package com.application.base.core.datasource.impl.common;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.core.constant.Constants;
import com.application.base.core.datasource.param.CustomSQL;
import com.application.base.core.datasource.param.ESQL;
import com.application.base.core.datasource.param.Param;
import com.application.base.core.datasource.param.SQLCreator;
import com.application.base.core.datasource.session.DataSession;
import com.application.base.core.exception.DataAccessException;
import com.application.base.core.utils.SQLUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认的 Session
 */
public class DefaultDataSession implements DataSession {

    public static final Logger logger = LoggerFactory.getLogger(DefaultDataSession.class);
    
    private SqlSessionFactory sqlSessionFactory;
    
    private static final String UPDATE_STRING = "updateSqlStr";
    
    private static final String WHERE_STRING = "whereSqlStr";
    
    private SqlSession session;

    public DefaultDataSession() {
    }

    //注入的 sqlSessionFactory ...
    public DefaultDataSession(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
    public SqlSession getCurrentSession() {
        if (session == null){
        	session = new SqlSessionTemplate(sqlSessionFactory);
        }
        return session;
    }

    
    /**
     * 存储对象
     */
    public <T> T saveObject(Class<T> clazz, T object) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("obj", object);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.SAVE).get(), map));
            int count = session.insert(SQLCreator.set(clazz, ESQL.SAVE).get(), object);
            if (count > 0) {
				return object;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * 批量存储对象.
     */
	public <T> boolean saveBatchObject(Class<T> clazz, List<T> objs) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("list", objs);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.SAVEBATCH).get(), map));
            int count = session.insert(SQLCreator.set(clazz, ESQL.SAVEBATCH).get(), objs);
            if (count > 0) {
				return true;
			}else{
				return false;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

	/**
	 * id修改对象
	 */
	public <T> int updateObjectByID(Class<T> clazz, T obj) throws DataAccessException {
		 try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("obj", obj);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.UPDATEBYID).get(), map));
            int count = session.update(SQLCreator.set(clazz, ESQL.UPDATEBYID).get(), obj);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
	}

	
	/**
	 * uuid修改对象
	 */
	public <T> int updateObjectByUUID(Class<T> clazz, T obj) throws DataAccessException {
		try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("obj", obj);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.UPDATEBYUUID).get(), map));
            int count = session.update(SQLCreator.set(clazz, ESQL.UPDATEBYUUID).get(), obj);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
	}
    

    /**
     * 逻辑删除对象.
     */
    public <T> int logicDelete(Class<T> clazz, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.LOGICDELETE).get(), param.get()));
            int count = session.update(SQLCreator.set(clazz, ESQL.LOGICDELETE).get(), param.get());
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
    

    /**
     * 物理删除对象
     */
    public <T> int physicalDelete(Class<T> clazz, String primaryKey, Object objId) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(primaryKey, objId);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.PHYSICALDELETE).get(), map));
            int count = session.delete(SQLCreator.set(clazz, ESQL.PHYSICALDELETE).get(), objId);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 通过id查找对象.
     */
	public <T> T querySingleResultById(Class<T> clazz, String primaryKey, Object objId) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(primaryKey,objId);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.QUERYSINGLERESULTBYID).get(), map));
            T object = session.selectOne(SQLCreator.set(clazz, ESQL.QUERYSINGLERESULTBYID).get(), objId);
            if (object != null ) {
				return object;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 通过uuid查找对象.
     */
    public <T> T querySingleResultByUUID(Class<T> clazz, String uuid) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("uuid", uuid);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.QUERYSINGLERESULTBYUUID).get(), map));
            T object = session.selectOne(SQLCreator.set(clazz, ESQL.QUERYSINGLERESULTBYUUID).get(), uuid);
            if (object != null ) {
				return object;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    /**
     * 通过条件查询对象
     */
    public <T> T querySingleResultByParams(Class<T> clazz, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.QUERYSINGLERESULTBYPARAMS).get(), param.get()));
            T object = session.selectOne(SQLCreator.set(clazz, ESQL.QUERYSINGLERESULTBYPARAMS).get(), param.get());
            if (object != null ) {
				return object;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 通过条件查询集合
     */
    public <T> List<T> queryListResult(Class<T> clazz, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.QUERYLISTRESULT).get(), param.get()));
            List<T> list = session.selectList(SQLCreator.set(clazz, ESQL.QUERYLISTRESULT).get(), param.get());
            if (list != null && list.size() > 0 ) {
				return list;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 通过条件查询集合
     */
    public <T> List<T> queryAllListResult(Class<T> clazz, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.QUERYALLLISTRESULT).get(), param.get()));
            List<T> list = session.selectList(SQLCreator.set(clazz, ESQL.QUERYALLLISTRESULT).get(), param.get());
            if (list != null && list.size() > 0 ) {
				return list;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 通过条件查询个数
     */
    public <T> int queryListResultCount(Class<T> clazz, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.QUERYLISTRESULTCOUNT).get(), param.get()));
            int count = session.selectOne(SQLCreator.set(clazz, ESQL.QUERYLISTRESULTCOUNT).get(), param.get());
            if (count > 0) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
    
    
    /**
     * 通过CustomerSQL文件中的id来查找对象集合
     */
    public <T> T querySingleVO(Class<T> formater, Throwable able, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(Constants.SQLConstants.CUSTOMER_SQL, able).get(), param.get()));
            T t = session.selectOne(SQLCreator.set(Constants.SQLConstants.CUSTOMER_SQL, able).get(), param.get());
            if (t != null ) {
				return t;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 通过CustomerSQL文件中的id来查找对象
     */
    public <T> T querySingleVOByCustomElementName(Class<T> formater, String elementId, Param param)
            throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(Constants.SQLConstants.CUSTOMER_SQL, elementId).get(), param.get()));
            T t = session.selectOne(SQLCreator.set(Constants.SQLConstants.CUSTOMER_SQL, elementId).get(), param.get());
            if (t != null ) {
				return t;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    /**
     * 通过参数查找对象
     */
    public <T> List<T> queryVOList(Class<T> clazz, Throwable able, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(Constants.SQLConstants.CUSTOMER_SQL, able).get(), param.get()));
            List<T> list = session.selectList(SQLCreator.set(Constants.SQLConstants.CUSTOMER_SQL, able).get(), param.get());
            if (list != null && list.size() > 0 ) {
				return list;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 通过CustomerSQL文件中的id来查找对象集合
     */
    public <T> List<T> queryVOListByCustomElementName(Class<T> clazz, String elementId, Param param)
            throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(Constants.SQLConstants.CUSTOMER_SQL, elementId).get(), param.get()));
            List<T> list = session.selectList(SQLCreator.set(Constants.SQLConstants.CUSTOMER_SQL, elementId).get(), param.get());
            if (list != null && list.size() > 0 ) {
				return list;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    /**
     * 物理删除操作
     */
    public <T> int physicalWhereDelete(Class<T> clazz, CustomSQL whereSql) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            String whereSqlStr = whereSql.toString();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("whereSqlStr", whereSqlStr);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.PHYSICALWHEREDELETE).get(), map));
            int count = session.delete(SQLCreator.set(clazz, ESQL.PHYSICALWHEREDELETE).get(), map);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    /**
     * 通过条件更改操作
     */
    public <T> int updateCustomColumnByWhere(Class<T> clazz, Param param, CustomSQL whereSql)
            throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> strMap = new HashMap<String, Object>();
            strMap.put(UPDATE_STRING, SQLUtil.updateSql(param));
            strMap.put(WHERE_STRING, whereSql.toString());
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.UPDATECUSTOMCOLUMNBYWHERE).get(), strMap));
            int count = session.update(SQLCreator.set(clazz, ESQL.UPDATECUSTOMCOLUMNBYWHERE).get(), strMap);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 逻辑删除对象
     */
    public <T> int logicWhereDelete(Class<T> clazz, CustomSQL whereSql) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            String whereSqlStr = whereSql.toString();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("whereSqlStr", whereSqlStr);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.LOGICWHEREDELETE).get(), map));
            int count = session.update(SQLCreator.set(clazz, ESQL.LOGICWHEREDELETE).get(), map);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 通过条件查询对象
     */
    public <T> List<T> queryListResultByWhere(Class<T> clazz, CustomSQL whereSQL) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            String whereSqlStr = whereSQL.toString();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("whereSqlStr", whereSqlStr);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.QUERYLISTRESULTBYWHERE).get(), map));
            List<T> list = session.selectList(SQLCreator.set(clazz, ESQL.QUERYLISTRESULTBYWHERE).get(), map);
            if (list != null && list.size() > 0 ) {
				return list;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    /**
     * 通过条件查询对象个数
     */
    public <T> int queryListResultCountByWhere(Class<T> clazz, CustomSQL whereSQL) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            String whereSqlStr = whereSQL.toString();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("whereSqlStr", whereSqlStr);
            logger.debug("sql:[{}]", SQLUtil.getSql(session, SQLCreator.set(clazz, ESQL.QUERYLISTRESULTCOUNTBYWHERE).get(), map));
            int count = session.selectOne(SQLCreator.set(clazz, ESQL.QUERYLISTRESULTCOUNTBYWHERE).get(), map);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

}
