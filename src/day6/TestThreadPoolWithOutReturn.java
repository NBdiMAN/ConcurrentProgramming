package day6;

import java.util.concurrent.*;

/**
 * @author wangyang
 * @date 2020/7/22 23:41
 * @description:
 */
public class TestThreadPoolWithOutReturn {
    public static void main(String[] args) {
        ExecutorService pool = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
            pool.execute(thread);
        }
        pool.shutdown();

    }
}
