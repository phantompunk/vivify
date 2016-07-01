package com.rva.mrb.vivify.Model;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmService {

    private final Realm mRealm;

    public RealmService(final Realm realm) {
        mRealm = realm;
    }

    public RealmResults<Alarm> getAllAlarms() {
        return mRealm.where(Alarm.class).findAll();
    }

    public Alarm getAlarm(final int alarmId) {
        return mRealm.where(Alarm.class).equalTo("id", alarmId).findFirst();
    }

    public void addAlarmAsync(final String time, final boolean isSet, final boolean isStandardTime, final String repeat) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(final Realm realm) {
                Alarm alarm = realm.createObject(Alarm.class);
                alarm.setId(realm.where(Alarm.class).findAll().size());
                alarm.setmWakeTime(time);
                alarm.setmIsSet(isSet);
                alarm.setmStandardTime(isStandardTime);
                alarm.setmRepeat(repeat);

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
