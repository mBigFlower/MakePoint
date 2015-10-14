package com.flowerfat.makepoint.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 明明大美女 on 2015/10/10.
 *
 * 原谅我的无聊 。
 * 目的：
 *     如果setText(text)的时候，text为null，就不做处理
 * 区别：
 *     原始的Textview 如果text为null的时候，那么就直接为空白了
 */
public class GoodTextView extends TextView {

    public GoodTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // 重写了setText方法
    public void setText(CharSequence text, BufferType type) {
        if(text != null)
            super.setText(text, type);
    }
}
