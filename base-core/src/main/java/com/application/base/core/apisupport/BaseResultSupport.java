package com.application.base.core.apisupport;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.application.base.core.constant.Constants;
import com.application.base.core.exception.BusinessException;
import com.application.base.core.obj.BaseBean;
import com.application.base.core.obj.Pagination;
import com.application.base.core.result.MessageContext;
import com.application.base.core.result.ResultData;
import com.application.base.core.result.ResultInfo;
import com.application.base.utils.common.JSONUtils;

/**
 * 消息信息.
 */
public class BaseResultSupport extends CoreController{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    protected MessageContext context;

    /**
     * 根据结果消息容器键key，获取结果消息
     *
     * @param key
     * @return ResultInfo
     */
    protected ResultInfo getResultInfo(String key) {
        ResultInfo result = context.getResultInfo(key);
        return result == null ? context.getResultInfo(Constants.RESULT_INFO_NOT_FOUNT) : result;
    }

    /**
     * 根据结果消息容器键key，获得结果信息
     *
     * @param key
     * @return Result
     */
    @SuppressWarnings("rawtypes")
	protected ResultData getResult(String key) {
        return new ResultData(getResultInfo(key));
    }

    /**
     * 获得操作成功结果信息
     *
     * @return Result
     */
    @SuppressWarnings("rawtypes")
    protected ResultData getSuccessResult() {
        return new ResultData(getResultInfo(Constants.ResultStatus.RESULT_SUCCESS));
    }

    /**
     * 获得操作成功的结果信息
     *
     * @param result
     * @return Result
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected ResultData getSuccessResult(Object result) {
        ResultData res = new ResultData(getResultInfo(Constants.ResultStatus.RESULT_SUCCESS));
        res.setResult(result);
        return res;
    }

    /**
     * 获得操作成功结果信息的JSON串
     *
     * @return String
     */
    protected String successResultJSON() {
        return JSONUtils.toJson(getSuccessResult());
    }

    /**
     * 获得操作成功结果信息JSON串，带有返回的结果内容
     *
     * @param result
     * @return String
     */
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected String successResultJSON(Object result) {
		ResultData res = getSuccessResult();
        res.setResult(result);
        //改输出String字段null值为""
        String resultJson = JSONUtils.toJsonWithStringNull2Empty(res);
        logger.info("调用接口成功，返回JSON数据 resultJson:[{}]", resultJson);
        return resultJson;
    }

    /**
     * 获得操作成功结果信息JSON串，带有返回的结果内容,使用完整的时间表示形式
     *
     * @param result
     * @return String
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected String successResultJSONIncludeFullDate(Object result) {
        ResultData res = getSuccessResult();
        res.setResult(result);
        String resultJson = JSONUtils.toJson(result, null, true, null, "yyyy-MM-dd HH:mm:ss", false,false);
        logger.info("调用接口成功，返回JSON数据 resultJson:[{}]", resultJson);
        return resultJson;
    }

    /**
     * @param result
     * @param nullAble 是否序列化为null的字段
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected String successResultJSON(Object result, boolean nullAble) {
        ResultData res = getSuccessResult();
        res.setResult(result);
        String resultJson = JSONUtils.toJson(res, nullAble);
        logger.debug("调用接口成功，返回JSON数据 resultJson:[{}]", resultJson);
        return resultJson;
    }

    /**
     * 获得结果信息的JSON串
     *
     * @param result
     * @return String
     */
    @SuppressWarnings("rawtypes")
    protected String resultJSON(ResultData result) {
        return JSONUtils.toJson(result);
    }

    /**
     * 获得结果信息的JSON串
     *
     * @param resultKey
     * @return String
     */
    protected String resultJSON(String resultKey) {
        return JSONUtils.toJson(getResult(resultKey));
    }


    /**
     * 根据异常信息返回错误消息
     *
     * @param ex
     * @return String
     */
    @SuppressWarnings("rawtypes")
    protected String resultJSON(BusinessException ex) {
        ResultData result = null;
        if (!StringUtils.isEmpty(ex.getExceptionKey())) {
            result = getResult(ex.getExceptionKey());
        } else {
            result = getResult(Constants.ResultStatus.RESULT_SYSTEM_ERROR);
        }
        return JSONUtils.toJson(result);
    }

    /**
     * java顶级异常处理
     *
     * @param e
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected String resultJSON(Exception e) {
        ResultData result = null;
        BusinessException ex = new BusinessException(e);
        if (!StringUtils.isEmpty(ex.getExceptionKey())) {
            result = getResult(ex.getExceptionKey());
        } else {
            result = getResult(Constants.ResultStatus.RESULT_SYSTEM_ERROR);
        }
        return JSONUtils.toJson(result);
    }

    /**
     * 将Pagination对象转换为指定类型的Pagination对象
     *
     * @param pagination
     * @param <T>
     * @return
     */
    protected <T> Pagination<T> getVOPagination(Pagination<? extends BaseBean> pagination, VOTransfer<T> transfer) {
        List<T> data = new ArrayList<T>();
        for (BaseBean po : pagination.getData()) {
            data.add(transfer.trans(po));
        }
        Pagination<T> result = new Pagination<T>(data, pagination.getPageNo(), pagination.getPageSize());
        result.setPageCount(pagination.getPageCount());
        result.setRowCount(pagination.getRowCount());
        return result;
    }

    // po2VO
    protected interface VOTransfer<T> {
        T trans(BaseBean po);
    }

}
