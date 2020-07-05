package day1;

import java.lang.management.ThreadInfo;

public class TestThreadLocal {
    private static  ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    private static class MyThread implements Runnable{
        @Override
        public void run() {
            Integer integer = threadLocal.get();
            System.out.println(Thread.currentThread().getName() + "：" + integer);
            integer++;
            threadLocal.set(integer);
            System.out.println(Thread.currentThread().getName() + "：" + integer);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new MyThread()).start();
        }
    }
}
