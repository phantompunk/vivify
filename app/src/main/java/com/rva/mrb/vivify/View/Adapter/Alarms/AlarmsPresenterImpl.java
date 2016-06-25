package com.rva.mrb.vivify.View.Adapter.Alarms;

import com.rva.mrb.vivify.Model.RealmService;

/**
 * Created by rigo on 6/25/16.
 */
public class AlarmsPresenterImpl implements AlarmsPresenter {

    private final RealmService mRealmService;

    private AlarmsView mAlarmsView = new AlarmsView.EmptyAlarmsList();
    private boolean alarmsWereShown = false;

    public AlarmsPresenterImpl(final RealmService realmService) {
        mRealmService = realmService;
    }

    @Override
    public void onAlarmClick(int id) {

    }

    @Override
    public void onAddNewAlarm() {
    }

    @Override
    public void setView(AlarmsView view) {
        mAlarmsView = view;
        showAlarmsIfNeeded();
    }

    public void showAlarmsIfNeeded() {
        if(!alarmsWereShown) {
            mAlarmsView.showAlarms(mRealmService.getAllAlarms());
            alarmsWereShown = true;
        }
    }

    @Override
    public void clearView() {
        mAlarmsView = new AlarmsView.EmptyAlarmsList();
    }

    @Override
    public void closeRealm() {
        mRealmService.closeRealm();
    }
}
