package com.rva.mrb.vivify.View.Alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

public class AlertReciever extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Reciever", "Something happened?");
        Toast.makeText(context, "AlarmManager Worked!!", Toast.LENGTH_LONG).show();

        Intent alert = new Intent(context, AlarmService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(alert);
    }
}
