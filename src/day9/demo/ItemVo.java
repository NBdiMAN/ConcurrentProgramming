package day9.demo;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyang
 * @date 2020/8/8 2:09
 * @description: 存放到延迟队列的元素，实现延迟功能
 */
public class ItemVo<T> implements Delayed {

    /**
     * 过期时刻，到了这一时刻，就需要从DelayQueue中弹出
     */
    private long expireTime;

    /**
     * 存放的数据
     */
    private T data;

    /**
     *
     * @param expireTime ms
     * @param date
     */
    public ItemVo(long expireTime, T date) {
        //将传入的ms转化成ns
        this.expireTime = TimeUnit.NANOSECONDS.convert(expireTime, TimeUnit.MILLISECONDS) + System.nanoTime();
        this.data = date;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public T getData() {
        return data;
    }

    /**
     * 根据任意的时间类型返回元素的剩余过期时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expireTime - System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    /**
     * 按剩余时间排序,剩余时间少的排在前面
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return Long.compare(getDelay(TimeUnit.NANOSECONDS), o.getDelay(TimeUnit.NANOSECONDS));
    }
}
