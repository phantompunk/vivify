package com.rva.mrb.vivify.Model.Service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.Model.RealmHelper.DaggerRealmHelperComponent;
import com.rva.mrb.vivify.Model.RealmHelper.RealmHelper;
import com.rva.mrb.vivify.Model.RealmHelper.RealmHelperComponent;
import com.rva.mrb.vivify.Model.RealmHelper.RealmHelperModule;

import javax.inject.Inject;

public class AlarmScheduler extends WakefulBroadcastReceiver{

    @Inject RealmHelper realmHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        RealmHelperComponent realmHelperComponent = DaggerRealmHelperComponent.builder()
                .applicationModule(new ApplicationModule((AlarmApplication) context.getApplicationContext()))
                .realmHelperModule(new RealmHelperModule(this))
                .applicationComponent(((AlarmApplication) context.getApplicationContext()).getComponent())
                .build();
        realmHelperComponent.inject(this);
    }



    public static void setNextAlarm(Context context, int alarmId) {
        /*alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AlarmActivity.this, AlarmsdfsSetupManager.class);
        alarmIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 5);
        cal.set(Calendar.MINUTE, 34);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()
                + 60*100, 1000*60*2, alarmIntent);*/
       /* AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Alarm alarm = null; // alarmhelp get alarm

        Intent intent = new Intent(context, AlerteReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+
                1000*30, pendingIntent);*/
    }

    public static void cancelNextAlarm(Context context) {

    }

    public static void setNextAlarmById(Context context) {

    }
}
