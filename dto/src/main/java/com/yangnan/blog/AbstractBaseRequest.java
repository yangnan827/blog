package com.yangnan.blog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yangnan.blog.helper.JsonAsStringDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractBaseRequest {

    private RequestParamHeader header;

    @JsonDeserialize(using = JsonAsStringDeserializer.class)
    private String body;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RequestParamHeader {
        private String token;
        private String memberId;
        private String roleId;

    }

}
