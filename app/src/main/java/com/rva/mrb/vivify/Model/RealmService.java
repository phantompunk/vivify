package com.rva.mrb.vivify.Model;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Bao on 6/24/16.
 */
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
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(final Realm realm) {
                Alarm alarm = realm.createObject(Alarm.class);
                alarm.setId(mRealm.where(Alarm.class).findAll().size());
                alarm.setmTime(time);
                alarm.setmAlarmSet(isSet);
                alarm.setmStandardTime(isStandardTime);
                alarm.setmRepeat(repeat);
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
