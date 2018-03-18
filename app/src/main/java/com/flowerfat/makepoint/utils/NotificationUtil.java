package com.flowerfat.makepoint.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.flowerfat.makepoint.entity.db.PointManager;
import com.flowerfat.makepoint.MyApplication;
import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.activity.MainActivity;

/**
 * Created by 明明大美女 on 2016/4/19.
 */
public class NotificationUtil {

    public static final int ID_NOTIFICATION = 123;

    public static void setSwitchState(boolean isNotification){
        SpInstance.get().setNotification(isNotification);
        if(isNotification) {
            show();
        } else {
            dismiss();
        }
    }

    public static void show() {
        Intent intent = new Intent(MyApplication.getInstance(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance(), 0, intent, 0);

        RemoteViews rv = new RemoteViews(MyApplication.getInstance().getPackageName(),
                R.layout.layout_notification);
        rv.setTextViewText(R.id.notification_tv1, PointManager.get().getPoint1().getTitle());
        rv.setTextViewText(R.id.notification_tv2, PointManager.get().getPoint2().getTitle());
        rv.setTextViewText(R.id.notification_tv3, PointManager.get().getPoint3().getTitle());
        rv.setTextViewText(R.id.notification_tv4, PointManager.get().getPoint4().getTitle());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getInstance());
        builder.setContent(rv).setOngoing(true).setSmallIcon(R.drawable.img_yellowman2);
        builder.setContentIntent(pendingIntent);
        // 5.0系统以后，要弄一个黑白的图标
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.mipmap.logo);
        } else {
            builder.setSmallIcon(R.mipmap.logo);
        }

        NotificationManager notificationManager = (NotificationManager)
                MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_NOTIFICATION, builder.build());
    }

    public static void dismiss() {
        NotificationManager manger = (NotificationManager)
                MyApplication.getInstance().getSystemService(MyApplication.getInstance().NOTIFICATION_SERVICE);
        manger.cancel(ID_NOTIFICATION);
    }

    public static void init(){

    }

    public static void refresh(){
        if(SpInstance.get().isNotification()) {
            dismiss();
            show();
        }
    }
}
