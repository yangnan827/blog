package com.yangnan.blog.result;

public class AbstractResult<V> implements Result<V> {

    protected V data;

    protected Message message;

    protected AbstractResult(V data) {
        this.data = data;
    }

    protected AbstractResult(Message message, V data) {
        this.data = data;
        this.message = message;
    }

    protected AbstractResult(Message message) {
        this.message = message;
    }

    @Override
    public V getData() {
        return data;
    }

    @Override
    public Message getMessage() {
        return message;
    }


}
