package com.yangnan.blog.utils;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import com.yangnan.blog.result.Result;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class SendResponseUtil {
    public static void sendResponse(HttpServletResponse resp, Result result) {
        try {
            resp.setContentType(ContentType.JSON.toString());
            resp.setCharacterEncoding("UTF-8");
            ServletOutputStream outputStream = resp.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            String jsonString = JSON.toJSONString(result);
            writer.write(jsonString);
            writer.flush();
            writer.close();
        } catch (IOException ignored) {

        }
    }
}
