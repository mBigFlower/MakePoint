package com.flowerfat.makepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.flowerfat.makepoint.utils.FileUtil;
import com.flowerfat.makepoint.utils.SpInstance;
import com.flowerfat.makepoint.utils.Utils;

import java.io.File;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstInit();
        dateCheck();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    private void firstInit(){
        if(SpInstance.get().gBoolean("firstLoad", true)){
            SpInstance.get().pBoolean("firstLoad", false);
            //1. give firstLoadDay a default values
            Utils.ifStepDay(this);
            //2. ^ ^
        }
    }

    /**
     * 每日检测，跨天与否执行的内容不同
     */
    private void dateCheck() {
        if (Utils.ifStepDay(this)) {
            // reset the point's text
            SpInstance.get().initOneDayPoint();
            // delete all the board pic
            try {
                FileUtil.del(new File(Environment.getExternalStorageDirectory(), "/boards/"));
            } catch (Exception e) {
                Toast.makeText(this, "错误信息：" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
