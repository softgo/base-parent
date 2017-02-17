package ${JavaBeanDaoImplPath};

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import org.springframework.stereotype.Repository;

import com.application.base.core.apisupport.impl.MultiCacheStrutsBaseDaoImpl;
import com.application.base.core.apisupport.impl.MultiStrutsBaseDaoImpl;
import com.application.base.core.datasource.param.CustomSQL;
import com.application.base.core.datasource.param.ESQLOperator;
import com.application.base.core.datasource.param.Param;
import com.application.base.core.datasource.param.ParamBuilder;
import com.application.base.core.datasource.param.SQLCreator;
import com.application.base.core.exception.BusinessException;
import com.application.base.core.obj.Pagination;
import com.application.base.core.utils.CommonBeanUtils;


import ${JavaBeanPath}.${poName};
import ${JavaBeanDaoPath}.${poName}Dao;

/**
 * ${poName}DaoImpl实现
 * 
 * @author 系统生成
 *
 */
@Repository("${firstLowerPoName}Dao")

<#if useCache == "YES">
public class ${poName}DaoImpl extends MultiCacheStrutsBaseDaoImpl<${poName}> implements ${poName}Dao {
<#else>
public class ${poName}DaoImpl extends MultiStrutsBaseDaoImpl<${poName}> implements ${poName}Dao {
</#if>	
	
	public ${poName} saveObject(Map<String, Object> param,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		${poName} object = CommonBeanUtils.transMap2BasePO(param, ${poName}.class);
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().saveObject(${poName}.class,object);
		<#else>
		return factory.getWriteDataSession().saveObject(${poName}.class,object);
		</#if>
	}


	public ${poName} saveObject(${poName} object,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().saveObject(${poName}.class,object);
		<#else>
		return factory.getWriteDataSession().saveObject(${poName}.class,object);
		</#if>
	}
	
	
	public boolean saveBatchObject(List<${poName}> objs,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().saveBatchObject(${poName}.class,objs);
		<#else>
		return factory.getWriteDataSession().saveBatchObject(${poName}.class,objs);
		</#if>
	}
	
	
	public ${poName} getObjectByID(Object objId,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultById(${poName}.class,"${tablePKVal}", objId);
		<#else>
		return factory.getReadDataSession().querySingleResultById(${poName}.class,"${tablePKVal}", objId);
		</#if>
	}


	<#if existUuid == "0">
	public ${poName} getObjectByUUID(String uuid,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultByUUID(${poName}.class, uuid);
		<#else>
		return factory.getReadDataSession().querySingleResultByUUID(${poName}.class, uuid);
		</#if>
	}
	<#else>
	public ${poName} getObjectByUUID(String uuid,String factoryTag) throws BusinessException {
		return null;
	}
	</#if>
	
	
	public int updateObjectByID(Map<String, Object> param, ${poName} object,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		${poName} operateObject = new ${poName}();
		operateObject.set${primaryKeySet}(object.get${primaryKeySet}());
		operateObject.setCreateTime(object.getCreateTime());
		operateObject = CommonBeanUtils.transMap2BasePO(param, operateObject);
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateObjectByID(${poName}.class, operateObject);
		<#else>
		return factory.getWriteDataSession().updateObjectByID(${poName}.class, operateObject);
		</#if>
	}
	
	
	<#if existUuid == "0">
	public int updateObjectByUUID(Map<String, Object> param, ${poName} object,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		${poName} operateObject = new ${poName}();
		operateObject.setUuid(object.getUuid());
		operateObject.setCreateTime(object.getCreateTime());
		operateObject = CommonBeanUtils.transMap2BasePO(param, operateObject);
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateObjectByUUID(${poName}.class, operateObject);
		<#else>
		return factory.getWriteDataSession().updateObjectByUUID(${poName}.class, operateObject);
		</#if>
	}
	<#else>
	public int updateObjectByUUID(Map<String, Object> param, ${poName} object,String factoryTag) throws BusinessException {
		return 0;
	}
	</#if>
	
	
	public int updateObjectByWhere(Map<String, Object> param,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		Param params = ParamBuilder.getInstance().getParam().add(param);
		CustomSQL where = null; //SQLCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA"); //根据实际情况填写.
        <#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		<#else>
		return factory.getWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		</#if>
	}
	
	
	public int updateObjectByWhere(${poName} object,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加 
		Param params = null; //ParamBuilder.getInstance().getParam(); //根据实际情况填写 //updateStr
		CustomSQL where = null; //SQLCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA"); //根据实际情况填写. //whereStr
       	<#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		<#else>
		return factory.getWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		</#if>
	}

	
	public int deleteObjectByID(Object objId,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		<#if existIsDelete == "0">
		Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv("${tablePKVal}",objId));
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicDelete(${poName}.class, param);
		<#else>
		return factory.getWriteDataSession().logicDelete(${poName}.class, param);
		</#if>
		<#else>
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalDelete(${poName}.class,"${tablePKVal}", objId);
		<#else>
		return factory.getWriteDataSession().physicalDelete(${poName}.class,"${tablePKVal}", objId);
		</#if>
		</#if>
	}


