package com.rva.mrb.vivify.View.Alarm;

import com.rva.mrb.vivify.ApplicationComponent;
import com.rva.mrb.vivify.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bao on 6/24/16.
 */

@Singleton
@Component(modules = {ApplicationModule.class, AlarmModule.class}, dependencies = ApplicationComponent.class)
public interface AlarmComponent {
    void inject(MainActivity mainActivity);
}
