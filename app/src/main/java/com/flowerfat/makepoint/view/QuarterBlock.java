package com.flowerfat.makepoint.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flowerfat.makepoint.PointColor;
import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.utils.SpInstance;
import com.flowerfat.makepoint.utils.Utils;

import java.io.File;

/**
 * Created by 明明大美女 on 2015/9/21.
 * <p/>
 * 主页的控件，一共四块
 */
public class QuarterBlock extends LinearLayout {


    private final int PADDING = Utils.dp2px(12);

    ImageView mImageView;
    TextView mTextView;

    private Context mContext;

    private String textString;
    private int textLocation;

    public QuarterBlock(Context context) {
        super(context);
        init();
    }

    public QuarterBlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.quarterBlock, 0, 0);

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
        this.setGravity(Gravity.CENTER);
        this.setPadding(PADDING, PADDING, PADDING, PADDING);
        widgetInit();
    }

    private void widgetInit() {
        tvInit();
        ivInit();
        setImageAndLoc(textLocation);
    }

    private void tvInit() {
        mTextView = new TextView(getContext());
        mTextView.setTextColor(Color.WHITE);
//        mTextView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        mTextView.setText(textString);
        mTextView.setTextSize(20);
        mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    private void ivInit() {
        mImageView = new ImageView(getContext());
    }

    /**
     * 根据位置，获得不同的图片 ； 并确定文字与图片的布局
     *
     * @param location
     */
    private void setImageAndLoc(int location) {
        switch (location) {
            case 1:
                this.addView(mImageView);
                this.addView(mTextView);
                break;
            case 2:
                this.addView(mImageView);
                this.addView(mTextView);
                break;
            case 3:
                this.addView(mTextView);
                this.addView(mImageView);
                break;
            default:
                this.addView(mTextView);
                this.addView(mImageView);
                break;
        }

    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public boolean setImg(int color) {
        String sdPath = Environment.getExternalStorageDirectory().getPath() + "/boards/";
        String imgName = SpInstance.get().gString(""+color);
        if(imgName == null)
            return false;
        File imgFile = new File(sdPath, imgName);
        if (imgFile.exists()) {
            Glide.with(mContext).load(imgFile).into(mImageView);
            imgFile = null;
            return true;
        } else {
            imgFile = null;
            return false;
        }
    }

    public void onResume(int color) {
        setText(SpInstance.get().gString("pColor" + color));
        if (setImg(color)) {
            if(color == PointColor.COLOR_1 || color == PointColor.COLOR_3) {
                mTextView.setGravity(Gravity.RIGHT);
            } else {
                mTextView.setGravity(Gravity.LEFT);
            }
        }
    }

}
