package com.rva.mrb.vivify;

import com.rva.mrb.vivify.Model.Service.RealmService;

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

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    RealmService provideRealmService(final Realm realm) { return new RealmService(realm); }

}
