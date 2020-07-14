package day5;

/**
 * @author wangyang
 * @date 2020/7/14 23:05
 * @description:
 */
public class IntToBinary {
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(4));

        //位与：按位相与
        System.out.println(Integer.toBinaryString(4 & 6));

        //位或，按位相或
        System.out.println(Integer.toBinaryString(4 | 6));

        //位异或
        System.out.println(Integer.toBinaryString(4 ^ 6));


        //位非
        System.out.println(Integer.toBinaryString(~4).length());
    }
}
