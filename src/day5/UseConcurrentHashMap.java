package day5;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangyang
 * @date 2020/7/13 22:27
 * @description:
 */
public class UseConcurrentHashMap {
    public static void main(String[] args) {
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>(16);
        map.put("1", "2");
        map.putIfAbsent("1", "0");
        System.out.println(map.get("1"));
    }
}
