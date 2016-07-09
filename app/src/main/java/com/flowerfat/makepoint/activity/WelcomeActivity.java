package com.flowerfat.makepoint.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.utils.SpInstance;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        startActivity(new Intent(this, TestActivity.class));
        isAnimShow();
    }

    private void isAnimShow(){
        if(SpInstance.get().isWelcomeAnim()) {
            delayStart();
        } else {
            MainActivity.launch(this);
            finish();
        }
    }

    private void delayStart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.launch(WelcomeActivity.this);
                finish();
            }
        }, 2000);
    }

}
