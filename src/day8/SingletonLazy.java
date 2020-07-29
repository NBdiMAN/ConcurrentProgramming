package day8;

/**
 * @author wangyang
 * @date 2020/7/29 23:10
 * @description: 懒汉式单例
 */
public class SingletonLazy {
    private SingletonLazy(){}

    private static class InitInstance{
        public static SingletonLazy singletonLazy = new SingletonLazy();
    }

    public static SingletonLazy getInstance(){
        return InitInstance.singletonLazy;
    }
}
