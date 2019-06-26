package com.yangnan.blog.result;


/**
 * @author 张涛
 */
public class SuccessResult<V> extends AbstractResult<V> {
    protected SuccessResult(V data) {
        super(data);
    }

    public SuccessResult(Message message, V data) {
        super(message, data);
    }

    public SuccessResult(Message message) {
        super(message);
    }

}
