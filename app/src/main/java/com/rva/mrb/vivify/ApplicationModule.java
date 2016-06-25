package com.rva.mrb.vivify;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rigo on 6/24/16.
 */
@Module
public class ApplicationModule {

    AlarmApplication mAlarmApplication;

    public ApplicationModule(AlarmApplication application) {
        mAlarmApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mAlarmApplication;
    }
}
