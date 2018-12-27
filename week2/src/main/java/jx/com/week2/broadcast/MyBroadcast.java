package jx.com.week2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import jx.com.week2.activity.DetailsActivity;

public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_OPENED")){
            Intent intent1 = new Intent(context, DetailsActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent1);
        }

    }
}
