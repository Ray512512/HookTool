package com.hook.tools.utils;

import android.util.Log;

import de.robv.android.xposed.XposedBridge;

/**
 * @ <p>TiTle:  LogUtils</p>
 * @ <p>Description: 懒得去判断这个日志是哪个进程发送的了，统一下日志接口吧。</p>
 * @ date:  2018/9/22
 */
public class LogUtils {

    public static void show(String tips) {
        try {
            XposedBridge.log(tips);
        } catch (NoClassDefFoundError ignore) {

        }
        Log.e("LogUtils", tips);
    }

    public static void showAll(String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001;
        //大于4000时
        while (msg.length() > max_str_length) {
            show(msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        show(msg);
    }
}
