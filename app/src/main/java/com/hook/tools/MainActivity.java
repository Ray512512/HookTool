package com.hook.tools;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hook.tools.bll.ApiBll;
import com.hook.tools.utils.ReceiveUtils;

public class MainActivity extends AppCompatActivity {


    public static MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity=this;
        ReceiveUtils.startReceive();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivity=null;
    }

    @Override
    public void onBackPressed() {
        Intent var1 = new Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME);
        startActivity(var1);
    }
}
