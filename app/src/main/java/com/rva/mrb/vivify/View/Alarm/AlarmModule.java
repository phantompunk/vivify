package com.rva.mrb.vivify.View.Alarm;

import com.rva.mrb.vivify.Model.RealmService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bao on 6/24/16.
 * Dagger Module that provides an AlarmPresenter.
 */
@Module
public class AlarmModule {

    private final AlarmActivity activity;
    public AlarmModule(AlarmActivity activity){ this.activity = activity; };

    @Provides
    AlarmsPresenter providesAlarmPresenterImpl(RealmService realmService){
        return new AlarmPresenterImpl(realmService);
    };

}
