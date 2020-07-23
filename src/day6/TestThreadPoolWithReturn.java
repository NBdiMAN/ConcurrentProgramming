package day6;

import java.util.concurrent.*;

/**
 * @author wangyang
 * @date 2020/7/23 0:03
 * @description:
 */
public class TestThreadPoolWithReturn {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 20; i++) {
            Callable<String> callable = new Callable() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName());
                    return Thread.currentThread().getName();
                }
            };
            Future<String> stringFuture = pool.submit(callable);
            //加上这行代码后，发现只使用了核心大小个线程，原因是Future的get()是一个阻塞方法，for循环进入不了下一次，所以get()之前一般要使用isDone来判断任务是否完成
            String s = stringFuture.get();
            System.out.println("out:"+s);
        }
        pool.shutdown();

    }
}
