package day1;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class NewThread {
    /**
     * 1.线程有三种开启方式 继承Thread类，实现Callable接口，实现Runnable接口
     */
    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                System.out.println("1.继承Thread类的方式");
            }
        }.start();

        new Thread(new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("2.实现Callable接口的方式");
                return "Callable";
            }
        })).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("3.实现Runnable接口的方式");
            }
        }).start();
    }
}
