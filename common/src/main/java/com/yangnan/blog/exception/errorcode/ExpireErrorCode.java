package com.yangnan.blog.exception.errorcode;

public enum ExpireErrorCode implements ErrorCode {

    ValidateCode;
    public String msg;
    public String code;
    private String msgFormat = "%s is expire";
    private String codeFormat = "%s_IsExpire";

    ExpireErrorCode() {
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
