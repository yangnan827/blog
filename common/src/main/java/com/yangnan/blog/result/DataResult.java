package com.yangnan.blog.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataResult<V> {

    private V data;

    private Integer code;
    private String codeMsg;
    private String userMsg;

}
