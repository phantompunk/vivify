package com.rva.mrb.vivify.Model.AlarmHelper;

import com.rva.mrb.vivify.Model.RealmService;
import com.rva.mrb.vivify.View.AlarmSetupManager;

import dagger.Module;
import dagger.Provides;

@Module
public class AlarmHelperModule {
    private final AlarmSetupManager alarmSetupManager;
    public AlarmHelperModule(AlarmSetupManager alarmSetupManager) {
        this.alarmSetupManager = alarmSetupManager;
    }
    @Provides
    AlarmHelper providesAlarmHelper(RealmService realmService) {
        return new AlarmHelper(realmService);
    }
}
