package com.khosroabadi.myplantaqua.di.module;

import android.content.Context;

import com.khosroabadi.myplantaqua.dataModel.da.filterCache.FilterCacheDataProvider;
import com.khosroabadi.myplantaqua.di.ActivityContextQualifaier;
import com.khosroabadi.myplantaqua.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alireza on 10/16/2017.
 */


@Module(includes = ActivityContextModule.class)
public class FilterCacheDataProviderModule {

    @Provides
    @ActivityScope
    public FilterCacheDataProvider providerFilterCacheDataProvider(@ActivityContextQualifaier Context context){
        return new FilterCacheDataProvider(context);
    }
}
