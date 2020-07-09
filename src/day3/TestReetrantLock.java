package day3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReetrantLock {
    //使用非公平锁
    static Lock unfairLock = new ReentrantLock(false);
    //使用公平锁
    static Lock fairLock = new ReentrantLock(true);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                unfairLock.lock();
                try{
                    System.out.println(Thread.currentThread().getName() + "is running!");
                }finally {
                    unfairLock.unlock();
                }
            },String.valueOf(i)).start();
        }
        System.out.println("===============================");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                fairLock.lock();
                try{
                    System.out.println(Thread.currentThread().getName() + "is running!");
                }finally {
                    fairLock.unlock();
                }
            },String.valueOf(i)).start();
        }
    }
}
