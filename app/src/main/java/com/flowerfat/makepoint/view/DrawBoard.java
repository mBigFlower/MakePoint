package com.flowerfat.makepoint.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.flowerfat.makepoint.Utils.ScreenUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 明明大美女 on 2015/9/19.
 */
public class DrawBoard extends View implements View.OnTouchListener {

    private Context mContext;

    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private List<Path> savePath = new ArrayList<Path>();

    private Bitmap mBitmap = null;
    private Paint mBitmapPaint;
    private Canvas mCanvas;
    private int backgroundColor;

    public DrawBoard(Context context) {
        super(context);
    }

    public DrawBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        setOnTouchListener(this);
    }

    public DrawBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        // 初始化画笔
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        int[] screenSize = ScreenUtil.getScreenSize(mContext);
        mBitmap = Bitmap.createBitmap(screenSize[0], screenSize[1] * 3 / 5,
                Bitmap.Config.RGB_565);
        mBitmap.eraseColor(Color.argb(0, 0, 0, 0));
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.TRANSPARENT);
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mBitmapPaint.setAlpha(0x00); //设置透明程度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        if (mPath != null)
            canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath, mPaint);
                savePath.add(mPath);
                break;
        }

        return true;
    }

    /**
     * 外部调用，设置board的背景色
     * @param color
     */
    public void setBoardColor(int color){
        mCanvas.drawColor(color);
    }

    /**
     * 撤销功能
     */
    public void toLastPath() {
        int length = savePath.size() - 1;
        if (length >= 0) {
            clear();
            savePath.remove(length--);
            mPath = savePath.get(length);
            invalidate();
        }
    }

    /**
     * 清屏
     */
    public void clear() {
        mPath.reset();
        mCanvas.drawColor(Color.TRANSPARENT);
        invalidate();
    }

    /*
        * 保存所绘图形
        * 返回绘图文件的存储路径
        * */
    public String saveBitmap() {
        //获得系统当前时间，并以该时间作为文件名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        String paintPath = null;
        str = str + "paint.png";
        File dir = new File("/sdcard/boards/");
        File file = new File("/sdcard/boards/", str);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            if (file.exists()) {
                file.delete();
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            //保存绘图文件路径
            paintPath = "/sdcard/boards/" + str;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return paintPath;
    }

    public void release() {
        if (mBitmap != null)
            mBitmap.recycle();
    }

}
