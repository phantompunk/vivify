package com.rva.mrb.vivify.View.Alert;

import com.rva.mrb.vivify.Model.RealmService;

import dagger.Module;
import dagger.Provides;

@Module
public class AlertModule {

    private final AlertActivity activity;

    public AlertModule(AlertActivity activity) {
        this.activity = activity;
    }

    @Provides
    AlertPresenter providesAlertPresenterImpl(RealmService realmService) {
        return new AlertPresenterImpl(realmService);
    }
}
