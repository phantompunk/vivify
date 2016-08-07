package com.rva.mrb.vivify.View.AddNewAlarm;

import com.rva.mrb.vivify.Model.Alarm;
import com.rva.mrb.vivify.Model.RealmService;

public class AlarmDetailPresenterImpl implements AlarmDetailPresenter, RealmService.OnTransactionCallback {

    private final RealmService mRealmService;
    private AlarmDetailView mAlarmDetailView = new AlarmDetailView.EmptyAlarmDetailView();

    public AlarmDetailPresenterImpl(RealmService realmService){ mRealmService = realmService; }

    public Alarm getAlarm(int index) {
        return mRealmService.getAlarm(index);
    }

    @Override
    public void onDeleteAlarm(int alarmid) {
        mRealmService.deleteAlarm(alarmid);
    }

    @Override
    public void onSaveAlarm(int alarmid, String name, String time, boolean isSet, boolean isStandardTime, String repeat) {
        mRealmService.saveAlarm(alarmid, name, time, isSet, isStandardTime, repeat);
    }

    @Override
    public void onRealmSuccess() {

    }

    @Override
    public void onRealmError(Exception e) {

    }

    @Override
    public void onAddClick(String name,String time, boolean isSet, boolean isStandardTime, String repeat) {
        mRealmService.addAlarmAsync(name, time, isSet, isStandardTime, repeat);
    }

    @Override
    public void setView(AlarmDetailView view) {
        mAlarmDetailView = view;
    }

    @Override
    public void clearView() {
        mAlarmDetailView = new AlarmDetailView.EmptyAlarmDetailView();
    }

    @Override
    public void closeRealm() {
        mRealmService.closeRealm();
    }
}
