package com.yangnan.blog.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

// JSON deserializer for JSON String to pojo field of type: string
public class JsonAsStringDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        TreeNode tree = p.getCodec().readTree(p);
        return tree.toString();
    }
}
