package com.rva.mrb.vivify.Model;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rigo on 6/25/16.
 */
public class RealmService {

    private final Realm mRealm;

    public RealmService(final Realm realm) {
        mRealm = realm;
    }

    public void closeRealm() {
        mRealm.close();
    }

    public RealmResults<Alarm> getAllAlarms() {
        return mRealm.where(Alarm.class).findAll();
    }

    public Alarm getAlarm(final int alarmId) {
        return mRealm.where(Alarm.class).equalTo("id", alarmId).findFirst();
    }
}
