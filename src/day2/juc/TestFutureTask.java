package day2.juc;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestFutureTask {

    private static class MyCallable implements Callable<Integer>{

        private int sum;
        @Override
        public Integer call() throws Exception {
            try {
                Thread.sleep(2);
            }catch(InterruptedException exe){
                System.out.println("中断............");
                exe.printStackTrace();
            }
            for (int i = 0; i < 500; i++) {
                sum += i;
            }

            System.out.println("MyCallable sum：" + sum);
            return sum;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new MyCallable());
        new Thread(futureTask).start();
        Random random = new Random();
        boolean nextBoolean = random.nextBoolean();
        Thread.sleep(3000);
        if(nextBoolean){
            System.out.println("运行结果：" + futureTask.get());
        }else{
            futureTask.cancel(true);
        }
    }
}
