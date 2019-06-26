package com.yangnan.blog.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/**
 * 用于对接魔蝎科技获取运营商分析数据
 *
 * @author anzhe
 * @date 2019-02-23
 */
@Slf4j
@Component
public class MoXieUtil {

    private static String API_KEY;
    private static String TOKEN;
    private static final String SANDBOX_API_KEY = "602e2fd003874256836ec1cc83e2fd4e";
    private static final String SANDBOX_TOKEN = "e7d2439b25d94bc28f13d16f5165148d";
    private static final String PRODUCTION_API_KEY = "c0e609b06475430d9ea1a566f9f1a0b6";
    private static final String PRODUCTION_TOKEN = "3907dbee339f4d6b8cfc835baababfce";

    // 获取原始数据的链接 第一个%s为手机号 第二个%s为task_id
    private static String getOriginalDataUrl = "https://api.51datakey.com/carrier/v3/mobiles/%s/mxdata?task_id=%s";
    // 获取报告的链接 第一个%s为手机号 第二个%s为task_id 第三个%s为客户姓名 第四个%s为客户身份证号
    private static String getReportUrl = "https://api.51datakey.com/carrier/v3/mobiles/%s/mxreport?task_id=%s&name=%s&idcard=%s";

    @Value("${env}")
    private String env;

    @PostConstruct
    public void setApiKeyAndToken() {
        if ("pro".equals(env) || "pre".equals(env) || "org".equals(env)) {
            MoXieUtil.API_KEY = PRODUCTION_API_KEY;
            MoXieUtil.TOKEN = PRODUCTION_TOKEN;
        } else {
            MoXieUtil.API_KEY = SANDBOX_API_KEY;
            MoXieUtil.TOKEN = SANDBOX_TOKEN;
        }
    }

    // 获取原始数据
    public static String getOriginalData(String phoneNumber, String taskId) {
        String response;
        try {
            response = HttpClientUtil.sendGetRequestForMoXie(String.format(getOriginalDataUrl, phoneNumber, taskId), TOKEN);
            log.info("get token:{}, API_KEY:{}", TOKEN, API_KEY);
            // log.info("get moxie original response:{}", response);
        } catch (IOException e) {
            log.error("Get moxie original data for {} failed: {}", phoneNumber, e);
            return null;
        }
        return verifyResponse(response);
    }

    // 获取报告
    public static String getReport(String phoneNumber, String taskId, String name, String idNumber, String contactInfo) {
        String response;
        try {
            String requestUrl = String.format(getReportUrl, phoneNumber, taskId, name, idNumber);
            if (contactInfo != null) {
                requestUrl = requestUrl + "&contact=" + URLEncoder.encode(contactInfo, "UTF-8");
            }
            response = HttpClientUtil.sendGetRequestForMoXie(requestUrl, TOKEN);
        } catch (IOException e) {
            log.error("Get moxie report for {} failed: {}", phoneNumber, e);
            return null;
        }
        return verifyResponse(response);
    }

    // 获取报告
    public static String getReport(String phoneNumber, String taskId, String name, String idNumber) {
        String response;
        try {
            String requestUrl = String.format(getReportUrl, phoneNumber, taskId, name, idNumber);
            response = HttpClientUtil.sendGetRequestForMoXie(requestUrl, TOKEN);
            // log.info("get report response:{} ", response);
        } catch (IOException e) {
            log.error("Get moxie report for {} failed: {}", phoneNumber, e);
            return null;
        }
        return verifyResponse(response);
    }

    /**
     * 校验返回结果
     *
     * @author anzhe
     * @date 2019-04-26
     */
    private static String verifyResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(response);
        } catch (IOException e) {
            log.error("verify moxie response occurred an error: ", e);
            return null;
        }
        JsonNode status = rootNode.findPath("status");
        if (status.isMissingNode()) {
            return response;
        }
        return null;
    }

    // 解压返回的数据
    private static String uncompress(InputStream gzippedResponse) throws IOException {
        if (gzippedResponse == null) {
            throw new IOException("InputStream null");
        }

        InputStream decompressedResponse = new GZIPInputStream(gzippedResponse);
        Reader reader = new InputStreamReader(decompressedResponse, StandardCharsets.UTF_8);
        StringWriter writer = new StringWriter();

        char[] buffer = new char[10240];
        for (int length = 0; (length = reader.read(buffer)) > 0; ) {
            writer.write(buffer, 0, length);
        }

        writer.close();
        reader.close();
        decompressedResponse.close();
        gzippedResponse.close();

        return writer.toString();
    }

}
