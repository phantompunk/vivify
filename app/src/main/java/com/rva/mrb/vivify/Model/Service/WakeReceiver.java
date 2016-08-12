package com.rva.mrb.vivify.Model.Service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;

import javax.inject.Inject;

public class WakeReceiver extends WakefulBroadcastReceiver {
//    @Inject
//    RealmService realmService;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Reciever", "Something happened?");
        Toast.makeText(context, "AlarmManager Worked!!", Toast.LENGTH_LONG).show();

//        Log.d("Realm", realmService.getMessage());
        Intent alert = new Intent(context, WakeService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(alert);
    }
}