	<#if existUuid == "0">
	public int deleteObjectByUUID(String uuid,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		<#if existIsDelete == "0">
		Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv("uuid",uuid));
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicDelete(${poName}.class, param);
		<#else>
		return factory.getWriteDataSession().logicDelete(${poName}.class, param);
		</#if>
		<#else>
		CustomSQL where = SQLCreator.where().cloumn("uuid").operator(ESQLOperator.EQ).value(uuid); //根据实际情况填写.
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalDelete(${poName}.class,where);
		<#else>
		return factory.getWriteDataSession().physicalDelete(${poName}.class,where);
		</#if>
		</#if>
	}
	<#else>
	public int deleteObjectByUUID(String uuid,String factoryTag) throws BusinessException {
		return 0;  //没有uuid,该方法就为不可用
	}
	</#if>

	
	public int deleteObjectByWhere(Map<String, Object> param,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己设置访问的数据库.
		//根据实际情况填写要查的列和对应的值.
		CustomSQL where = null; //SQLCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA"); //根据实际情况填写. //whereStr
		<#if existIsDelete == "0">
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicWhereDelete(${poName}.class, where);
		<#else>
		return factory.getWriteDataSession().logicWhereDelete(${poName}.class, where);
		</#if>
		<#else>
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalWhereDelete(${poName}.class, where);
		<#else>
		return factory.getWriteDataSession().physicalWhereDelete(${poName}.class, where);
		</#if>
		</#if>		
	}


	public int deleteObjectByWhere(${poName} object,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己设置访问的数据库.
		//根据实际情况填写要查的列和对应的值.
		CustomSQL where = null; //SQLCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA"); //根据实际情况填写.
		<#if existIsDelete == "0">
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicWhereDelete(${poName}.class, where);
		<#else>
		return factory.getWriteDataSession().logicWhereDelete(${poName}.class, where);
		</#if>
		<#else>
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalWhereDelete(${poName}.class, where);
		<#else>
		return factory.getWriteDataSession().physicalWhereDelete(${poName}.class, where);
		</#if>
		</#if>		
	}
	
	
	public List<${poName}> getObjects(Map<String, Object> param,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		Param params = ParamBuilder.getInstance().getParam().add(param);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryAllListResult(${poName}.class,params);
		<#else>
		return factory.getReadDataSession().queryAllListResult(${poName}.class,params);
		</#if>
	}
	
	
	public List<${poName}> getObjects(${poName} object,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(object));
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryAllListResult(${poName}.class,params);
		<#else>
		return factory.getReadDataSession().queryAllListResult(${poName}.class,params);
		</#if>
	}
	
	
	public Pagination<${poName}> paginationObjects(Map<String, Object> param, int pageNo, int pageSize,String factoryTag)
			throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(param);
		return queryClassPagination(${poName}.class, params, pageNo, pageSize, factoryTag);
	}


	public Pagination<${poName}> paginationObjects(${poName} object, int pageNo, int pageSize,String factoryTag)
			throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(object));
		return queryClassPagination(${poName}.class, params, pageNo, pageSize, factoryTag);
	}
	
	
	public ${poName} findObjectByPros(Map<String, Object> param,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		Param params = ParamBuilder.getInstance().getParam().add(param);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultByParams(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().querySingleResultByParams(${poName}.class, params);
		</#if>
	}
	
	
	public ${poName} findObjectByPros(${poName} object,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(object));
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultByParams(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().querySingleResultByParams(${poName}.class, params);
		</#if>
	}
	
	
	public List<${poName}> findObjectListByPros(Map<String, Object> param,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		Param params = ParamBuilder.getInstance().getParam().add(param);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResult(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResult(${poName}.class, params);
		</#if>
	}
	
	
	public List<${poName}> findObjectListByPros(${poName} object,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(object));
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResult(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResult(${poName}.class, params);
		</#if>
	}
	
	public int getObjectCount(String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResultCount(${poName}.class, ParamBuilder.getInstance().getParam());
		<#else>
		return factory.getReadDataSession().queryListResultCount(${poName}.class, ParamBuilder.getInstance().getParam());
		</#if>
	}
	
	
	public int getObjectCount(Map<String, Object> param,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		Param params = ParamBuilder.getInstance().getParam().add(param);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResultCount(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResultCount(${poName}.class, params);
		</#if>
	}


	public int getObjectCount(${poName} object,String factoryTag) throws BusinessException {
		factory.setFactoryTag(factoryTag); //根据实际情况自己添加
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(object));
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResultCount(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResultCount(${poName}.class, params);
		</#if>
	}

}
