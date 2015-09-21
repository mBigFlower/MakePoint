package com.flowerfat.makepoint.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.flowerfat.makepoint.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 明明大美女 on 2015/9/21.
 */
public class DrawBoardView extends RelativeLayout {

    @Bind(R.id.BoardView_close)
    ImageView ImgClose ;
    @Bind(R.id.BoardView_save)
    ImageView ImgSave ;
    @Bind(R.id.BoardView_setting)
    ImageView ImgSetting ;
    @Bind(R.id.BoardView_drawBoard)
    DrawBoard drawBoard ;


    public DrawBoardView(Context context) {
        super(context);
    }

    public DrawBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_drawboard, this,
                true);
        ButterKnife.bind(this);
    }

    public DrawBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @OnClick(R.id.BoardView_close)
    void clear(){
        drawBoard.clear();
    }

    @OnClick(R.id.BoardView_save)
    void saveOnclick(){
        drawBoard.toLastPath();
    }


    public void close(){

    }

    public void save(){

    }

    public void openSetting(){

    }

}
