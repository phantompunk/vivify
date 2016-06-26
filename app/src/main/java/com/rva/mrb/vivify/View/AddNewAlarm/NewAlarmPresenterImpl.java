package com.rva.mrb.vivify.View.AddNewAlarm;

import com.rva.mrb.vivify.Model.RealmService;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Alarm.AlarmsView;

import butterknife.OnClick;

/**
 * Created by Bao on 6/25/16.
 */
public class NewAlarmPresenterImpl implements NewAlarmPresenter, RealmService.OnTransactionCallback {

    private final RealmService mRealmService;
    private NewAlarmView mNewAlarmView = new NewAlarmView.EmptyNewAlarmView();

    public NewAlarmPresenterImpl(RealmService realmService){ mRealmService = realmService; }

    @Override
    public void onRealmSuccess() {

    }

    @Override
    public void onRealmError(Exception e) {

    }

    @Override
    public void onAddClick(String time, boolean isSet, boolean isStandardTime, String repeat) {
        mRealmService.addAlarmAsync(time, isSet, isStandardTime, repeat);
    }

    @Override
    public void setView(NewAlarmView view) {
        mNewAlarmView = view;
    }

    @Override
    public void clearView() {
        mNewAlarmView = new NewAlarmView.EmptyNewAlarmView();
    }

    @Override
    public void closeRealm() {
        mRealmService.closeRealm();
    }
}
