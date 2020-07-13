package day4;

import java.util.IllegalFormatException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author wangyang
 * @date 2020/7/12 22:35
 * @description:
 */
public class TrinityLock implements Lock {
    /**
     * 为3表示允许3个线程同时获得锁
     */
    private final Sync sync = new Sync(3);



    private static final class Sync extends AbstractQueuedSynchronizer{
        Sync(int count){
            if(count < 0){
                throw new IllegalArgumentException("count must large than zero");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for (;;){
                int current = getState();
                int newCount = current - reduceCount;
                if(newCount < 0 || compareAndSetState(current, newCount)){
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int reduceCount) {
            for (;;){
                int current = getState();
                int newCount = current + reduceCount;
                if(compareAndSetState(current, newCount)){
                    return true;
                }
            }
        }

        public Condition newCondition() {
            return new ConditionObject();
        }

    }
    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) < 0 ? true : false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, 1000);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
