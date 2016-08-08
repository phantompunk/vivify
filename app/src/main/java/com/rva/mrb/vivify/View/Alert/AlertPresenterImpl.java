package com.rva.mrb.vivify.View.Alert;

import com.rva.mrb.vivify.Model.RealmService;

/**
 * Created by rigo on 8/6/16.
 */
public class AlertPresenterImpl implements AlertPresenter {

    private final RealmService mRealmService;
    private AlertView mAlertView = new AlertView.EmptypAlarmView();

    public AlertPresenterImpl(RealmService realmService) {
        mRealmService = realmService;
    }
    @Override
    public void setView(AlertView view) {
        mAlertView = view;
    }

    @Override
    public void clearView() {
        mAlertView = new AlertView.EmptypAlarmView();
    }

    @Override
    public void closeRealm() {
        mRealmService.closeRealm();
    }
}
