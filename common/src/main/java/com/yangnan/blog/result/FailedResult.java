package com.yangnan.blog.result;


public class FailedResult<V> extends AbstractResult<V> {
    public FailedResult(V data) {
        super(data);
    }

    public FailedResult(Message message, V data) {
        super(message, data);
    }

    public FailedResult(Message message) {
        super(message);
    }
}
