package com.rva.mrb.vivify.Model.AlarmHelper;

import com.rva.mrb.vivify.ApplicationComponent;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.View.AlarmSetupManager;

import dagger.Component;

@Component(modules = {ApplicationModule.class, AlarmHelperModule.class},
    dependencies = ApplicationComponent.class)
public interface AlarmHelperComponent {
    void inject(AlarmSetupManager alarmSetupManager);
}
