package com.rva.mrb.vivify.Model.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.Model.Data.Alarm;
//import com.rva.mrb.vivify.Model.RealmHelper.DaggerRealmHelperComponent;
//import com.rva.mrb.vivify.Model.RealmHelper.RealmHelper;
//import com.rva.mrb.vivify.Model.RealmHelper.RealmHelperComponent;
//import com.rva.mrb.vivify.Model.RealmHelper.RealmHelperModule;

import javax.inject.Inject;

import io.realm.Realm;

public class AlarmScheduler extends WakefulBroadcastReceiver{

    public static final String TAG = AlarmScheduler.class.getSimpleName();

//    @Inject static RealmHelper realmHelper;
//    static AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
//        RealmHelperComponent realmHelperComponent = DaggerRealmHelperComponent.builder()
//                .applicationModule(new ApplicationModule((AlarmApplication) context.getApplicationContext()))
//                .realmHelperModule(new RealmHelperModule(this))
//                .applicationComponent(((AlarmApplication) context.getApplicationContext()).getComponent())
//                .build();
//        realmHelperComponent.inject(this);

        Log.d(TAG, "On Receive Success!!");
    }

    public static Alarm getNextAlarm() {
        Log.d(TAG, "Querying for next enabled alarm");
//        RealmService.updateAlarms();
        return RealmService.getNextPendingAlarm();
    }

    public static void setNextAlarm(Context context) {

        Log.d(TAG, "Setting next alarms");
        cancelNextAlarm(context);
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // grab the next alarm
        // handle some error checking and no results. No alarms set
        Alarm alarm = null;
        try {
            alarm = getNextAlarm();
            Log.d("setNextAlarm", "Alarm time: " + alarm.getTime());
        } catch (Exception e) {
            Log.e(TAG, "No alarms are set. " + e.getMessage());
        }
        if (alarm != null) {

            Intent intent = new Intent(context, WakeReceiver.class);
            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(context, Alarm.FLAG_NEXT_ALARM,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // set alarm manager according to build version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d(TAG, "Manager set and allow at " + alarm.getTime());
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        alarm.getTimeInMillis(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Log.d(TAG, "Manager set exact at " + alarm.getTime());
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                        alarm.getTimeInMillis(), pendingIntent);
            } else {
                Log.d(TAG, "Manager set at " + alarm.getTime());
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        alarm.getTimeInMillis(), pendingIntent);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(alarmManager.getNextAlarmClock() != null)
                    Log.d(TAG, "Trigger time: " +
                            alarmManager.getNextAlarmClock().getTriggerTime());
            }
        }
        else
            Log.d(TAG, "Alarm is not set");

    }

    public static void snoozeNextAlarm(Context context) {
        cancelNextAlarm(context);
        Log.d(TAG, "Snoozing Alarm");
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WakeReceiver.class);
        PendingIntent snoozedIntent = PendingIntent.getBroadcast(context,
                Alarm.FLAG_NEXT_ALARM , intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // update
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() + 1000*60*2, snoozedIntent);
    }

    public static void cancelNextAlarm(Context context) {
        Log.d(TAG, "Cancelling alarm");
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WakeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                Alarm.FLAG_NEXT_ALARM, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
//        RealmService.updateAlarms();
    }

    public static void enableAlarmById(Context context, String id) {

//        Log.d(TAG, "Toggle alarm id: " + id);
//        AlarmManager alarmManager = (AlarmManager)
//                context.getSystemService(Context.ALARM_SERVICE);

        // toggle alarm in database
        RealmService.enableAlarm(id);

        // check alarm date and update as needed
//        RealmService.updateAlarms();

        // if the alarm id is corresponds to an alarm return it
//        Alarm alarm = RealmService.getAlarmById(id);

        // reset the alarm manager and set the next enabled alarm
        setNextAlarm(context);
    }

    private static void updateAlarm(String id) {
        Log.d(TAG, "Checking alarm date");
//        Alarm
    }
}