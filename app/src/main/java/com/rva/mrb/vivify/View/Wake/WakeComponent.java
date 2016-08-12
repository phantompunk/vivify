package com.rva.mrb.vivify.View.Wake;

import com.rva.mrb.vivify.ApplicationComponent;
import com.rva.mrb.vivify.ApplicationModule;

import dagger.Component;

@Component(modules = {ApplicationModule.class, WakeModule.class}, dependencies = {ApplicationComponent.class})
public interface WakeComponent {
    void inject(WakeActivity wakeActivity);
}
