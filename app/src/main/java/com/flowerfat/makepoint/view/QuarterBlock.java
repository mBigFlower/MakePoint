package com.flowerfat.makepoint.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.utils.SpInstance;
import com.flowerfat.makepoint.utils.Utils;

import java.io.File;

/**
 * Created by 明明大美女 on 2015/9/21.
 * <p/>
 * 主页的控件，一共四块
 */
public class QuarterBlock extends RelativeLayout {


    private final int PADDING = Utils.dp2px(12);

    ImageView mImageView;
    TextView mTextView, mDoneMaskTv;

    private String textString;
    private int textLocation;
    private int mColor;
    private boolean isDone;
    private boolean hasImg;

    public QuarterBlock(Context context) {
        super(context);
        init();
    }

    public QuarterBlock(Context context, AttributeSet attrs) {
        super(context, attrs);

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
        widgetInit();
    }

    private void widgetInit() {
        ivInit();
        contentTvInit();
        doneTvInit();
    }

    private void doneTvInit() {
        if (mDoneMaskTv == null) {
            mDoneMaskTv = new TextView(getContext());
            mDoneMaskTv.setTextColor(Color.WHITE);
            mDoneMaskTv.setPadding(PADDING, PADDING, 0, 0);
            mDoneMaskTv.setBackgroundColor(Color.argb(119, 0, 0, 0));
            mDoneMaskTv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mDoneMaskTv.setText("Done");
            mDoneMaskTv.setTextSize(20);
            addView(mDoneMaskTv);
        }
    }

    private void contentTvInit() {
        if (mTextView == null) {
            mTextView = new TextView(getContext());
            mTextView.setPadding(0, 0, 0, PADDING);
            mTextView.setTextColor(Color.WHITE);
            mTextView.setText(textString);
            mTextView.setTextSize(20);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            this.addView(mTextView, lp);
        }
    }

    private void ivInit() {
        mImageView = new ImageView(getContext());
        mImageView.setId(R.id.Qb_ImageView);
        mImageView.setPadding(PADDING, PADDING, PADDING, PADDING);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(mImageView);
    }

    public boolean setImg(int color) {
        String sdPath = Environment.getExternalStorageDirectory().getPath() + "/boards/";
        String imgName = SpInstance.get().gString("" + color);
        if (imgName == null)
            return false;
        File imgFile = new File(sdPath, imgName);
        if (imgFile.exists()) {
            Glide.with(getContext()).load(imgFile).into(mImageView);
            imgFile = null;
            return true;
        } else {
            imgFile = null;
            return false;
        }
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void toggle() {
        if (isDone) {
            setDoneMask(false);
        } else {
            setDoneMask(true);
        }
    }

    public void setDoneMask(boolean isDoneShow) {
        isDone = isDoneShow;
        SpInstance.get().pBoolean("isTaskDone" + mColor, isDone);
        if (isDoneShow) {
            mDoneMaskTv.setVisibility(VISIBLE);
        } else {
            mDoneMaskTv.setVisibility(GONE);
        }
    }


    public void onResume(int color) {
        mColor = color;
        setText(SpInstance.get().gString("pColor" + color));
        setDoneMask(SpInstance.get().gBoolean("isTaskDone" + color, false));
        imgAndTextChange(color);
    }

    /**
     * 图片有无变化，而影响文字的位置
     */
    private void imgAndTextChange(int color) {
        if (setImg(color)) {
            if (!hasImg) {
                textLocationChange();
            }
            hasImg = true;
        } else {
            if (hasImg) {
                textLocationChange();
            }
            hasImg = false;
        }
    }

    //////////////////////////////////
    // 工具
    //////////////////////////////////
    private void textLocationChange() {
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (hasImg) {
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        } else {
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        }
        mTextView.setLayoutParams(lp);
    }

}
