package com.yangnan.blog.result;

import com.yangnan.blog.exception.BusinessException;
import com.yangnan.blog.exception.SuccessMsgWrapException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author 张涛
 */
@Slf4j
public class ResultBuilder {

    private static Map defaultMap = new HashMap(0);

    public static <V> Result execute(Callable<V> callable) {
        try {
            V call = callable.call();
            if (call instanceof DataResult) {
                DataResult dr = (DataResult) call;
                Integer code = dr.getCode() == null ? MessageResult.Success.getCode() : dr.getCode();
                String codeMsg = dr.getCodeMsg() == null ? MessageResult.Success.getCodeMsg() : dr.getCodeMsg();
                String userMsg = dr.getUserMsg() == null ? MessageResult.Success.getUserMsg() : dr.getUserMsg();
                ReMessage message = new ReMessage(code, codeMsg, userMsg);
                return wrapResult(new AbstractResult(message, dr.getData()));
            } else {
                return success(call);
            }
        } catch (Throwable e) {
            return failed(e);
        }
    }

    public static <V> Result execute(Callable<V> callable, String userMsg) {
        try {
            ReMessage message = new ReMessage(MessageResult.Success.getCode(), MessageResult.Success.getCodeMsg(), userMsg);
            return wrapResult(new SuccessResult<>(message, callable.call()));
        } catch (Throwable e) {
            return failed(e);
        }
    }

    public static <V> Result<V> success(V v) {
        log.debug("execute controller action success.");
        return wrapResult(new SuccessResult<>(MessageResult.success(), v));
    }

    public static <V> Result<V> failed(Throwable e) {
        if (e instanceof SuccessMsgWrapException) {
            SuccessMsgWrapException s = (SuccessMsgWrapException) e;
            log.debug("execute controller action success.");
            ReMessage reMessage = ReMessage.builder()
                    .code(MessageResult.Success.getCode())
                    .codeMsg(s.getCodeMsg() == null ? MessageResult.success().getCodeMsg() : s.getCodeMsg())
                    .userMsg(s.getUserMsg() == null ? MessageResult.success().getUserMsg() : s.getUserMsg())
                    .build();
            if (s.getData() != null) {
                return wrapResult(new SuccessResult(reMessage, s.getData()));
            } else {
                return wrapResult(new SuccessResult(reMessage));
            }
        }

        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            log.warn("execute controller action occurred a business exception: {}; {};", businessException.getCodeMessage(), businessException.getUserMessage());

            if (StringUtils.isEmpty(businessException.getErrorCode())) {
                return wrapResult(new FailedResult<>(ReMessage.builder()
                        .code(MessageResult.Fail.getCode())
                        .codeMsg(businessException.getCodeMessage())
                        .userMsg(businessException.getUserMessage())
                        .build()));
            }

            if (businessException.getData() == null) {
                return wrapResult(new SuccessResult(new ReMessage(Integer.valueOf(businessException.getErrorCode()), businessException.getCodeMessage(), businessException.getUserMessage())));
            } else {
                return wrapResult(new SuccessResult(new ReMessage(Integer.valueOf(businessException.getErrorCode()), businessException.getCodeMessage(), businessException.getUserMessage()), businessException.getData()));
            }
        }

        log.error("execute controller action occurred an error: {}", e);
        return wrapResult(new FailedResult<>(ReMessage.builder()
                .code(MessageResult.Fail.getCode())
                .userMsg(MessageResult.Fail.getUserMsg())
                .codeMsg(MessageResult.Fail.getCodeMsg())
                .build()));
    }

    // 防止返回data为null及msg返回null
    private static Result wrapResult(AbstractResult result) {
        if (result.getData() == null) {
            result.data = defaultMap;
        }
        Message message = result.getMessage();
        if (message instanceof ReMessage) {
            ReMessage m = (ReMessage) message;
            if (m.getCodeMsg() == null) {
                m.setCodeMsg("");
            }
            if (m.getUserMsg() == null) {
                m.setUserMsg("");
            }
        }
        return result;
    }

}
