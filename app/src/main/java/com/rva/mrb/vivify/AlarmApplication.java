package com.rva.mrb.vivify;

import android.app.Application;
import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Bao on 6/24/16.
 */
public class AlarmApplication extends Application {
    private ApplicationComponent mComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        initRealmConfiguration();
    }

    private void initRealmConfiguration() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }
}
