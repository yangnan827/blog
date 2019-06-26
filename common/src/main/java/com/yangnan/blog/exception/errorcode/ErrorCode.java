package com.yangnan.blog.exception.errorcode;

public interface ErrorCode {


    default String appendMsg(String name) {
        return String.format(this.getMsgFormat(), name);
    }


    default String appendCode(String name) {
        return String.format(this.getErrorCodeFormat(), name);
    }

    String getMsgFormat();

    String getErrorCodeFormat();

    String getMsg();

    String getErrorCode();

}
