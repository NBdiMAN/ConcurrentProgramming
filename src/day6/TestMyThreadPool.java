package day6;

/**
 * @author wangyang
 * @date 2020/7/21 23:31
 * @description:
 */
public class TestMyThreadPool {
    public static void main(String[] args) throws InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        MyThreadPool myThreadPool = new MyThreadPool(availableProcessors,3);
        while(true){
            Thread.sleep(500);
            myThreadPool.execute(new MyTask());
        }

    }

    private static class MyTask implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
