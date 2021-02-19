package com.jing.study.util.lock;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2021/2/18
 */
public class ReentrantReadWriteLockTest {
    private final static Map<String, String> m = new TreeMap<String, String>();
    private final static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final static Lock readLock = rwl.readLock();
    private final static Lock writeLock = rwl.writeLock();

    public static String get(String key) {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "进行读操作");
            return m.get(key);
        } catch (Exception e) {
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public static String put(String key, String value) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "进行写操作");
            TimeUnit.SECONDS.sleep(1);
            return m.put(key, value);
        } catch (Exception e) {
            return null;
        } finally {
            writeLock.unlock();
        }
    }

    public void clear() {
        writeLock.lock();
        try {
            m.clear();
        } catch (Exception e) {
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                put(String.valueOf(finalI), "value:" + finalI);
            }, "线程name:" + i).start();


        }
        System.out.println("写完毕=================================");
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                String s = get(String.valueOf(finalI));
                System.out.println(s);
            }, "线程name:" + i).start();


        }

    }
}
