package com.rva.mrb.vivify.View.Alarm;

import com.rva.mrb.vivify.BasePresenter;
import com.rva.mrb.vivify.Model.Data.Alarm;

import io.realm.RealmResults;

public interface AlarmsPresenter extends BasePresenter<AlarmsView> {
    void onAddNewAlarm();
    void onSearch();
    RealmResults<Alarm> getAllAlarms();

    String getNextAlarmTime();
//    void onAlarmClick(int id);
//    String getMessage();
//    String getRSMessage();

}
