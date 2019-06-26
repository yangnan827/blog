package com.yangnan.blog.exception;

/**
 * 用于改变接口成功时返回前端的msg
 * <p>
 * 用于事务时务必添加为非回滚异常：
 * <p>
 * <b>@Transactional(noRollbackFor = SuccessMsgWrapException.class)</b>
 *
 * @author anzhe
 * @date 2019-02-16
 */
public class SuccessMsgWrapException extends RuntimeException {
    private String codeMsg;
    private String userMsg;
    private Object data;

    public SuccessMsgWrapException(String codeMsg, String userMsg) {
        this.codeMsg = codeMsg;
        this.userMsg = userMsg;
    }

    public SuccessMsgWrapException(String userMsg) {
        this.userMsg = userMsg;
    }

    public SuccessMsgWrapException(String userMsg, Object data) {
        this.userMsg = userMsg;
        this.data = data;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public Object getData() {
        return data;
    }
}
