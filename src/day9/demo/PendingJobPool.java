package day9.demo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author wangyang
 * @date 2020/8/6 20:52
 * @description:框架的主体类，也是调用者主要使用的类
 */
public class PendingJobPool {

    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<Runnable>(5000);

    /**
     * 线程池，固定大小，有界队列
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(THREAD_COUNT, THREAD_COUNT, 60, TimeUnit.SECONDS,taskQueue);
    /**
     * 工作Job的存放容器
     */
    private static ConcurrentHashMap<String,JobInfo<?>> jobInfoMap = new ConcurrentHashMap<>();

    private static CheckJobProcesser checkJobProcesser = CheckJobProcesser.getInstance();

    private PendingJobPool(){}

    private static class JobPoolHolder{
        private static PendingJobPool pendingJobPool = new PendingJobPool();
        public static PendingJobPool getPendingJobPool(){
            return pendingJobPool;
        }
    }

    /**
     * 获取工作池的单例
     * @return
     */
    public static PendingJobPool getInstance(){
        return JobPoolHolder.getPendingJobPool();
    }

    public static Map<String,JobInfo<?>> getMap(){
        return jobInfoMap;
    }

    /**
     * 注册工作，将工作放入存放容器中
     * @param jobName
     * @param jobLength
     * @param taskProcesser
     * @param expireTime
     * @param <R>
     */
    public <R> void registerJob(String jobName, int jobLength,ITaskProcesser<?,?> taskProcesser, long expireTime){
        JobInfo<R> jobInfo = new JobInfo<>(jobName, jobLength, taskProcesser, expireTime);
        if(jobInfoMap.putIfAbsent(jobName, jobInfo) != null){
            throw new RuntimeException(jobName + "已经注册了！");
        }
    }

    /**
     * 调用者提交工作中的任务
     * @param jobName
     * @param t
     * @param <T>
     * @param <R>
     */
    public <T, R> void putTask(String jobName, T t){
        JobInfo<R> job = getJob(jobName);
        PendingTask<T,R> pendingTask = new PendingTask<>(job, t);
        executorService.execute(pendingTask);
    }

    /**
     * 获取每个任务的处理详情
     * @param jobName
     * @param <R>
     * @return
     */
    public <R> List<TaskResult<R>> getTaskDetail(String jobName){
        JobInfo<R> job = getJob(jobName);
        return job.getTaskDetail();
    }

    public <R> String getTaskProgress(String jobName){
        JobInfo<R> job = getJob(jobName);
        return job.getTotalProcess();
    }

    /**
     * 根据任务名查找任务
     * @param jobName
     * @param <R>
     * @return
     */
    private <R> JobInfo<R> getJob(String jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        if (null == jobInfo){
            throw new RuntimeException( jobName + "是个非法任务！");
        }
        return jobInfo;
    }


    private static class PendingTask<T,R> implements Runnable{
        /**
         * 任务信息
         */
        private JobInfo<R> jobInfo;
        /**
         * 要处理的数据
         */
        private T processData;

        public PendingTask(JobInfo<R> jobInfo, T processData) {
            this.jobInfo = jobInfo;
            this.processData = processData;
        }

        @Override
        public void run() {
            R r = null;
            ITaskProcesser<T, R> taskProcesser = (ITaskProcesser<T, R>) jobInfo.getTaskProcesser();
            //调用业务人员实现的具体方法
            TaskResult<R> result = null;
            try {
                result = (TaskResult<R>) taskProcesser.taskExecute(processData);
                //进行检查，防止开发人员处理不当
                if(result == null){
                    result = new TaskResult(TaskResultType.EXCEPTION, processData
                            ,"result is null");
                }
                if(result.getResultType() == null){
                    if(result.getReason() == null){
                        result = new TaskResult(TaskResultType.EXCEPTION, processData
                                ,"result is null");
                    }else{
                        result = new TaskResult(TaskResultType.EXCEPTION, processData
                                ,"result is null but reason:" + result.getReason());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                result = result = new TaskResult(TaskResultType.EXCEPTION, processData
                        ,e.getMessage()
                );
            }finally {
                jobInfo.addTaskResult(result, checkJobProcesser);
            }
        }
    }
}
