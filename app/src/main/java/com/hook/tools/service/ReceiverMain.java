package com.hook.tools.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.hook.tools.MainActivity;
import com.hook.tools.utils.LogUtils;
import com.hook.tools.utils.ReceiveUtils;


/**
 * @ <p>TiTle:  ReceiverMain</p>
 * @ <p>Description: 当HOOK之后的处理结果，只能用此广播来接受，不然很多数据不方便共享的</p>
 * @ date:  2018/09/22
 */
public class ReceiverMain extends BroadcastReceiver {
    private static final String TAG = "ReceiverMain";
    public static boolean mIsInit = false;
    public static long lastMsgTime=0L;//防止重启接收广播，一定要用static

    public ReceiverMain() {
        super();
        mIsInit = true;
        LogUtils.show("Receiver创建成功！");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (System.currentTimeMillis()-lastMsgTime<500) {
                return;
            }
            lastMsgTime =System.currentTimeMillis();
            switch (intent.getAction()){
                case ReceiveUtils.RECIVER_HOOK:
                    String ip=intent.getStringExtra("ip");
                    String port=intent.getStringExtra("port");
                    Log.v(TAG,"ip:"+ip+",port:"+port);
                    break;
                    default:
                break;
            }
        } catch (Exception e) {
            LogUtils.show(e.getMessage());
        }
    }

}
