package com.yangnan.blog.result;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class RpcResult<V> implements Serializable {

    private V data;

    private boolean success = true;

    private String requestId;

    private String errorCode;

    private String message;

}
