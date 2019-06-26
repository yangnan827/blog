package com.yangnan.blog.utils;


import org.springframework.util.Assert;

/**
 * Created by woodle on 16/6/17.
 */
public class IDWorker {

    public static final long WORKER_ID_BITS = 10L;
    public static final long SEQUENCE_BITS = 12L;
    public static final long BEGIN_TIME = 1320681600000L; // 2011-11-08
    private static volatile IDWorker instance;
    public final long WORKER_ID;
    private long lastTimeDelta = System.currentTimeMillis() - BEGIN_TIME;
    private long sequence = 0L;
    private IDWorker(long workerId) {
        int maxId = 1 << WORKER_ID_BITS;
        Assert.isTrue(workerId < maxId && workerId >= 0, String.format("当前workId=%s, 不在[0,%s)中!", workerId, maxId));
        WORKER_ID = workerId;
    }

    public static IDWorker getInstance() {
        return getInstance(0);
    }

    /**
     * @param workerId 范围是 [0, 1024)
     */
    public static IDWorker getInstance(long workerId) {
        if (instance == null) {
            synchronized (IDWorker.class) {
                if (instance == null) {
                    instance = new IDWorker(workerId);
                }
            }
        }
        return instance;
    }

    public synchronized long nextId() throws InterruptedException {
        if (sequence >= (1 << SEQUENCE_BITS)) {
            // 允许时钟倒退 2000 ms,  即, 0 <= lastTimeDelta - newTimeDelta <= 2000 ms 成立,
            // 并等待到 lastTimeDelta - newTimeDelta < 0 成立
            while (true) {
                long newTimeDelta = System.currentTimeMillis() - BEGIN_TIME;
                long delta = lastTimeDelta - newTimeDelta;
                if (delta == 0) {
                    continue;// 忙等
                } else if (delta < 0) {
                    // newTimeDelta > lastTimeDelta 成立
                    sequence = 0;
                    lastTimeDelta = newTimeDelta;
                    break;
                } else if (delta > 2000) {// 误差太大, 不等待
                    throw new RuntimeException("ID生成器检测到时钟倒退大于 2000 ms!");
                }
                Thread.sleep(delta);
            }
        }
        return (lastTimeDelta << (WORKER_ID_BITS + SEQUENCE_BITS)) | (WORKER_ID << SEQUENCE_BITS) | sequence++;
    }
}

