package com.flowerfat.makepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flowerfat.makepoint.Utils.SpInstance;
import com.flowerfat.makepoint.Utils.Utils;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstInit();

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

}
