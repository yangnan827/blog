package com.yangnan.blog.result;

import lombok.Builder;

@Builder
public class ReMessage implements Message {

    private int code;
    private String codeMsg;
    private String userMsg;

    public ReMessage() {
    }

    public ReMessage(int code, String codeMsg, String userMsg) {
        this.code = code;
        this.codeMsg = codeMsg;
        this.userMsg = userMsg;
    }

    public ReMessage(int code, String userMsg) {
        this.code = code;
        this.codeMsg = userMsg;
        this.userMsg = userMsg;
    }

    public ReMessage(MessageResult result) {
        this.code = result.getCode();
        this.codeMsg = result.getCodeMsg();
        this.userMsg = result.getUserMsg();
    }

    @Override
    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getCodeMsg() {
        return this.codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    @Override
    public String getUserMsg() {
        return this.userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }
}
