package day8;

/**
 * @author wangyang
 * @date 2020/7/29 23:06
 * @description: 饿汉式单例
 */
public class SingletonHungry {
    private static SingletonHungry singletonHungry = new SingletonHungry();

    private SingletonHungry(){}

    public static SingletonHungry getInstance(){
        return singletonHungry;
    }
}
