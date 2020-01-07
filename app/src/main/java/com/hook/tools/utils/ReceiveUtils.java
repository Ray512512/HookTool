package com.hook.tools.utils;

import android.content.IntentFilter;

import com.hook.tools.HKApplication;
import com.hook.tools.HookMain;
import com.hook.tools.service.ReceiverMain;

import java.util.Objects;

/**
 * @ Created by Dpc
 * @ <p>TiTle:  ReceiveUtils</p>
 * @ <p>Description:</p>
 * @ date:  2018/10/14 12:02
 * @ QQ:    315096953
 */
public class ReceiveUtils {

    public static final String RECIVER_HOOK="com.dosomething.receive";
    public static void startReceive() {
        if (!ReceiverMain.mIsInit) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(RECIVER_HOOK);
            if(HKApplication.app==null){
                Objects.requireNonNull(HookMain.getContext()).registerReceiver(new ReceiverMain(), filter);
            }else {
            HKApplication.app.registerReceiver(new ReceiverMain(), filter);
        }
        }
    }

}
