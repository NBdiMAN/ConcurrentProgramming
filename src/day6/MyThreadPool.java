package day6;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author wangyang
 * @date 2020/7/21 7:39
 * @description: 实现自己的线程池
 */
public class MyThreadPool {
    /**
     * 线程池中默认线程个数
     */
    private static int WORK_NUM = 5;

    /**
     * 队列默认任务个数
     */
    private static int TASK_COUNT = 100;

    /**
     * 工作线程
     */
    private WorkThread[] workThreads;

    /**
     * 任务队列
     */
    private BlockingQueue<Runnable> taskQueue;

    /**
     *用户希望的线程数
     */
    private  int worker_num;


    public MyThreadPool(){
        this(WORK_NUM,TASK_COUNT);
    }

    public MyThreadPool(int workNum, int taskCount) {
        if(workNum <= 0) {workNum = WORK_NUM;}
        if(taskCount <= 0){taskCount = TASK_COUNT;}
        this.worker_num = workNum;
        this.taskQueue = new ArrayBlockingQueue<>(taskCount);

        workThreads = new WorkThread[worker_num];

        for (WorkThread workThread : workThreads) {
            workThread = new WorkThread();
            workThread.start();
        }
    }

    /**
     * 执行任务，只是把用户任务加入阻塞队列
     */
    public void execute(Runnable task){
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁线程池
     */
    public void destory(){
        for (int i = 0; i < worker_num; i++) {
            workThreads[i].stopWorker();
            // Help GC
            workThreads[i] = null;
        }
        taskQueue.clear();
    }

    private class WorkThread extends Thread{
        @Override
        public void run() {
            Runnable r = null;
            try {
                while(!isInterrupted()){
                    r = taskQueue.take();
                    if (r != null){
                        System.out.println(getId() + " ready exec :" + r);
                        r.run();
                    }
                    r = null;//help GC
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void stopWorker(){
            interrupt();
        }
    }
}
