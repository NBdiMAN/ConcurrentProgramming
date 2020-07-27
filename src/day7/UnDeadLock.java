package day7;

/**
 * @author wangyang
 * @date 2020/7/28 0:04
 * @description:
 */
public class UnDeadLock {
    private static Object res3 = new Object();
    private static Object res2 = new Object();
    private static Object res1 = new Object();


    public static void main(String[] args) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                    getRes(res1,res2);

            }
        }).start();

        getRes(res2,res1);
    }
    /**
     * 两个资源根据原始的hashCode按顺序执行，即使hashCode相同，就像足球加时赛，用第三个锁来保证顺序执行
     */
    private static void getRes(Object res1, Object res2){
        int code1 = System.identityHashCode(res1);
        int code2 = System.identityHashCode(res2);
        if(code1 < code2){
            synchronized (res1){
                System.out.println(Thread.currentThread().getName() + " get " + res1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (res2){
                    System.out.println(Thread.currentThread().getName() + " get " + res2);
                }
            }
        }else if(code1 > code2){
            synchronized (res2){
                System.out.println(Thread.currentThread().getName() + " get " + res2);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (res1){
                    System.out.println(Thread.currentThread().getName() + " get " + res1);
                }
            }
        }else{
            synchronized (res3){//解决hash冲突，走这里的概率非常低
                synchronized (res1){
                    System.out.println(Thread.currentThread().getName() + " get " + res1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (res2){
                        System.out.println(Thread.currentThread().getName() + " get " + res2);
                    }
                }
            }
        }
    }
}
