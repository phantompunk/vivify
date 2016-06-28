package com.rva.mrb.vivify.View.Alarm;

import com.rva.mrb.vivify.BasePresenter;
import com.rva.mrb.vivify.Model.Alarm;

import io.realm.RealmList;

/**
 * Created by rigo on 6/25/16.
 */
public interface AlarmsPresenter extends BasePresenter<AlarmsView> {
    void onAlarmClick(int id);
    void onAddNewAlarm();
    String getMessage();
    String getRSMessage();
    RealmList<Alarm> getAllAlarms();
}
