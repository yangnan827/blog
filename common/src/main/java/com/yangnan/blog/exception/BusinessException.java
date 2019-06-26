package com.yangnan.blog.exception;

import com.yangnan.blog.result.MessageResult;
import com.yangnan.blog.utils.StringUtil;
import lombok.Data;


@Data
public class BusinessException extends RuntimeException {

    private String errorCode;

    private String codeMessage;

    private String userMessage;

    private Object data;

    public BusinessException(MessageResult messageResult) {
        this.errorCode = String.valueOf(messageResult.getCode());
        this.codeMessage = messageResult.getCodeMsg();
        this.userMessage = messageResult.getUserMsg();
    }

    public BusinessException(MessageResult messageResult, Object data) {
        this.errorCode = String.valueOf(messageResult.getCode());
        this.codeMessage = messageResult.getCodeMsg();
        this.userMessage = messageResult.getUserMsg();
        this.data = data;
    }

    public BusinessException(String errorCode, String codeMessage, String userMessage) {
        this.errorCode = errorCode;
        this.codeMessage = codeMessage;
        this.userMessage = userMessage;
    }

    /**
     * @param codeMessage
     * @param userMessage
     */
    public BusinessException(String codeMessage, String userMessage) {
        this.codeMessage = codeMessage;
        this.userMessage = userMessage;
    }

    /**
     * @param userMessage 用户提示信息
     */
    public BusinessException(String userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public String getMessage() {
        return StringUtil.isEmpty(userMessage) ? codeMessage : userMessage;
    }

}
