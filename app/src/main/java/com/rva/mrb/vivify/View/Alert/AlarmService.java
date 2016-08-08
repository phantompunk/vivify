package com.rva.mrb.vivify.View.Alert;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class AlarmService extends Service {

    private AlarmBinder alarmBinder = new AlarmBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service", "Creating Service");
        Intent intent = new Intent(getApplicationContext(), AlertActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Service", "Bind Service");
        return alarmBinder;
    }

    public int onStartCommand(Intent intent, int flag, int startId) {
        Log.d("Service", "Starting Service");
        return super.onStartCommand(intent, flag, startId);
    }
    public class AlarmBinder extends Binder {
        public AlarmService getService() {
            return AlarmService.this;
        }
    }
}
