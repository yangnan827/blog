package com.yangnan.blog.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * 高德LBS
 *
 * @author anzhe
 * @date 2019-03-29
 */
@Slf4j
public class AmapLbsUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonNode EMPTY_JSON_NODE = JsonNodeFactory.instance.textNode("");

    private static final String API_KEY = "926597620c4145ce8fb95d81083d13bb";
    private static final String IP_TO_LOCATION_URL = "https://restapi.amap.com/v3/ip";

    private static final String SUCCESS_STATUS = "1";

    /**
     * 从IP地址获取地址(省和市)
     *
     * @author anzhe
     * @date 2019-04-01
     */
    public static String getLocationFromIP(String ip) {
        ip = validateIP(ip);
        if (ip == null) {
            return "";
        }

        String params = new StringBuilder().append("?")
                .append("key")
                .append("=")
                .append(API_KEY)
                .append('&')
                .append("ip")
                .append("=")
                .append(ip).toString();
        String responseStr;
        try {
            responseStr = HttpClientUtil.sendGetRequest(IP_TO_LOCATION_URL + params);
            log.info("get address from ip: {} response: {}", ip, responseStr);
        } catch (Exception e) {
            log.error("get address from ip: {} wrong: {}", ip, e.getMessage());
            return "";
        }

        JsonNode root;
        try {
            root = objectMapper.readTree(responseStr);
        } catch (IOException e) {
            log.error("get address from ip: {} wrong: {}", ip, e.getMessage());
            return "";
        }

        String status = Optional.ofNullable(root.get("status")).orElse(EMPTY_JSON_NODE).asText();
        if (StringUtil.lang_equals(SUCCESS_STATUS, status)) {
            JsonNode provinceNode = Optional.ofNullable(root.get("province")).orElse(EMPTY_JSON_NODE);
            JsonNode cityNode = Optional.ofNullable(root.get("city")).orElse(EMPTY_JSON_NODE);

            String province;
            String city;
            province = provinceNode.isArray() ? "" : provinceNode.asText();
            city = cityNode.isArray() ? "" : cityNode.asText();

            if (province.equals(city)) {
                return province;
            } else {
                return province + city;
            }
        } else {
            log.debug("get address from ip: {} wrong: {}", ip, Optional.ofNullable(root.get("info")).orElse(EMPTY_JSON_NODE));
            return "";
        }
    }

    public static String validateIP(String ip) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            log.error("get address from ip: {} wrong: not a valid IPv4 address", ip);
            return null;
        }

        if (inetAddress instanceof Inet6Address) {
            log.error("get address from ip: {} wrong: not a valid IPv4 address(it is IPv6 address)", ip);
            return null;
        }

        if (inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress() ||
                inetAddress.isSiteLocalAddress() || inetAddress.isLinkLocalAddress() ||
                inetAddress.isMulticastAddress()) {
            log.error("get address from ip: {} wrong: not a valid IPv4 address(special purpose IP address)", ip);
            return null;
        }
        return ip;
    }

}
