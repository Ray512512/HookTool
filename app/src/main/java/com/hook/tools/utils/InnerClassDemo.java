package com.hook.tools.utils;

import java.lang.reflect.Field;

/**
 * 获取内部类的外部类对象
 */
public class InnerClassDemo {

    private String mName;

    public InnerClassDemo(String name) {
        mName = name;
    }

    public static void main(String[] args) {
        InnerClassDemo main = new InnerClassDemo("Main");
        main.test();
    }

    public interface Callback{
        public void callback(String s);
    }

    private void test() {
        Callback callback = new Callback() {
            String this$0 = "callback_this0";
            // String this$0$ = "callback_this0$";
            String this$0$$ = "callback_this0$$";

            @Override
            public void callback(String s) {
                System.out.println(getName());
            }
        };
        try {
            System.out.println("获取外部类对象：" + getExternalClass(callback));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(callback.getClass().getName() + "不是一个匿名内部类，或者该匿名内部类没有持有外部类对象。", e);
        }
    }

    private String getName() {
        return mName;
    }

    private static final int SYNTHETIC = 0x00001000;
    private static final int FINAL = 0x00000010;
    private static final int SYNTHETIC_AND_FINAL = SYNTHETIC | FINAL;

    private static boolean checkModifier(int mod) {
        return (mod & SYNTHETIC_AND_FINAL) == SYNTHETIC_AND_FINAL;
    }

    public static Object getExternalClass(Object target) throws NoSuchFieldException {
        return getField(target, null, null);
    }

    private static Object getField(Object target, String name, Class classCache) throws NoSuchFieldException {
        if (classCache == null) {
            classCache = target.getClass();
        }
        if (name == null || name.isEmpty()) {
            name = "this$0";
        }
        Field field = classCache.getDeclaredField(name);
        field.setAccessible(true);
        if (checkModifier(field.getModifiers())) {
            try {
                return field.get(target);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return getField(target, name + "$", classCache);
    }
}