package com.khosroabadi.myplantaqua.di.module;

import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by khosroabadi on 10/15/2017.
 */

@Module(includes = ContextModule.class)
public class NetworkModule {



    @Provides
    public HttpLoggingInterceptor provideHttpLoggingInterceptor(){

        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });
    }

    @Provides
    public Cache provideCache(File file){
        return new Cache(file , 10*1000*1000);//10mg cache
    }

    @Provides
    public File provideCacheFile(Context context){
        return new File(context.getCacheDir() , "okhttp_cache");
    }

    @Provides
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor , Cache cache){
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
    }
}
