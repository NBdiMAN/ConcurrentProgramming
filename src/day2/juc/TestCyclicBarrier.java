package day2.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(10,()->{
        System.out.println("执行业务方法................");
    });


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + "准备中！");
                    cyclicBarrier.await();//调用parties个await()之后，说明线程都到达了屏障，此时所有线程被唤醒！
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "执行初始化方法！");
            },String.valueOf(i)).start();
        }
    }

}
