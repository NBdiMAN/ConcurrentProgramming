package day8;

import java.nio.file.attribute.UserDefinedFileAttributeView;

/**
 * @author wangyang
 * @date 2020/7/29 23:15
 * @description:
 */
public class  SingletonEnum {



    private SingletonEnum() {}

    static enum Single{
        INSTANCE;
        private SingletonEnum singletonEnum;

        private Single(){
            singletonEnum = new SingletonEnum();
        }

        public SingletonEnum getSingletonEnum() {
            return singletonEnum;
        }
    }

    public SingletonEnum getInstance(){
        return Single.INSTANCE.getSingletonEnum();
    }

}
