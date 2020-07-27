package day6;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyang
 * @date 2020/7/23 23:43
 * @description:
 */
public class UseScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);

        scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("fly");
            }
        }, 5,3, TimeUnit.SECONDS);

        //scheduledThreadPoolExecutor.shutdown();
    }
}
