package com.yangnan.blog.result;


import com.alibaba.fastjson.JSON;
import com.yangnan.blog.constant.Constant;


public enum MessageResult implements Message {

    Success(0, "success", "请求成功！") {
        @Override
        public Result<Object> result(Object data) {
            return new SuccessResult(new ReMessage(Success), data);
        }
    },
    Fail(1, "error", "请求失败，请重试！") {
        @Override
        public Result<Object> result(Object data) {
            return new FailedResult(new ReMessage(Fail), data);
        }
    },
    /*** 权限模块***************************************/
    HAS_NOT_PRIVILEGE(10086, "The action is forbidden", "对不起，您尚未取得该操作权限！") {
        @Override
        public Result<Object> result(Object data) {
            return new FailedResult(new ReMessage(this.getCode(), this.getCodeMsg(), this.getUserMsg()), data);
        }
    },
    TokenWrong1(4, "token wrong", Constant.LoginConstants.TOKEN_WRONG_MSG) {
        @Override
        public Result<Object> result(Object data) {
            return new FailedResult(new ReMessage(this.getCode(), this.getCodeMsg(), this.getUserMsg()));
        }
    },
    ;
    private int code;

    private String codeMessage;

    private String userMessage;

    MessageResult(int code, String codeMessage, String userMessage) {
        this.code = code;
        this.codeMessage = codeMessage;
        this.userMessage = userMessage;
    }

    public static Message getMessageByCode(int code) {
        for (MessageResult result : MessageResult.values()) {
            if (code == result.code) {
                return new ReMessage(result);
            }
        }
        return null;
    }

    public static Message success() {
        return new ReMessage(Success);
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(Fail));
    }

    public abstract Result<Object> result(Object data);

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getCodeMsg() {
        return this.codeMessage;
    }

    @Override
    public String getUserMsg() {
        return this.userMessage;
    }

}
