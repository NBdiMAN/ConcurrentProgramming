package day1;

public class DaemonThread {
    private static class UserThread extends Thread{

        public UserThread(String name){
            this.setName(name);
        }

        @Override
        public void run() {
            String name = getName();
            while (!isInterrupted()){
                System.out.println(name + "is running!");
            }
            System.out.println(name + " interrupt flag is " + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new UserThread("daemonThread");
        thread.setDaemon(true);//设置成守护线程，随主线程停止
        thread.start();
        Thread.sleep(50);
        thread.interrupt();
    }
}
