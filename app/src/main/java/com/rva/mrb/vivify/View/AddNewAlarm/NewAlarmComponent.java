package com.rva.mrb.vivify.View.AddNewAlarm;

import com.rva.mrb.vivify.ApplicationComponent;
import com.rva.mrb.vivify.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bao on 6/25/16.
 */

@Component(modules = {ApplicationModule.class, NewAlarmModule.class}, dependencies = {ApplicationComponent.class})
public interface NewAlarmComponent {

    void inject(NewAlarmActivity newAlarmActivity);
}
