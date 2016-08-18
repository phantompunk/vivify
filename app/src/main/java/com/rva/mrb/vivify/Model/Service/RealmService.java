package com.rva.mrb.vivify.Model.Service;

import android.util.Log;

import com.rva.mrb.vivify.Model.Data.Alarm;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmService {

    private final Realm mRealm;

    public RealmService(final Realm realm) {
        mRealm = realm;
    }

    public RealmResults<Alarm> getAllAlarms() {
        return mRealm.where(Alarm.class).findAll();
    }

    public Alarm getAlarm(final String alarmId) {
        return mRealm.where(Alarm.class).equalTo("id", alarmId).findFirst();
    }

    public static void enableAlarm(final String alarmId) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Alarm alarm = realm.where(Alarm.class).equalTo("id", alarmId).findFirst();
                alarm.setEnabled(!alarm.isEnabled());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("EditAlarm", "Alarm Enabled: " );
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("EditAlarm", "failed: " + error.getMessage());
            }
        });
    }
    // Return the the newest alarm by pulling the highest alarm id
    public String getNextAlarm() {
        if (mRealm.where(Alarm.class).equalTo("enabled",true).findAll().size() > 0)
            return mRealm.where(Alarm.class).equalTo("enabled", true).findAll()
                .sort("time").first().getmWakeTime();
        else
            return "No Alarm set";
    }

    public void saveAlarm(final String alarmId, final String name, final String time, final boolean isSet, final boolean isStandardTime, final String repeat) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Alarm editAlarm = realm.where(Alarm.class).equalTo("id", alarmId).findFirst();
                editAlarm.setAlarmLabel(name);
                editAlarm.setId(alarmId);
                editAlarm.setmWakeTime(time);
                editAlarm.setTime(time);
                editAlarm.setEnabled(isSet);
                editAlarm.setmStandardTime(isStandardTime);
                editAlarm.setmRepeat(repeat);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("EditAlarm", "Success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("EditAlarm", "failed: " + error.getMessage());
            }
        });
    }

    public void deleteAlarm(final String alarmId) {
        final Realm realm = Realm.getDefaultInstance();
//        Log.d("Realm", realm.toString());
        final RealmResults<Alarm> results = realm.where(Alarm.class).equalTo("id", alarmId).findAll();
//        Log.d("realm", results.get(0).getmWakeTime());
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Alarm.class).equalTo("id", alarmId).findAll().deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("Successful", "Alarm deleted");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("Error", error.getMessage());
            }
        });
    }

    public void addAlarmAsync(final String name, final String time, final boolean isSet, final boolean isStandardTime, final String repeat) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(final Realm realm) {
                Alarm alarm = realm.createObject(Alarm.class);
                alarm.setId(UUID.randomUUID().toString());
                alarm.setAlarmLabel(name);
                alarm.setmWakeTime(time);
                alarm.setTime(time);
                alarm.setEnabled(isSet);
                alarm.setmStandardTime(isStandardTime);
                alarm.setmRepeat(repeat);
//                Log.d("Alarm"alarm.getTime();
            }
        }, new Realm.Transaction.OnSuccess() {

            @Override
            public void onSuccess() {
                Log.d("successful", "Successful transaction!");
            }
        }, new Realm.Transaction.OnError(){

            @Override
            public void onError(Throwable error) {
                Log.d("error", error.getMessage());
            }
        });
    }

    public String getMessage(){
        return "From realmService!!";
    }

    public void closeRealm() {
        mRealm.close();
    }

    public interface OnTransactionCallback {
        void onRealmSuccess();
        void onRealmError(final Exception e);
    }
}
