package day6;


import java.util.concurrent.ExecutorService;


import static java.util.concurrent.Executors.*;

/**
 * @author wangyang
 * @date 2020/7/22 23:28
 * @description:
 */
public class UseThreadPool {
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = newFixedThreadPool(2);
        ExecutorService singleThreadExecutor = newSingleThreadExecutor();
        ExecutorService cachedThreadPool = newCachedThreadPool();
        ExecutorService scheduledThreadPool = newScheduledThreadPool(5);
        ExecutorService workStealingPool = newWorkStealingPool();

    }
}
