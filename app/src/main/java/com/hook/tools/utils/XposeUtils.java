package com.hook.tools.utils;


import android.app.Activity;
import android.content.res.Resources;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Ray on 2019/8/15.
 */
public class XposeUtils {
    private static final String TAG = "XposeUtils";
    public static String getStringField(final Object obj, final String fieldName) throws IllegalAccessException {
        Field sField = XposedHelpers.findField(obj.getClass(), fieldName);
        if (sField == null) {
            return null;
        }
        return String.valueOf(sField.get(obj));
    }

    public static Object getField(final Object obj, final String fieldName) throws IllegalAccessException {
        Field sField = XposedHelpers.findField(obj.getClass(), fieldName);
        if (sField == null) {
            return null;
        }
        return sField.get(obj);
    }


    public static void printObject(Object o){
        Field[] fields = o.getClass().getFields();
        for(int i = 0 ; i < fields.length ; i++) {
            //设置是否允许访问，不是修改原来的访问权限修饰词。
            fields[i].setAccessible(true);
            try {
                LogUtils.show(fields[i].getName()+":"+fields[i].get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
