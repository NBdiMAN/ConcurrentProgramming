package day3;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestCondition {
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    static AtomicInteger num = new AtomicInteger(0);
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                lock.lock();
                try {
                    try {
                        while (num.get() == 0){
                            System.out.println("消费者等等..");
                            condition.await();
                        }
                        num.decrementAndGet();
                        condition.signalAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "is eating!");
                }finally {
                    lock.unlock();
                }

            },"消费者").start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                lock.lock();
                try {
                    try {
                        while (num.get() == Integer.MAX_VALUE){
                            System.out.println("生成者等等..");
                            condition.await();
                        }
                        num.incrementAndGet();
                        condition.signalAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "is cooking!");
                }finally {
                    lock.unlock();
                }

            },"生产者").start();
        }


    }
}
