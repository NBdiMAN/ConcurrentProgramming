package day2.juc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class TestAtomic {
    static AtomicInteger num = new AtomicInteger(10);
    static AtomicReference<String> atomicReference = new AtomicReference<>();
    public static void main(String[] args) {
        System.out.println(num.get());
        System.out.println(num.getAndIncrement());
        System.out.println(num.incrementAndGet());

        /**
         * int a;
         * while(!compareAndSet(v,a,a+1)){
         *      a = get();
         * }
         * return a + 1;
         */
        atomicReference.set("Lee");
        atomicReference.compareAndSet("Lee", "M");
        System.out.println(atomicReference.get());
        //演示解决ABA问题
        AtomicStampedReference<String> asr = new AtomicStampedReference<>("mx", 0);
        asr.compareAndSet("mx", "wy", 0, 2);
        System.out.println(asr.getReference());
        System.out.println(asr.getStamp());
    }
}
