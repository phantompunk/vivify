package com.rva.mrb.vivify;

import android.app.Application;

import com.rva.mrb.vivify.Model.RealmService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by Bao on 6/24/16.
 */
@Module
public class ApplicationModule {

    private AlarmApplication mApp;

    public ApplicationModule(AlarmApplication app) {
        mApp = app;
    }

    @Provides @Singleton
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides @Singleton
    RealmService provideRealmService(final Realm realm) { return new RealmService(realm); }

}
