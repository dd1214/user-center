package com.jidian.usercenter.common;

/**
 * 错误码
 *
 * @author 梁钊炜
 */
public enum ErrorCode {

    SUCCESS(0,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"没有权限",""),
    SYSTEM_ERROR(50000,"系统内部异常","");

    private final int code;

    /**
     * 状态码信息
     */

    private final String massage;

    /**
     * 状态码描述（详细）
     */

    private final String description;

    ErrorCode(int code, String massage, String description) {
        this.code = code;
        this.massage = massage;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMassage() {
        return massage;
    }

    public String getDescription() {
        return description;
    }
}
