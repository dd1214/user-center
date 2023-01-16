package com.jidian.usercenter.exception;

import com.jidian.usercenter.common.ErrorCode;

/**
 * 自定义异常类
 *
 * @author 梁钊炜
 */

public class BusinessException extends RuntimeException {

    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMassage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode,String description){
        super(errorCode.getMassage());
        this.code = errorCode.getCode();
        this.description = description;

    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
