package com.rva.mrb.vivify.View.Alarm;

import com.rva.mrb.vivify.BasePresenter;
import com.rva.mrb.vivify.Model.Alarm;

import io.realm.RealmList;
import io.realm.RealmResults;

public interface AlarmsPresenter extends BasePresenter<AlarmsView> {
    void onAddNewAlarm();
    void onSearch();
    RealmResults<Alarm> getAllAlarms();
//    void onAlarmClick(int id);
//    String getMessage();
//    String getRSMessage();

}
