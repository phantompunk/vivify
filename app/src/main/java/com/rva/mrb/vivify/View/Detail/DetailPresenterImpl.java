package com.rva.mrb.vivify.View.Detail;

import com.rva.mrb.vivify.Model.Data.Alarm;
import com.rva.mrb.vivify.Model.Service.RealmService;

public class DetailPresenterImpl implements DetailPresenter, RealmService.OnTransactionCallback {

    private final RealmService mRealmService;
    private DetailView mDetailView = new DetailView.EmptyDetailView();

    public DetailPresenterImpl(RealmService realmService){ mRealmService = realmService; }

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
        int newestAlarmId = mRealmService.getNewestAlarm().getId(); // getAlarm.last()
        // call static alarmsetupmanager
    }

    @Override
    public void setView(DetailView view) {
        mDetailView = view;
    }

    @Override
    public void clearView() {
        mDetailView = new DetailView.EmptyDetailView();
    }

    @Override
    public void closeRealm() {
        mRealmService.closeRealm();
    }
}
