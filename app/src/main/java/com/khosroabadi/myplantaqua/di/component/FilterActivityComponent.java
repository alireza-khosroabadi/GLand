package com.khosroabadi.myplantaqua.di.component;

import com.khosroabadi.myplantaqua.activity.FilterActivity;
import com.khosroabadi.myplantaqua.di.module.FilterActivityModule;
import com.khosroabadi.myplantaqua.di.module.FilterCacheDataProviderModule;
import com.khosroabadi.myplantaqua.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by khosroabadi on 10/16/2017.
 */

@ActivityScope
@Component(modules = {FilterActivityModule.class ,
        FilterCacheDataProviderModule.class} ,
        dependencies = GreenLandApplicationComponent.class)
public interface FilterActivityComponent {

    void injectFilterActivity(FilterActivity filterActivity);
}
