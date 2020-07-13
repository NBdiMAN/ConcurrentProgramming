package day4;

/**
 * @author wangyang
 * @date 2020/7/12 16:53
 * @description: 测试自己实现的简单独占锁
 */
public class TestSelfLock {
    private static SelfLock selfLock = new SelfLock();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                selfLock.lock();
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " is running!");
                }catch (InterruptedException e){
                  e.printStackTrace();
                } finally {
                    selfLock.unlock();
                }
            }).start();
        }
    }
}
