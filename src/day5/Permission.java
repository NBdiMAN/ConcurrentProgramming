package day5;

/**
 * @author wangyang
 * @date 2020/7/15 20:14
 * @description: 使用位运算设置用户权限；位运算常用于用户权限和设置多属性
 */
public class Permission {
    public static final int ALLOW_SELECT = 1 << 0;
    public static final int ALLOW_INSERT = 1 << 1;
    public static final int ALLOW_UPDATE = 1 << 2;
    public static final int ALLOW_DELETE = 1 << 3;

    /**
     * 用户当前的权限
     */
    private int flag;


    /**
     * 设置用户权限
     * @param per
     */
    public void setPer(int per){
        this.flag = per;
    }

    /**
     * 增加用户权限
     * @param per
     */
    public void enable(int per){
        this.flag = this.flag | per;
    }

    /**
     * 删除用户权限
     * @param per
     */
    public void disable(int per){
        this.flag = this.flag & ~per;
    }

    /**
     * 查看用户是否有某种权限
     * @param per
     * @return
     */
    public boolean isAllow(int per){
        return this.flag == (this.flag & per);
    }
    /**
     * 查看用户是否没有某种权限
     * @param per
     * @return
     */
    public boolean isNotAllow(int per){
        return 0 == (this.flag & per);
    }

}
