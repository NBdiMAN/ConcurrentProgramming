package day2.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class TestForkJoin extends RecursiveTask<Integer> {
    private final static int THRESHOLD = MakeArray.LENGTH / 10;

    private int[] src;
    private int fromIndex;
    private int toIndex;
    public TestForkJoin(int[] src, int fromIndex, int toIndex){
        this.fromIndex = fromIndex;
        this.src = src;
        this.toIndex = toIndex;
    }
    @Override
    protected Integer compute() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 666;
        /*if(toIndex - fromIndex < THRESHOLD){
            System.out.println("执行。。。。。");
            int result = 0;
            for (int i = fromIndex; i <= toIndex; i++) {
                result += src[i];
            }
            return result;
        }else{
            System.out.println("拆分子任务");
            int mid = (toIndex + fromIndex) >> 1;
            TestForkJoin left = new TestForkJoin(src, fromIndex, mid);
            TestForkJoin right = new TestForkJoin(src, mid + 1, toIndex);
            left.invoke();
            right.invoke();
            //推荐使用invokeAll(left,right);
            return left.join() + right.join();
        }*/
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        TestForkJoin testForkJoin = new TestForkJoin(MakeArray.getArray(), 0, MakeArray.LENGTH - 1);
        //同步调用
        long start = System.currentTimeMillis();
        /*Integer result = pool.invoke(testForkJoin);
        System.out.println(result);*/
        System.out.println("================================");
        //异步调用，没有返回值
        pool.execute(testForkJoin);
        Thread.sleep(2000);
        System.out.println(testForkJoin.get());
        long end = System.currentTimeMillis();
        System.out.println(end - start);//同步5s，异步3秒
    }
}
