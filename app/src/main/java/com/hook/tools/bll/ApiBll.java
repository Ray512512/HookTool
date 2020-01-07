package com.hook.tools.bll;


import com.hook.tools.utils.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @ Created by Ray
 * @ <p>TiTle:  ApiBll</p>
 * @ <p>Description: 和服务端交互的业务类</p>
 * @ date:  2018/9/21
 */
public class ApiBll {
    private static final String TAG = "ApiBll";
    /**
     * 请求成功
     */
    public static final String OK = "OK";

    /**
     * 请求回调
     *
     * @param <T>
     */
    public interface NetCallback<T> {
        public void success(T t);
    }


    public void test(String url) {
        StringBuilder sburl;
        try {
            sburl = new StringBuilder(url)
                    .append("?act=init&ctl=app&i_type=1")
            ;
            LogUtils.show(TAG + ":"+sburl.toString());
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(sburl.toString())
//                    .header("X-JSL-API-AUTH",token)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtils.show(TAG + e);
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (response.body() != null) {
                        String r = response.body().string();
                        LogUtils.show(TAG + "结果：" + r);
                    }
                }
            });
        } catch (Exception e) {
            LogUtils.show(TAG + "error" + e.getMessage());
        }
    }


}
