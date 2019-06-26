package com.yangnan.blog.exception.errorcode;

public enum InvalidErrorCode implements ErrorCode {

    Password,
    ValidateCode;

    public String msg;
    public String code;
    private String msgFormat = "%s is invalid";
    private String codeFormat = "%s_IsInvalid";

    InvalidErrorCode() {
        this.msg = this.appendMsg(this.name());
        this.code = this.appendCode(this.name());
    }

    @Override
    public String getMsgFormat() {
        return msgFormat;
    }

    @Override
    public String getErrorCodeFormat() {
        return codeFormat;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getErrorCode() {
        return code;
    }

}
