package com.flowerfat.makepoint.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flowerfat.makepoint.R;
import com.hanks.htextview.HTextView;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        HTextView hTextView = (HTextView)findViewById(R.id.htextview);
        hTextView.animateText("woqule !");
    }

}
