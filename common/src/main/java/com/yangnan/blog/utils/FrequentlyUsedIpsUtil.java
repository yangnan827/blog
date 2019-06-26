package com.yangnan.blog.utils;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.StringJoiner;

public class FrequentlyUsedIpsUtil {

    private static final String IP_DELIMITER_REGEX = "\\|";
    private static final String IP_DELIMITER = "|";

    private static final int MAX_COUNT_OF_FREQUENTLY_USED_IPS = 3;

    public static String addIp(String frequentlyUsedIps, String ipToAdd) {
        String[] Ips = frequentlyUsedIps.split(IP_DELIMITER_REGEX);

        Ips = Arrays.stream(Ips).filter(StrUtil::isNotBlank).toArray(String[]::new);

        if (Ips.length == 0)
            return ipToAdd;

        if (Ips.length > MAX_COUNT_OF_FREQUENTLY_USED_IPS)
            Ips = Arrays.copyOf(Ips, MAX_COUNT_OF_FREQUENTLY_USED_IPS);

        boolean alreadyExists = false;
        if (Arrays.stream(Ips).anyMatch(ipToAdd::equalsIgnoreCase))
            alreadyExists = true;

        StringJoiner stringJoiner = new StringJoiner(IP_DELIMITER);
        stringJoiner.add(ipToAdd);
        if (alreadyExists) {
            for (String ip : Ips) {
                if (ipToAdd.equalsIgnoreCase(ip))
                    continue;
                stringJoiner.add(ip);
            }
        } else {
            for (int i = 0; i < Ips.length; ++i) {
                if (i == MAX_COUNT_OF_FREQUENTLY_USED_IPS - 1)
                    break;
                stringJoiner.add(Ips[i]);
            }
        }
        return stringJoiner.toString();
    }

    public static boolean isFrequentlyUsedIp(String frequentlyUsedIps, String ipToJudge) {
        if (StrUtil.isBlank(ipToJudge))
            return false;

        String[] Ips = frequentlyUsedIps.split(IP_DELIMITER_REGEX);

        Ips = Arrays.stream(Ips).filter(StrUtil::isNotBlank).toArray(String[]::new);

        if (Ips.length == 0)
            return false;

        return Arrays.stream(Ips).anyMatch(ipToJudge::equalsIgnoreCase);
    }

}
