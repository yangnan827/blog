package com.yangnan.blog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OSSResponse {
    private String downloadUrl;

    private List<String> downloadUrls;

    private Map<String, Object> params;
}
