package day6;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangyang
 * @date 2020/7/26 21:53
 * @description:
 */
public class UseCompletionService {

    private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int TASK_COUNT = Runtime.getRuntime().availableProcessors() * 10;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        testByCompletion();
    }
    public static void testByCompletion() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        ExecutorService pool = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE,
                0, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        CompletionService<Integer> completionService = new ExecutorCompletionService(pool);
        for (int i = 0; i < TASK_COUNT; i++) {
            //这里需要用completionService去调用submit
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sleepTime = new Random().nextInt(1000);
                    Thread.sleep(sleepTime);
                    System.out.println("休眠时间：" + sleepTime + "ms");
                    return sleepTime;
                }
            });
        }


        for (int i = 0; i < TASK_COUNT; i++) {
            Integer sleepTime = completionService.take().get();
            count.addAndGet(sleepTime);
        }
        pool.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("线程总休眠时间：" + count.get() + "ms");
        System.out.println("总耗时：" + (end - start) + "ms");
    }
}
