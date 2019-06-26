package com.yangnan.blog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class OperatorReportJsonUtil {
    public static final String NON_EXIST_STR = "--";
    public static final int NON_EXIST_INT = 0;
    public static final double NON_EXIST_DOUBLE = 0;
    public static final String NON_EXIST_STR_OTHER = "--";
    public static final String TIME_FORMAT = "1999-12-01 00:00:00";

    private static ObjectMapper objectMapper = new ObjectMapper();

    // 将空字符串及null替换为默认值
    public static String replaceNullStr(String str) {
        if (null == str || "".equals(str)) {
            str = NON_EXIST_STR;
        }
        return str;
    }

    // 将null替换为默认数字
    public static Integer replaceNullInteger(Integer i) {
        return i == null ? NON_EXIST_INT : i;
    }

    // 将null替换为默认数字
    public static Double replaceNullDouble(Double d) {
        return d == null ? NON_EXIST_DOUBLE : d;
    }

    // 将null替换为含0个元素的List
    public static List replaceNullList(List list) {
        return list == null ? new ArrayList() : list;
    }

}
