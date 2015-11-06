package com.flowerfat.makepoint.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flowerfat.makepoint.PointColor;
import com.flowerfat.makepoint.R;

import java.io.File;

/**
 * Created by 明明大美女 on 2015/9/21.
 * <p>
 * 主页的控件，一共四块
 */
public class QuarterBlock2 extends LinearLayout {



    ImageView mImageView;
    TextView mTextView;

    private Context mContext;

    private String textString ;
    private int textLocation ;

    public QuarterBlock2(Context context) {
        super(context);
        init();
    }

    public QuarterBlock2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs,R.styleable.quarterBlock,0, 0);

        try {
            textString = ta.getString(R.styleable.quarterBlock_blockText);
            textLocation = ta.getInt(R.styleable.quarterBlock_textLocation, 1);
        } finally {
            ta.recycle();
        }

        init();
    }

    private void init() {
        this.setOrientation(VERTICAL);
        widgetInit();
        this.addView(mTextView);
        this.addView(mImageView);
    }

    private void widgetInit(){
        tvInit();
        ivInit();
    }
    private void tvInit(){
        mTextView = new TextView(getContext());
        mTextView.setTextColor(Color.WHITE);
        mTextView.setText(textString);
        mTextView.setTextSize(20);
    }
    private void ivInit(){
        mImageView = new ImageView(getContext());
        setImage(textLocation);
    }



    public void setText(String text){
        mTextView.setText(text);
    }

    public void setImage(int location){
        String sdPath = Environment.getExternalStorageDirectory().getPath() + "/boards/";
        File imgFile ;
        switch (location){
            case 1:
                imgFile = new File(sdPath, PointColor.COLOR_1 + ".png");
                break ;
            case 2:
                imgFile = new File(sdPath, PointColor.COLOR_2 + ".png");
                break ;
            case 3:
                imgFile = new File(sdPath, PointColor.COLOR_3 + ".png");
                break ;
            default:
                imgFile = new File(sdPath, PointColor.COLOR_4 + ".png");
                break ;
        }

        Uri imageUri = Uri.fromFile(imgFile);
        System.out.println("onActivityResult"+ imageUri);
        if (imgFile.exists()) {
            Glide.with(mContext).load(imgFile).into(mImageView);
        }
    }

}
