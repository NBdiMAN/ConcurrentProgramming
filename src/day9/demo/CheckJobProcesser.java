package day9.demo;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.DelayQueue;

/**
 * @author wangyang
 * @date 2020/8/7 23:22
 * @description: 任务完成后，在一定的时间内供查询，之后释放资源节约内存，需要定期处理过期的任务
 */
public class CheckJobProcesser {
    /**
     * 存放已完成的任务到延迟队列中，用于在一段时间内查询
     */
    private static DelayQueue<ItemVo<String>> queue = new DelayQueue<>();

    private CheckJobProcesser() {
    }

    private static class ProcessHolder {
        public static CheckJobProcesser checkJobProcesser = new CheckJobProcesser();
    }

    static {
        Thread thread = new Thread(new FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("开启清除过期完成任务的守护线程。。。。。");
    }

    /**
     * 得到单例的CheckJobProcesser
     *
     * @return
     */
    public static CheckJobProcesser getInstance() {
        return ProcessHolder.checkJobProcesser;
    }

    private static class FetchJob implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    ItemVo<String> itemVo = queue.take();
                    String jobName = itemVo.getData();
                    PendingJobPool.getMap().remove(jobName);
                    System.out.println(new Date().toLocaleString() + "从保留已完成任务信息的延迟队列中清除：" + jobName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param jobName    任务名称
     * @param expireTime 毫秒
     */
    public void putJob(String jobName, long expireTime) {
        ItemVo<String> itemVo = new ItemVo<>(expireTime, jobName);
        queue.add(itemVo);
        System.out.println(new Date().toLocaleString() + "将完成的任务放入延迟队列中保存：" + jobName);
    }


}
