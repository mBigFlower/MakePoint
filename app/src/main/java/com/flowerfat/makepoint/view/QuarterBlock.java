package com.flowerfat.makepoint.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.entity.db.Point;
import com.flowerfat.makepoint.utils.Utils;

/**
 * Created by 明明大美女 on 2015/9/21.
 * <p/>
 * 主页的控件，一共四块
 */
public class QuarterBlock extends RelativeLayout {

    private final int PADDING = Utils.dp2px(12);
    public static final int WIDTH = 340;
    public static final int HEIGHT = 600;

    PathView mPathView;
    TextView mTextView, mDoneMaskTv;

    private int textLocation;

    private Point mPoint;

    public QuarterBlock(Context context) {
        super(context);
        init();
    }

    public QuarterBlock(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.quarterBlock, 0, 0);

        try {
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
            mDoneMaskTv.setVisibility(GONE);
            addView(mDoneMaskTv);
        }
    }

    private void contentTvInit() {
        if (mTextView == null) {
            mTextView = new TextView(getContext());
            mTextView.setPadding(0, 0, 0, PADDING);
            mTextView.setTextColor(Color.WHITE);
            mTextView.setTextSize(20);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            this.addView(mTextView, lp);
        }
    }

    private void ivInit() {
        mPathView = new PathView(getContext());
        mPathView.setId(R.id.Qb_ImageView);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        this.addView(mPathView, lp);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void toggle() {
        if (mPoint.getDoneTime() == null) {
            setDoneMask("Done\n"+"0809");
        } else {
            setDoneMask(null);
        }
    }

    public void setDoneMask(String doneTime) {
        if("".equals(mPoint.getTitle()) && mPoint.getImgPath() == null)
            return ;
        mPoint.setDoneTime(doneTime);
        mPoint.update();
        if (doneTime == null) {
            mDoneMaskTv.setVisibility(GONE);
        } else {
            mDoneMaskTv.setVisibility(VISIBLE);
            mDoneMaskTv.setText(doneTime);
        }
    }

    public void onResume(Point point) {
        mPoint = point;
        setText(mPoint.getTitle());
        mPathView.setmPath(mPoint.getImgPath());
        setDoneMask(mPoint.getDoneTime());
        textLocationChange(mPoint.getImgPath() != null);
    }

    //////////////////////////////////
    // 工具
    //////////////////////////////////

    /**
     * 根据有无图片，来决定文字的位置
     *
     * @param hasImg
     */
    private void textLocationChange(boolean hasImg) {
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (hasImg) {
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        } else {
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        }
        mTextView.setLayoutParams(lp);
    }

}
