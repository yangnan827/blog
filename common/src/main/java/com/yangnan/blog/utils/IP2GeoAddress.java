package com.yangnan.blog.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class IP2GeoAddress {

    public static String getAddressByIP(String strIP) {
        try {
            URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + strIP);
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(5 * 1000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
//            System.err.println(result.toString());
            if (StringUtils.isNotBlank(result.toString())) {
                TaobaoIPResponse parse = JSON.parseObject(result.toString(), TaobaoIPResponse.class);
                if (0 == parse.getCode()) {
                    String province = parse.getData().getRegion();
                    String country = parse.getData().getCountry();
                    String city = parse.getData().getCity();
                    String isp = parse.getData().getIsp();
                    return country + " " + province + " " + city + " " + isp;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
//        System.out.println(getAddressByIP("124.114.57.128"));
        System.out.println(getAddressByIP("171.108.233.157"));
    }
}

@Data
class TaobaoIPResponse {
    private Integer code;
    private TaobaoIPData data;

    @Data
    class TaobaoIPData {
        private String ip;
        private String country;
        private String region;
        private String city;
        private String isp;
    }
}