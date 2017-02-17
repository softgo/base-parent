package com.application.base.core.exception;

import com.application.base.core.exception.BusinessException;

/**
 * 异常信息
 * @author rocky
 */
public class InfoEmptyException extends BusinessException {

    public InfoEmptyException() {
        super();
        super.exceptionKey = "INF_EMPTY";
    }

}
