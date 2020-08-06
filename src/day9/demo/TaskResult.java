package day9.demo;

/**
 * @author wangyang
 * @date 2020/7/31 0:24
 * @description: 任务处理返回的结果
 */
public class TaskResult<R> {
    private final TaskResultType resultType;

    /**
     * 返回结果
     */
    private final R returnValue;

    /**
     * 失败原因
     */
    private final String reason;

    public TaskResult(TaskResultType resultType,R returnValue, String reason) {
        this.resultType = resultType;
        this.returnValue = returnValue;
        this.reason = reason;
    }

    public TaskResultType getResultType() {
        return resultType;
    }

    public R getReturnValue() {
        return returnValue;
    }

    public String getReason() {
        return reason;
    }
}
