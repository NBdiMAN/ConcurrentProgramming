package day9.demo;

/**
 * @author wangyang
 * @date 2020/7/30 23:57
 * @description: 要求框架使用者实现的接口，因为任务的性质在调用时才知道
 *                  所以使用泛型
 */
public interface ITaskProcesser<T,R> {
    TaskResult<R> taskExecute(T t);
}
