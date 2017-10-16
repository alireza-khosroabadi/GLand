package com.khosroabadi.myplantaqua.di.component;

import com.khosroabadi.myplantaqua.activity.FilterActivity;
import com.khosroabadi.myplantaqua.di.module.FilterActivityModule;
import com.khosroabadi.myplantaqua.di.scope.FilterActivityScope;

import dagger.Component;

/**
 * Created by khosroabadi on 10/16/2017.
 */

@FilterActivityScope
@Component(modules = FilterActivityModule.class , dependencies = GreenLandApplicationComponent.class)
public interface FilterActivityComponent {

    void injectFilterActivity(FilterActivity filterActivity);
}
