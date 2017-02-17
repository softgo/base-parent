package ${JavaBeanServiceImplPath};

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.base.core.constant.CommonResultConstant;
import com.application.base.core.exception.CommonException;
import com.application.base.core.obj.Pagination;

import com.application.base.core.utils.CommonBeanUtils;

import ${JavaBeanPath}.${poName};
import ${JavaBeanDaoPath}.${poName}Dao;
import ${JavaBeanServicePath}.${poName}Service;

/**
 * ${poName}ServiceImpl实现
 * 
 * @author 系统生成
 *
 */

@Service("${firstLowerPoName}Service")
public class ${poName}ServiceImpl implements ${poName}Service {

	@Autowired
	private ${poName}Dao ${firstLowerPoName}Dao; //Dao 注入.

	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public ${poName} saveObject(Map<String, Object> param) {
		try {
			//添加的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			${poName} object = ${firstLowerPoName}Dao.saveObject(param,factoryTag);
			if(object==null){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return object;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}


	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public ${poName} saveObject(${poName} object) {
		try {
			//添加的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			${poName} result = ${firstLowerPoName}Dao.saveObject(object,factoryTag);
			if(result==null){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return result;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public boolean saveBatchObject(List<${poName}> objs) {
		try {
			//批量添加处理,自己去装数据
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			boolean result = ${firstLowerPoName}Dao.saveBatchObject(objs,factoryTag);
			if(!result){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return result;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}

	
	public ${poName} getObjectByID(Object objId) {
		try {
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			${poName} object = ${firstLowerPoName}Dao.getObjectByID(objId,factoryTag);
			if(object==null){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return object;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}

	<#if existUuid == "0">
	public ${poName} getObjectByUUID(String uuid) {
		try {
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			${poName} object = ${firstLowerPoName}Dao.getObjectByUUID(uuid,factoryTag);
			if(object==null){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return object;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	<#else>
	public ${poName} getObjectByUUID(String uuid) {
		return null;
	}
	</#if>

	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public int updateObjectByID(Map<String, Object> param, Object objId) {
		try {
			//修改的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			${poName} object = getObjectByID(objId); 
			int count = ${firstLowerPoName}Dao.updateObjectByID(param, object,factoryTag);
			if(count < 1 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}


	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public int updateObjectByID(${poName} object, Object objId) {
		try {
			//修改的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			${poName} existObj = getObjectByID(objId);
			Map<String, Object> param = CommonBeanUtils.transBean2Map(object);
			param = CommonBeanUtils.getValueMap(param);
			int count = ${firstLowerPoName}Dao.updateObjectByID(param,existObj,factoryTag);
			if(count < 1 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	<#if existUuid == "0">
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public int updateObjectByUUID(Map<String, Object> param, String uuid) {
		try {
			//修改的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			${poName} object = getObjectByUUID(uuid);
			int count = ${firstLowerPoName}Dao.updateObjectByUUID(param, object,factoryTag);
			if(count < 1 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	<#else>
	public int updateObjectByUUID(Map<String, Object> param, String uuid) {
		return 0;
	}
	</#if>
	
	
	<#if existUuid == "0">
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public int updateObjectByUUID(${poName} object, String uuid) {
		try {
			//修改的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			${poName} existObj = getObjectByUUID(uuid);
			Map<String, Object> param = CommonBeanUtils.transBean2Map(object);
			param = CommonBeanUtils.getValueMap(param);
			int count = ${firstLowerPoName}Dao.updateObjectByUUID(param,existObj,factoryTag);
			if(count < 1 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	<#else>
	public int updateObjectByUUID(${poName} object, String uuid) {
		return 0;
	}
	</#if>
	
	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public int updateObjectByWhere(Map<String, Object> param) {
		try {
			//修改的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			int count = ${firstLowerPoName}Dao.updateObjectByWhere(param,factoryTag);
			if(count < 1 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public int updateObjectByWhere(${poName} object) {
		try {
			//修改的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			int count = ${firstLowerPoName}Dao.updateObjectByWhere(object,factoryTag);
			if(count < 1 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public int deleteObjectByID(Object objId) {
		try {
			//删除的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			int count = ${firstLowerPoName}Dao.deleteObjectByID(objId,factoryTag);
			if(count < 1 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}

	<#if existUuid == "0">
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public int deleteObjectByUUID(String uuid) {
		try {
			//删除的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			int count = ${firstLowerPoName}Dao.deleteObjectByUUID(uuid,factoryTag);
			if(count < 1 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	<#else>
	public int deleteObjectByUUID(String uuid) {
		return 0;
	}
	</#if>
	
	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public int deleteObjectByWhere(Map<String, Object> param) {
		try {
			//删除的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			int count = ${firstLowerPoName}Dao.deleteObjectByWhere(param,factoryTag);
			if(count < 1 ){
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}


	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	public int deleteObjectByWhere(${poName} object) {
		try {
			//删除的验证和业务逻辑自己添加处理
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			int count = ${firstLowerPoName}Dao.deleteObjectByWhere(object,factoryTag);
			if(count < 1 ){
				throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	public List<${poName}> getObjects(Map<String, Object> param) {
		try {
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			List<${poName}> objects = ${firstLowerPoName}Dao.getObjects(param,factoryTag);
			if(objects==null || objects.size()==0 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return objects;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}


	public List<${poName}> getObjects(${poName} object) {
		try {
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			List<${poName}> objects = ${firstLowerPoName}Dao.getObjects(object,factoryTag);
			if(objects==null || objects.size()==0 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return objects;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	public Pagination<${poName}> paginationObjects(Map<String, Object> param, int pageNo, int pageSize){
		try {
			//根据条件查询分页操作.
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			Pagination<${poName}> pagination = ${firstLowerPoName}Dao.paginationObjects(param, pageNo, pageSize,factoryTag);
			if(pagination==null){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return pagination;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}

	
	public Pagination<${poName}> paginationObjects(${poName} object, int pageNo, int pageSize){
		try {
			//根据条件查询分页操作.
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			Pagination<${poName}> pagination = ${firstLowerPoName}Dao.paginationObjects(object, pageNo, pageSize,factoryTag);
			if(pagination==null){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return pagination;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	public ${poName} findObjectByPros(Map<String, Object> param) {
		try {
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			${poName} object = ${firstLowerPoName}Dao.findObjectByPros(param,factoryTag);
			if(object==null){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return object;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	public ${poName} findObjectByPros(${poName} object) {
		try {
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			${poName} result = ${firstLowerPoName}Dao.findObjectByPros(object,factoryTag);
			if(result==null){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return result;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	public List<${poName}> findObjectListByPros(Map<String, Object> param) {
		try {
			//根据条件查询对象.
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			List<${poName}> objects = ${firstLowerPoName}Dao.findObjectListByPros(param,factoryTag);
			if(objects==null || objects.size()==0 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return objects;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	public List<${poName}> findObjectListByPros(${poName} object) {
		try {
			//根据条件查询对象.
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			List<${poName}> objects = ${firstLowerPoName}Dao.findObjectListByPros(object,factoryTag);
			if(objects==null || objects.size()==0 ){
				//异常信息由自己去配置文件中编写
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_IS_NULL);
			}
			return objects;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	public int getObjectCount() {
		try {
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			int count = ${firstLowerPoName}Dao.getObjectCount(factoryTag);
			if(count < 1 ){
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_COUNT_FAILD);
			}
			return count;
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}

	
	public int getObjectCount(Map<String, Object> param) {
		try {
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			int count = ${firstLowerPoName}Dao.getObjectCount(param,factoryTag);
			if(count < 1){
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_COUNT_FAILD);
			}	
			return count;		
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
	
	
	public int getObjectCount(${poName} object) {
		try {
			String factoryTag = null; //根据实际情况,来设置访问的数据源,如果设置为null,将访问默认的数据源配置
			<#if '${factoryTag}' !='' >
		    factoryTag = "${factoryTag}" ;
			</#if>
			int count = ${firstLowerPoName}Dao.getObjectCount(object,factoryTag);
			if(count < 1){
				throw new CommonException(CommonResultConstant.CommonResult.GET_RESULT_COUNT_FAILD);
			}	
			return count;		
		}
		catch (Exception e) {
			throw new CommonException(CommonResultConstant.CommonResult.OPERATE_ACTION_IS_FAILD);
		}
	}
}
