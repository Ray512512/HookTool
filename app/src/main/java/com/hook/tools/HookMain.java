package com.hook.tools;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;

import com.hook.tools.hooks.HookTimAppId;
import com.hook.tools.utils.LogUtils;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


/**
 * @ Created by Ray
 * @ <p>TiTle:  HookMain</p>
 * @ <p>Description: Xposed的唯一Hook入口</p>
 */
public class HookMain implements IXposedHookLoadPackage {


    public static ClassLoader mClassLoader;
    public static Application app;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam)
            throws Throwable {
        if (lpparam.appInfo == null || (lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM |
                ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0) {
            return;
        }
        mClassLoader = lpparam.classLoader;
        final String packageName = lpparam.packageName;
        final String processName = lpparam.processName;
        LogUtils.show("hook packageName:" + packageName);

        /**
         * 从主类开始hook防止多dex问题
         */
        try {
            XposedHelpers.findAndHookMethod(ContextWrapper.class, "attachBaseContext", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Context context = (Context) param.args[0];
                    ClassLoader appClassLoader = context.getClassLoader();
                    new HookTimAppId().hook(appClassLoader, context);
                }
            });
        } catch (Throwable e) {
            LogUtils.show(e.getMessage());
        }
    }

    public static Context getContext() {
        try {
            Class<?> ActivityThread =
                    Class.forName("android.app.ActivityThread");

            Method method = ActivityThread.getMethod("currentActivityThread");
            Object currentActivityThread = method.invoke(ActivityThread);//获取currentActivityThread 对象
            Method method2 = currentActivityThread.getClass().getMethod("getApplication");
            Context context = (Context) method2.invoke(currentActivityThread);//获取 Context对象
            XposedBridge.log("Context " + context);
            return context;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}




