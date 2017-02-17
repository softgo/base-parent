package com.application.base.core.exception;

import com.application.base.core.exception.BusinessException;

/**
 * 异常信息
 * @author rocky
 */
public class ValideErrorException extends BusinessException {

    public ValideErrorException() {
        super();
        super.exceptionKey="VALID_ERROR";
    }

}
