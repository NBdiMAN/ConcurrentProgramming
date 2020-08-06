package day9.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangyang
 * @date 2020/8/3 22:06
 * @description:提交给框架执行的工作实体类
 */
public class JobInfo<R> {
    //区分唯一的工作
    private final String jobName;
    //工作的任务个数
    private final int jobLength;



    //这个工作任务的处理器
    private final ITaskProcesser<?, ?> taskProcesser;

    private AtomicInteger successCount;

    private AtomicInteger taskProcesserCount;

    /**
     * 拿结果从头拿，从结尾放,处理结果队列
     */
    private LinkedBlockingDeque<TaskResult<R>> taskDetailQueue;

    private final long expireTime;

    public JobInfo(String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser,
                     long expireTime) {
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.taskProcesser = taskProcesser;
        this.successCount = new AtomicInteger(0);
        this.taskProcesserCount = new AtomicInteger(0);
        this.taskDetailQueue = new LinkedBlockingDeque<>(jobLength);
        this.expireTime = expireTime;
    }

    public ITaskProcesser<?, ?> getTaskProcesser() {
        return taskProcesser;
    }
    public int getSuccessCount() {
        return successCount.get();
    }

    public int getTaskProcesserCount() {
        return taskProcesserCount.get();
    }

    public int getFailCount(){
        return taskProcesserCount.get() - successCount.get();
    }

    public String getTotalProcess(){
        return "Success[" + successCount.get() + "]/Currentt[" + taskProcesserCount.get() + "] Total[" + jobLength + "]";
    }

    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> taskResultList = new LinkedList<>();
        TaskResult<R> taskResult;
        while ((taskResult=taskDetailQueue.pollFirst()) != null){
            taskResultList.add(taskResult);
        }
        return taskResultList;
    }

    /**
     * 都是并发安全的操作，只需要保证最终一致性即可，可能中间数据不一致，比如successCount > taskProcesserCount
     * @param taskResult
     */
    public void addTaskResult(TaskResult<R> taskResult){
        if(TaskResultType.SUCCESS.equals(taskResult.getResultType())){
            successCount.incrementAndGet();
        }
        taskDetailQueue.addLast(taskResult);
        taskProcesserCount.incrementAndGet();
    }
}
