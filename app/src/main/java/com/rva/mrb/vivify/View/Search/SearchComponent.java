package com.rva.mrb.vivify.View.Search;


import com.rva.mrb.vivify.ApplicationComponent;
import com.rva.mrb.vivify.ApplicationModule;

import dagger.Component;

@Component(modules = {ApplicationModule.class, SearchModule.class}, dependencies ={ApplicationComponent.class})
public interface SearchComponent {
    void inject(SearchActivity searchActivity);
}
