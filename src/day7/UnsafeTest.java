package day7;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyang
 * @date 2020/7/27 22:38
 * @description: 以下操作没啥意义，线程安全是指 操作的原子性和变量的可见性
 */
public class UnsafeTest {

    private static List<Integer> list = new ArrayList<>(20);

    public static void setValue(int value){
        list.add(value);
    }

    public static  int getValue(int index){
        return list.get(index);
    }

    public static void main(String[] args) throws InterruptedException {
        /*for (int i = 0; i < 20; i++) {
            setValue(0);
        }*/
        for (int i = 0; i < 20; i++) {
            final int j = i;
            new Thread(new Runnable(){
                @Override
                public void run() {
                    System.out.println(j);
                    setValue(j);
                }
            }).start();
        }

        Thread.sleep(5000);
        System.out.println("================================");
        for (int i = 0; i < 20; i++) {
            System.out.println(list.get(i));
        }
        System.out.println("================================");
        for (int i = 0; i < 20; i++) {
            final int j = i;
            new Thread(new Runnable(){
                @Override
                public void run() {
                    System.out.println(getValue(j));

                }
            }).start();
        }
    }
}
