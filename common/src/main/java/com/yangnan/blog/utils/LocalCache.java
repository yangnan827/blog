package com.yangnan.blog.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class LocalCache {

    private static final ExecutorService executor = new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder()
            .setNameFormat("ExpireCacePool%d")
            .setUncaughtExceptionHandler((thread, throwable) -> {
                log.error("threadPool: %s occured error", thread.getName(), throwable);
            }).build());
    private static ReentrantLock lock = new ReentrantLock();

    private static ConcurrentHashMap<String, Node> cacheMap = new ConcurrentHashMap<>(200);

    private static PriorityQueue<Node> priorityQueue = new PriorityQueue<>(200);

    @PreDestroy
    public void shutDown() {
        executor.shutdown();
    }

    public Object add(String key, Object value, long ttl) {

        Assert.isTrue(StringUtils.hasLength(key), "key can't be empty");
        Assert.isTrue(ttl > 0, "ttl must greater than 0");
        long expireTime = System.currentTimeMillis() + ttl;
        lock.lock();
        try {
            Node node = new Node(key, value, expireTime);
            Object put = cacheMap.put(key, node);
            priorityQueue.add(node);
            if (put != null) {
                priorityQueue.remove(key);
                cacheMap.remove(key);
            }
            return null;
        } finally {
            lock.unlock();
        }

    }

    public Object get(String key) {
        Node node = cacheMap.get(key);
        return node == null ? null : node.value;
    }

    public Object remove(String key) {
        lock.lock();
        try {
            Node remove = cacheMap.remove(key);
            if (null != remove) {
                priorityQueue.remove(remove);
                return remove.value;
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }

    }

    private static class ExpireTask implements Runnable {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (true) {
                lock.lock();
                try {
                    Node peek = priorityQueue.peek();
                    if (null == peek || peek.expirTime > start) {
                        return;
                    }
                    cacheMap.remove(peek.key);
                    priorityQueue.poll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class Node implements Comparable<Node> {
        private long expirTime = 0;
        private String key;
        private Object value;

        public Node(String key, Object value, long expirTime) {
            this.key = key;
            this.value = value;
            this.expirTime = expirTime;
        }

        @Override
        public int compareTo(Node o) {
            long isExpire = this.expirTime - o.expirTime;

            if (isExpire == 0) {
                return 0;
            }
            if (isExpire > 0) {
                return 1;
            }
            if (isExpire < 0) {
                return -1;
            }
            return 0;
        }


    }
}
