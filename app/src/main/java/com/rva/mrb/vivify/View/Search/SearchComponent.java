package com.rva.mrb.vivify.View.Search;


import com.rva.mrb.vivify.ApplicationComponent;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.Spotify.NodeModule;
import com.rva.mrb.vivify.Spotify.SpotifyModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, SearchModule.class}, dependencies ={ApplicationComponent.class})
public interface SearchComponent {
    void inject(SearchActivity searchActivity);
}
