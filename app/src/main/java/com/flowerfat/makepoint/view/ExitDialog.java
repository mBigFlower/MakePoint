package com.flowerfat.makepoint.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.flowerfat.makepoint.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/1/9.
 */
public class ExitDialog extends AlertDialog {

    private OnExitListener mInterface;

    public ExitDialog(Context context) {
        super(context);
    }

    public ExitDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @OnClick(R.id.dialogExit_cancel)
    void cancelClick() {
        dismiss();
        if (mInterface != null)
            mInterface.cancel();
    }

    @OnClick(R.id.dialogExit_exit)
    void exitClick() {
        dismiss();
        if (mInterface != null)
            mInterface.exit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mInterface != null)
                    mInterface.cancel();
            }
        });
    }

    public ExitDialog setOnExitListener(OnExitListener listener) {
        mInterface = listener;
        return this;
    }

    public interface OnExitListener {
        void exit();

        void cancel();
    }
}
