package com.dumas.sbta.multi.thread.communication;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author dumas
 * @desc TODO
 * @date 2022/06/30 17:34
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ArrayList<String> list = new ArrayList<>();
        // 线程A
        Thread threadA = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                list.add("abc");
                System.out.println("线程A添加元素，此时list的size为： " + list.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (list.size() == 5) {
                    countDownLatch.countDown();
                }
            }
        });
        // 线程B
        Thread threadB = new Thread(() -> {
            while (true) {
                if (list.size() != 5) {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("线程B收到通知，开始执行自己的业务...");
                break;
            }
        });
        // 需要先启动线程B
        threadB.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 启动线程A
        threadA.start();
    }
}
