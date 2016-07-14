package com.rva.mrb.vivify.View.Alarm;

import com.rva.mrb.vivify.Model.Alarm;

import io.realm.RealmResults;

/**
 * Created by rigo on 6/25/16.
 */
public interface AlarmsView {

    void showAlarms(RealmResults<Alarm> alarms);
    void showAddNewAlarmView();
    void showSearchView();

    class EmptyAlarmsList implements AlarmsView {

        @Override
        public void showAlarms(RealmResults<Alarm> alarms) {

        }

        @Override
        public void showAddNewAlarmView(){}

        @Override
        public void showSearchView() {

        }
    }
}
