package com.rva.mrb.vivify.View.AddNewAlarm;

import com.rva.mrb.vivify.Model.RealmService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bao on 6/25/16.
 */
@Module
public class AlarmDetailModule {

    @Provides
    AlarmDetailPresenter providesNewAlarmPresenterImpl(RealmService realmService){
        return new AlarmDetailPresenterImpl(realmService);
    }
}
