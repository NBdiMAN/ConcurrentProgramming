package day2.juc;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {
    private static CountDownLatch countDownLatch = new CountDownLatch(10);

    public static void main(String[] args) {
        System.out.println("进入主线程!");
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                System.out.println("线程" + Thread.currentThread().getName() + "已完成");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        new Thread(()->{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("业务线程...............");
        }).start();
        for (int i = 5; i < 10; i++) {
            new Thread(()->{
                System.out.println("线程" + Thread.currentThread().getName() + "已完成");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

    }
}
