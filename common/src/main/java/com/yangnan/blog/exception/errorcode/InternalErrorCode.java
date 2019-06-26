package com.yangnan.blog.exception.errorcode;

public enum InternalErrorCode implements ErrorCode {

    InternalException;

    public String msg;
    public String code;
    private String msgFormat = "%s occured internal error";
    private String codeFormat = "%s_IsInternalError";

    InternalErrorCode() {
        this.msg = this.appendMsg(name());
        this.code = this.appendCode(name());
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
