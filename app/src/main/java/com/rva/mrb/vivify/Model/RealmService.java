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

    public String getMessage(){
        return "From realmService!!";
    }

    public void closeRealm() {
        mRealm.close();
    }
}
