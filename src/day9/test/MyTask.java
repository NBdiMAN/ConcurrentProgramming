package day9.test;

import day9.demo.ITaskProcesser;
import day9.demo.TaskResult;
import day9.demo.TaskResultType;

import java.util.Random;

/**
 * @author wangyang
 * @date 2020/5/6 0:04
 * @description: 一个实际任务类，将数值加上一个随机数，并休眠随机时间
 */
public class MyTask implements ITaskProcesser<Integer,Integer> {

    @Override
    public TaskResult<Integer> taskExecute(Integer data) {
        Random r = new Random();
        int flag = r.nextInt(500);
        try{
            Thread.sleep(flag);
            if(flag <= 300){
                //正常处理的情况
                Integer returnValue = data.intValue() + flag;
                return new TaskResult<Integer>(TaskResultType.SUCCESS,returnValue);
            }else if (flag <= 400){
                return new TaskResult<Integer>(TaskResultType.FAILURE, -1, "Failure");
            }else{
                return new TaskResult<Integer>(TaskResultType.EXCEPTION, -1, "Exception");
            }
        }catch (Exception e){
            return new TaskResult<Integer>(TaskResultType.EXCEPTION, -1, e.getMessage());
        }
    }
}
