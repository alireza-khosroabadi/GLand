package com.khosroabadi.myplantaqua.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by khosroabadi on 10/15/2017.
 */

@Module
public class ContextModule {

    private final Context mContext;

    public ContextModule(Context context){
     this.mContext = context;
    }

    @Provides
    public Context provideContext(){
        return mContext;
    }
}
