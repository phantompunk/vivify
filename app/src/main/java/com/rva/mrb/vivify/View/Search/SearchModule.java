package com.rva.mrb.vivify.View.Search;

import com.rva.mrb.vivify.Model.RealmService;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    private final SearchActivity activity;

    public SearchModule(SearchActivity activity) {
        this.activity = activity;
    }

    @Provides
    SearchPresenter providesNewAlarmPresenterImpl(RealmService realmService) {
        return new SearchPresenterImpl(realmService);
    }
}
