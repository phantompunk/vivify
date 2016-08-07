package com.rva.mrb.vivify.View.AddNewAlarm;

import com.rva.mrb.vivify.ApplicationComponent;
import com.rva.mrb.vivify.ApplicationModule;

import dagger.Component;

@Component(modules = {ApplicationModule.class, AlarmDetailModule.class}, dependencies = {ApplicationComponent.class})
public interface AlarmDetailComponent {

    void inject(AlarmDetailActivity newAlarmActivity);
}
