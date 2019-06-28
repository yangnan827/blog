package com.yangnan.blog.utils;


import com.yangnan.blog.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by woodle on 16/6/17.
 */
public class IDWorkerUtil {
    private static final Logger log = LoggerFactory.getLogger(IDWorkerUtil.class);
    //    public static RedisUtil redisUtil;
    private static IDWorker worker;

    static {
        try {
            long ip = NetUtil.convertIpToLong(NetUtil.getLocalAddress());
            int port = NetUtil.getAvailablePort();
            long ipPort = Long.parseLong(String.valueOf(ip) + String.valueOf(port));
            worker = IDWorker.getInstance(ipPort % 1024);
        } catch (Exception ex) {
            log.error("generate id worker occurs exception", ex);
            throw new RuntimeException("generate id worker occurs exception");
        }
    }

    public static String getID() {
        try {
            return String.valueOf(worker.nextId());
        } catch (InterruptedException e) {
            try {
//                return String.valueOf(redisUtil.incr(Constant.RedisKeyPrefix.PLUS_ONE_KEY, 1));
                return null;
            } catch (Exception e1) {
                throw new BusinessException("Redis plus one occur ERROR ");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(IDWorkerUtil.getID());
        System.out.println(IDWorkerUtil.getID());
        System.out.println(IDWorkerUtil.getID());
        System.out.println(IDWorkerUtil.getID());
        System.out.println(IDWorkerUtil.getID());
        System.out.println(IDWorkerUtil.getID());
        System.out.println(IDWorkerUtil.getID());
        System.out.println(IDWorkerUtil.getID());
        System.out.println(IDWorkerUtil.getID());
    }
}
