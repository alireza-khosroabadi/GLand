package com.khosroabadi.myplantaqua.di.module;

import com.khosroabadi.myplantaqua.activity.FilterActivity;
import com.khosroabadi.myplantaqua.di.scope.FilterActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by khosroabadi on 10/16/2017.
 */

@Module
public class FilterActivityModule {

    private FilterActivity filterActivity;

    public FilterActivityModule(FilterActivity filterActivity) {
        this.filterActivity = filterActivity;
    }

    @Provides
    @FilterActivityScope
    public FilterActivity provideFilterActivity(){
        return filterActivity;
    }
}
