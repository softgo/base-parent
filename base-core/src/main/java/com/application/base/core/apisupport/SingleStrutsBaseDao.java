package com.application.base.core.apisupport;

import com.application.base.core.datasource.param.CustomSQL;
import com.application.base.core.datasource.param.Param;
import com.application.base.core.exception.BusinessException;
import com.application.base.core.obj.Pagination;

/**
 * 单数据源操作数据库
 * @param <T>
 */
public interface SingleStrutsBaseDao<T>{

	/**
	 * 查询列表数据，带分页功能
	 * @param clazz
	 * @param param
	 * @param pageNo
	 * @param pageSize
	 * @param factoryTag
	 * @return
	 */
	@SuppressWarnings("hiding")
	<T> Pagination<T> queryClassPagination(Class<T> clazz, Param param, int pageNo, int pageSize) throws BusinessException;

	/**
	 * 查询列表数据，带分页功能查询列表数据，带分页功能
	 * @param clazz
	 * @param listElementId 列表查询元素ID,在 custom.xml 文件中.
	 * @param countElementId 列表个数查询元素ID,在 custom.xml 文件中.
	 * @param param
	 * @param pageNo
	 * @param pageSize
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 * Pagination<S>
	 *
	 */
	@SuppressWarnings("hiding")
	<T> Pagination<T> queryClassPagination(Class<T> clazz, String listElementId, String countElementId, Param param,int pageNo, int pageSize) throws BusinessException;
	
	
	/**
	 * 根据自定义where条件sql分页查询
	 * 
	 * @param clazz
	 * @param whereSQL
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("hiding")
	public <T> Pagination<T> queryClassPagination(Class<T> clazz,CustomSQL whereSQL, int pageNo, int pageSize) throws BusinessException;
	
}
