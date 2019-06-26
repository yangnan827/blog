package com.yangnan.blog.result;

import java.io.Serializable;

public interface Result<V> extends Serializable {

    V getData();

    Message getMessage();

}
