package com.flowerfat.makepoint.view;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 明明大美女 on 2015/9/21.
 */
public class DrawBoardView extends RelativeLayout {

    @Bind(R.id.BoardView_close)
    ImageView ImgClose;
    @Bind(R.id.BoardView_save)
    ImageView ImgSave;
    @Bind(R.id.BoardView_clear)
    ImageView ImgClear;
    @Bind(R.id.BoardView_drawBoard)
    DrawBoard drawBoard;
    @Bind(R.id.BoardView_boardLayout)
    RelativeLayout boardLayout;
    @Bind(R.id.BoardView_spread)
    ImageView ImgSpread;


    private Context mContext;
    private int boardHeight;
    private List<Path> savePath = new ArrayList<>();


    public DrawBoardView(Context context) {
        super(context);
    }

    public DrawBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_drawboard_canvas, this, true);
        ButterKnife.bind(this);

        init();
    }

    public DrawBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        boardHeight = ScreenUtil.getScreenSize(mContext)[1] * 3 / 5;
        boardLayout.setTranslationY(-boardHeight);
    }


    @OnClick(R.id.BoardView_spread)
    public void show() {
        initBoardState();
        ImgSpread.animate().translationY(boardHeight).alpha(0).setDuration(400);
        boardLayout.animate().translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f)).setDuration(500);
        drawBoard.setDrawEnable(true);
    }

    @OnClick(R.id.BoardView_close)
    void close() {
        ImgSpread.setTranslationY(0);
        ImgSpread.animate().alpha(1).rotation(360).setDuration(200).setStartDelay(200);

        boardLayout.animate().alpha(0).setDuration(300);
        drawBoard.setDrawEnable(false);
    }

    @OnClick(R.id.BoardView_save)
    public void save() {
        String saveResult = drawBoard.save();
        Toast.makeText(mContext, saveResult, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.BoardView_clear)
    void clear() {
        drawBoard.clear();
    }

    /**
     * 在画板即将展开前，将各个部分回复出厂设置
     */
    private void initBoardState() {
        boardLayout.setAlpha(1);
        boardLayout.setTranslationY(-boardHeight);
        ImgClose.setRotation(0);
        ImgSpread.setRotation(0);
    }

    public void setBoardColor(int color) {
        drawBoard.setBoardColor(color);
    }

    public void setPathStr(String pathStr){
        drawBoard.setPath(pathStr);
    }
    /**
     * 释放资源
     */
    public void release() {
        drawBoard.release();
    }

    public boolean isDrawed() {
        return drawBoard.isDrawed();
    }
}
