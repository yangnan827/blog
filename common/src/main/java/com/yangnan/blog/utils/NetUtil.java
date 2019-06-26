package com.yangnan.blog.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Enumeration;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by woodle on 16/6/27.
 */
public class NetUtil {
    public static final String LOCAL_HOST = "127.0.0.1";
    public static final String ANY_HOST = "0.0.0.0";
    private static final Logger log = LoggerFactory.getLogger(NetUtil.class);
    private static final int RND_PORT_START = 30000;

    private static final int RND_PORT_RANGE = 10000;

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    public static String getLocalAddress() {
        String localAddress = getLocalAddress0().getHostAddress();
        if (localAddress.contains("/")) {
            String[] addressArr = StringUtils.split(localAddress, '/');
            for (String address : addressArr) {
                if (IP_PATTERN.matcher(address).matches()) {
                    return address;
                }
            }
        }
        return localAddress;
    }

    public static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
            log.warn("Failed to retrieve ip address {}", e.getMessage(), e);
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e) {
                                    log.warn("Failed to retrieve ip address {} ", e.getMessage(), e);
                                }
                            }
                        }
                    } catch (Throwable e) {
                        log.warn("Failed to retrieve ip address {} ", e.getMessage(), e);
                    }
                }
            }
        } catch (Throwable e) {
            log.warn("Failed to retrieve ip address {} ", e.getMessage(), e);
        }
        log.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress())
            return false;
        String name = address.getHostAddress();
        return (name != null
                && !ANY_HOST.equals(name)
                && !LOCAL_HOST.equals(name)
                && IP_PATTERN.matcher(name).matches());
    }

    public static int getAvailablePort() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket();
            ss.bind(null);
            return ss.getLocalPort();
        } catch (IOException e) {
            return getRandomPort();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    private static int getRandomPort() {
        return RND_PORT_START + RANDOM.nextInt(RND_PORT_RANGE);
    }

    /**
     * 将ip转化为int
     */
    public static long convertIpToLong(String ip) {
        String[] ipArray = StringUtils.split(ip, '.');
        long ipInt = 0;

        try {
            for (int i = 0; i < ipArray.length; i++) {
                if (ipArray[i] == null || ipArray[i].trim().equals("")) {
                    ipArray[i] = "0";
                }
                if (new Integer(ipArray[i]) < 0) {
                    Double j = (double) (Math.abs(new Integer(ipArray[i])));
                    ipArray[i] = j.toString();
                }
                if (new Integer(ipArray[i]) > 255) {
                    ipArray[i] = "255";
                }
            }
            ipInt = new Long(ipArray[0]) * 256 * 256 * 256 + new Long(ipArray[1]) * 256 * 256
                    + new Long(ipArray[2]) * 256 + new Long(ipArray[3]);
        } catch (Exception e) {
            log.error("occurs exception", e);
        }
        return ipInt;
    }
}

