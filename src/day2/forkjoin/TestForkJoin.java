package day2.forkjoin;

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
        if(toIndex - fromIndex < THRESHOLD){
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
            return left.join() + right.join();
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        //同步调用
        Integer result = pool.invoke(new TestForkJoin(MakeArray.getArray(), 0, MakeArray.LENGTH - 1));
        System.out.println(result);
    }
}
