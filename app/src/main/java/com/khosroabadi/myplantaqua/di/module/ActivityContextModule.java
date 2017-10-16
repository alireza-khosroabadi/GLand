package com.khosroabadi.myplantaqua.di.module;

import android.app.Activity;
import android.content.Context;

import com.khosroabadi.myplantaqua.di.ActivityContextQualifaier;
import com.khosroabadi.myplantaqua.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alireza on 10/16/2017.
 */

@Module
public class ActivityContextModule {

    private final Activity mContext;

    public ActivityContextModule(Activity context){
        this.mContext = context;
    }

    @Provides
    @ActivityScope
    @ActivityContextQualifaier
    public Context provideContext(){
        return mContext;
    }
}
