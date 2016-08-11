package com.rva.mrb.vivify.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.Model.AlarmHelper.AlarmHelper;
import com.rva.mrb.vivify.Model.AlarmHelper.AlarmHelperComponent;
import com.rva.mrb.vivify.Model.AlarmHelper.AlarmHelperModule;
import com.rva.mrb.vivify.Model.AlarmHelper.DaggerAlarmHelperComponent;
import com.rva.mrb.vivify.View.Alert.AlarmService;

import javax.inject.Inject;

public class AlarmSetupManager extends WakefulBroadcastReceiver {
    @Inject
    AlarmHelper alarmHelper;
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmHelperComponent alarmHelperComponent = DaggerAlarmHelperComponent.builder()
                .applicationModule(new ApplicationModule((AlarmApplication) context.getApplicationContext()))
                .alarmHelperModule(new AlarmHelperModule(this))
                .applicationComponent(((AlarmApplication) context.getApplicationContext()).getComponent())
                .build();
        alarmHelperComponent.inject(this);

        Log.d("Helper", alarmHelper.getMessage());

        Log.d("Reciever", "Something happened?");
        Toast.makeText(context, "AlarmManager Worked!!", Toast.LENGTH_LONG).show();

        Intent alert = new Intent(context, AlarmService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(alert);
    }
}
