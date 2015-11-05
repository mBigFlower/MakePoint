package com.flowerfat.makepoint.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flowerfat.makepoint.PointColor;
import com.flowerfat.makepoint.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 明明大美女 on 2015/9/21.
 * <p>
 * 主页的控件，一共四块
 */
public class QuarterBlock extends LinearLayout {



    @Bind(R.id.block_img)
    ImageView mImageView;
    @Bind(R.id.block_text)
    TextView mTextView;
    @Bind(R.id.block_root)
    LinearLayout mLinearLayout ;

    private Context mContext;

    private String textString ;
    private int textLocation ;

    public QuarterBlock(Context context) {
        super(context);
    }

    public QuarterBlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_quarter_block, this, true);
        ButterKnife.bind(this);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.quarterBlock,
                0, 0);

        try {
            textString = a.getString(R.styleable.quarterBlock_blockText);
            textLocation = a.getInt(R.styleable.quarterBlock_textLocation, 1);
        } finally {
            a.recycle();
        }

        init();
    }

    public QuarterBlock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mTextView.setText(textString);
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
