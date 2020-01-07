package com.hook.tools.hooks;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.hook.tools.utils.LogUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Ray on 2020/1/2.
 */
public class HookTimAppId {
    public static Context mContext;
    public static ClassLoader mClassLoader;

    public void hook(ClassLoader appClassLoader, final Context context) {
        try {
            mContext=context;
            mClassLoader=appClassLoader;
            hookAesKey();
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }


    private void hookAesKey(){
        LogUtils.show("hookAesKey");
        //hook TimAppId
        XposedHelpers.findAndHookMethod("com.tencent.TIMManager", mClassLoader, "init", Context.class, int.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        timManager = param.thisObject;
                        mHandler.sendEmptyMessageDelayed(0, 10*1000);
                        LogUtils.show("AppId2:" + param.args[1].toString() + "&" + param.args[2]);
                    }
                });
    }


    private Object timManager;

    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            tryGetAppId();
            return false;
        }
    });
    private void tryGetAppId() {
        if (timManager != null) {
            Class<?> applicationClazz = null;
            try {
                applicationClazz = mClassLoader.loadClass("com.tencent.TIMManager");
                Method getSdkAppIdMethod = applicationClazz.getMethod("getSdkAppId");
                getSdkAppIdMethod.setAccessible(true);
                Object appId = getSdkAppIdMethod.invoke(timManager);
                LogUtils.show("AppId3:" + appId);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
