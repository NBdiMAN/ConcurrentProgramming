package day2.forkjoin;

import java.util.Random;

public class MakeArray {
    public static final int LENGTH = 4000;

    public static int[] getArray(){
        Random random = new Random();
        int[] array = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            array[i] = random.nextInt(LENGTH * 3);
        }
        return array;
    }
}
