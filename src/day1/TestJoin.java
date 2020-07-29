package day1;

public class TestJoin {

    private static class MyThread implements Runnable{
        private Thread thread;
        public MyThread(Thread thread){
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                System.out.println(thread.getName() + " join before " + Thread.currentThread().getName());
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is terminated!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread1 = new Thread(new MyThread(thread), String.valueOf(i));
            thread1.start();
            thread = thread1;
        }
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + " is terminated!");

    }

}
