package day9.test;

import day9.demo.PendingJobPool;
import day9.demo.TaskResult;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangyang
 * @date 2020/8/9 0:46
 * @description: 模拟一个需要执行批量任务的应用，提交任务给并发框架，并查询任务进度
 */
public class AppTest {
    private final static String JOB_NAME = "数值计算";

    private final static int JOB_LENGTH = 1000;

    /**
     * 查询任务进度的线程
     */
    private static class QueryResult implements Runnable{
        private static PendingJobPool pool = PendingJobPool.getInstance();

        private String jobName;

        public QueryResult(String jobName) {
            this.jobName = jobName;
        }

        @Override
        public void run() {
            int i = 0;
            while (i < 350){
                List<TaskResult<Integer>> taskResults = pool.getTaskDetail(jobName);
                if(!taskResults.isEmpty()){
                    System.out.println(pool.getTaskProgress(jobName));
                    System.out.println(i);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    public static void main(String[] args) {
        PendingJobPool pendingJobPool = PendingJobPool.getInstance();
        pendingJobPool.registerJob(JOB_NAME, JOB_LENGTH, new MyTask(), 5000);
        Random r = new Random();
        for (int i = 0; i < JOB_LENGTH; i++) {
            pendingJobPool.putTask(JOB_NAME, r.nextInt(1000));
        }
        Thread t  = new Thread(new QueryResult(JOB_NAME));
        t.setDaemon(true);
        t.start();
    }
}
