package com.rva.mrb.vivify.View.Alert;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class AlarmService extends Service {

    private Intent alertIntent;
    private AlarmBinder alarmBinder = new AlarmBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service", "Creating Service");
        alertIntent = new Intent(getApplicationContext(), AlertActivity.class);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Service", "Bind Service");
        return alarmBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        super.onStartCommand(intent, flag, startId);
        Log.d("Service", "Starting Service");
//        Intent intent1 = new Intent(getApplicationContext(), AlertActivity.class);
        alertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(alertIntent);
        return START_STICKY;
    }
    public class AlarmBinder extends Binder {
        public AlarmService getService() {
            return AlarmService.this;
        }
    }
}
