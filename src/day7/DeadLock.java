package day7;

/**
 * @author wangyang
 * @date 2020/7/27 23:19
 * @description:
 */
public class DeadLock {
    private static Object res1 = new Object();
    private static Object res2 = new Object();

    public static void getRes1() throws InterruptedException {
        synchronized (res1){
            System.out.println(Thread.currentThread().getName() + " get res1");
            Thread.sleep(1000);

            synchronized (res2){
                System.out.println(Thread.currentThread().getName() + " get res2");
            }
        }
    }
    public static void getRes2() throws InterruptedException {
        synchronized (res2){
            System.out.println(Thread.currentThread().getName() + " get res2");
            Thread.sleep(1000);

            synchronized (res1){
                System.out.println(Thread.currentThread().getName() + " get res1");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    getRes2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        getRes1();
    }
}
