package com.application.base.core.apisupport.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.application.base.core.apisupport.SingleStrutsBaseDao;
import com.application.base.core.constant.Constants;
import com.application.base.core.datasource.impl.cache.DefaultCacheReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.param.CustomSQL;
import com.application.base.core.datasource.param.ESQLOperator;
import com.application.base.core.datasource.param.Param;
import com.application.base.core.datasource.param.ParamBuilder;
import com.application.base.core.datasource.param.SQLCreator;
import com.application.base.core.exception.BusinessException;
import com.application.base.core.obj.Pagination;

@Service
public class SingleCacheStrutsBaseDaoImpl<T> implements SingleStrutsBaseDao<T> {

	@Resource
	protected DefaultCacheReadAndWriteDataSessionFactory factory;

	/**
	 * PO 的分页方式.
	 */
	@Override
	@SuppressWarnings("hiding")
	public <T> Pagination<T> queryClassPagination(Class<T> clazz, Param param, int pageNo, int pageSize)
			throws BusinessException {
        if(param == null){
        	param = ParamBuilder.getInstance().getParam();
        }
		param.add(ParamBuilder.nv(Constants.SQLConstants.PAGE_NO, pageNo));
		// 如果每页显示的数据为0时，不需要进行分析
		if (pageSize > 0) {
			param.add(ParamBuilder.nv(Constants.SQLConstants.PAGE_SIZE, pageSize));
		}

		int count = 0;
		List<T> list = factory.getReadDataSession().queryListResult(clazz, param);
		// 如果list为空则没有必须再查询总条数
		if (list != null && list.size() > 0) {
			count = factory.getReadDataSession().queryListResultCount(clazz, param);
		}
		Pagination<T> pageResult = new Pagination<T>(list, pageNo, pageSize);
		// 如果总条数为零则不需要设置初始化数值
		if (count > 0L) {
			pageResult.setRowCount(count);
		}
		return pageResult;
	}

	/**
	 * VO 的分页查询方式
	 * @param listElementId:查询list集合的select标签id
	 * @param countElementId:查询list集合size的select标签id
	 */
	@Override
	@SuppressWarnings("hiding")
	public <T> Pagination<T> queryClassPagination(Class<T> clazz, String listElementId, String countElementId,
			Param param, int pageNo, int pageSize) throws BusinessException {
		param.add(ParamBuilder.nv(Constants.SQLConstants.PAGE_NO, pageNo));
		// 如果每页显示的数据为0时，不需要进行分析
		if (pageSize > 0) {
			param.add(ParamBuilder.nv(Constants.SQLConstants.PAGE_SIZE, pageSize));
		}

		int count = 0;
		List<T> list = factory.getReadDataSession().queryVOListByCustomElementName(clazz,listElementId,param);
		// 如果list为空则没有必须再查询总条数
		if (list != null && list.size() > 0) {
			count = factory.getReadDataSession().querySingleVOByCustomElementName(Integer.class,countElementId, param);
		}
		Pagination<T> pageResult = new Pagination<T>(list, pageNo, pageSize);
		// 如果总条数为零则不需要设置初始化数值
		if (count > 0L) {
			pageResult.setRowCount(count);
		}
		return pageResult;
	}
	
	
	/**
	 * 自定义的 whereSQL 查询分页操作.
	 */
	@Override
	@SuppressWarnings("hiding")
	public <T> Pagination<T> queryClassPagination(Class<T> clazz,CustomSQL whereSQL, int pageNo, int pageSize) throws BusinessException {
		if (whereSQL == null){
			whereSQL = SQLCreator.where();
		}
		int count = 0;
		Pagination<T> pageResult = null;
		// 如果每页显示的数据为0时，不需要进行分页
		if (pageSize > 0) {
			count = factory.getReadDataSession().queryListResultCountByWhere(clazz, whereSQL);
			whereSQL.operator(ESQLOperator.LIMIT).value((pageNo - 1) * pageSize).operator(ESQLOperator.COMMA).value(pageSize);
			List<T> list = factory.getReadDataSession().queryListResultByWhere(clazz, whereSQL);
			pageResult = new Pagination<T>(list, pageNo, pageSize);
			// 如果总条数为零则不需要设置初始化数值
			if (count > 0L) {
				pageResult.setRowCount(count);
			}
		}
		return pageResult;
	}
	
}
