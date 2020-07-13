package day4;

import java.util.concurrent.locks.Lock;

/**
 * @author wangyang
 * @date 2020/7/13 21:34
 * @description:
 */
public class TestTrinityLock {

    private static Lock trinityLock = new TrinityLock();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                trinityLock.lock();
                try{
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                }catch (Exception e){
                }finally {
                    trinityLock.unlock();
                }
            }).start();
        }
    }
}
