package day1;

/**
 * 安全的中断线程
 */
public class EndThread {

    private static class UserThread extends Thread{

        public UserThread(String name){
            this.setName(name);
        }

        @Override
        public void run() {
            String name = getName();
            while (!isInterrupted()){
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    System.out.println(name + "44444444444 interrupt flag is " + isInterrupted());//捕获到中断异常后，中断标志位就会复位为false
                    e.printStackTrace();
                    //如果想跳出循环，必须再次中断
                    interrupt();
                }
                System.out.println(name + "22222222222 interrupt flag is " + isInterrupted());
                System.out.println(name + "is running!");
            }
            System.out.println(name + "111111111 interrupt flag is " + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new UserThread("endThread");
        thread.start();
        Thread.sleep(50);
        thread.interrupt();
    }

}
