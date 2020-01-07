package com.hook.tools.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.hook.tools.utils.LogUtils;
import com.hook.tools.utils.ReceiveUtils;

import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;


/**
 * @ <p>TiTle:  ServiceMain</p>
 * @ <p>Description: 这个类就一直轮循去请求是否需要二维码</p>
 * @ date:  2018/09/22
 */
public class ServiceMain extends Service {

    //是否启动了检测二维码需求的功能
    public static Boolean mIsRunning = false;

    //上次询问服务器是否需要二维码的时间
    public static long mLastQueryTime = 0;

    //防止被休眠，你们根据情况可以开关，我是一直打开的，有点费电是必然的，哈哈
    private PowerManager.WakeLock mWakeLock;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mLastQueryTime = System.currentTimeMillis();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP, getPackageName());
        mWakeLock.acquire();

        mIsRunning = true;
        LogUtils.show("服务启动");
        ReceiveUtils.startReceive();
        if (!handler.hasMessages(0)) {
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.sendEmptyMessage(0);
        return START_STICKY;
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (handler.hasMessages(0)) {
                return;
            }

            mLastQueryTime = System.currentTimeMillis();
            //0-7点的时候就慢速轮循
            handler.sendEmptyMessageDelayed(0,
                    5000);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mWakeLock != null)
                mWakeLock.release();
            mWakeLock = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogUtils.show("服务被杀死");
        Intent intent = new Intent(this.getApplicationContext(), ServiceMain.class);
        this.startService(intent);
    }
}
