package com.rva.mrb.vivify.View.Alert;

import com.rva.mrb.vivify.ApplicationComponent;
import com.rva.mrb.vivify.ApplicationModule;

import dagger.Component;

@Component(modules = {ApplicationModule.class, AlertModule.class}, dependencies = {ApplicationComponent.class})
public interface AlertComponent {
    void inject(AlertActivity alertActivity);
}
