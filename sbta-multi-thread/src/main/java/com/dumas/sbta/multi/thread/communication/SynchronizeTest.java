package com.dumas.sbta.multi.thread.communication;

import java.util.ArrayList;

/**
 * @author dumas
 * @desc TODO
 * @date 2022/06/30 17:27
 */
public class SynchronizeTest {
    public static void main(String[] args) {
        // 定义一个锁对象
        Object lock = new Object();
        ArrayList<String> list = new ArrayList<>();
        // 线程A
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                for (int i = 1; i <= 10; i++) {
                    list.add("abc");
                    System.out.println("线程A添加元素，此时的list的size为： " + list.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (list.size() == 5) {
                        lock.notify();
                    }
                }
            }
        });
        // 线程B
        Thread threadB = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (list.size() != 5) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("线程B收到通知，开始执行自己的业务...");
                }
            }
        });
        // 需要先启动线程B
        threadB.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 再启动线程A
        threadA.start();
    }
}
