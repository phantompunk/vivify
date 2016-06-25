package com.rva.mrb.vivify.View.Alarm;

import com.rva.mrb.vivify.Model.Alarm;

import io.realm.RealmResults;

/**
 * Created by rigo on 6/25/16.
 */
public interface AlarmsView {

    void showAlarms(RealmResults<Alarm> alarms);

    class EmptyAlarmsList implements AlarmsView {

        @Override
        public void showAlarms(RealmResults<Alarm> alarms) {

        }
    }
}
