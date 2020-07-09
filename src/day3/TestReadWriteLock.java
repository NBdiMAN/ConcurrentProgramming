package day3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();
    private static class Employee{
        private String name;

        public String getName() {
            readLock.lock();
            try {
                return name;
            }finally {
                readLock.unlock();
            }

        }

        public void setName(String name) {

            writeLock.lock();
            try {
                this.name = name;
            }finally {
                writeLock.unlock();
            }
        }
    }
    public static void main(String[] args) {
        Employee employee = new Employee();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                long start = System.currentTimeMillis();
                for (int j = 0; j < 10; j++) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    employee.getName();
                }
                System.out.println(Thread.currentThread().getName() + "读锁耗时：" + (System.currentTimeMillis() - start));
            }, String.valueOf(i)).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                long start = System.currentTimeMillis();
                for (int j = 0; j < 10; j++) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    employee.setName(String.valueOf(j));
                }
                System.out.println(Thread.currentThread().getName() + "写锁耗时：" + (System.currentTimeMillis() - start));
            }, String.valueOf(i)).start();
        }
    }
}
