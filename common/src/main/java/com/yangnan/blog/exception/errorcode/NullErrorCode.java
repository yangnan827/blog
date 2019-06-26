package com.yangnan.blog.exception.errorcode;

public enum NullErrorCode implements ErrorCode {

    User,
    UserName,
    Password,
    ValidateCode;
    public String msg;
    public String code;
    private String msgFormat = "%s dose not exsit in our records";
    private String codeFormat = "%s_NotExisting";

    NullErrorCode() {
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
