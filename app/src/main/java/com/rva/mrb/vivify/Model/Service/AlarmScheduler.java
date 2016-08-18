package com.rva.mrb.vivify.Model.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.Model.Data.Alarm;
import com.rva.mrb.vivify.Model.RealmHelper.DaggerRealmHelperComponent;
import com.rva.mrb.vivify.Model.RealmHelper.RealmHelper;
import com.rva.mrb.vivify.Model.RealmHelper.RealmHelperComponent;
import com.rva.mrb.vivify.Model.RealmHelper.RealmHelperModule;

import javax.inject.Inject;

public class AlarmScheduler extends WakefulBroadcastReceiver{

    @Inject static RealmHelper realmHelper;
//    static AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        RealmHelperComponent realmHelperComponent = DaggerRealmHelperComponent.builder()
                .applicationModule(new ApplicationModule((AlarmApplication) context.getApplicationContext()))
                .realmHelperModule(new RealmHelperModule(this))
                .applicationComponent(((AlarmApplication) context.getApplicationContext()).getComponent())
                .build();
        realmHelperComponent.inject(this);

        Log.d("AlarmScheduler", "On Receive Success!!");
    }



    public static void setNextAlarm(Context context, int alarmId) {
        Log.d("SetNextAlarm", "Called by Detail Presenter");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Alarm alarm = null; // alarmhelp get alarm

        Intent intent = new Intent(context, WakeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+
                100*60, 60*1000, pendingIntent);
//        Log.d("realm", realmHelper.);
        Log.d("Manager", "Trigger time: " +alarmManager);
    }

    public static void cancelNextAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WakeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0 , intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);

    }

    public static void setNextAlarmById(Context context) {


    }
}
