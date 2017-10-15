package com.khosroabadi.myplantaqua.di.module;

import android.content.Context;

import com.khosroabadi.myplantaqua.di.scope.GreenLandApplicationScope;

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
    @GreenLandApplicationScope
    public HttpLoggingInterceptor provideHttpLoggingInterceptor(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @GreenLandApplicationScope
    public Cache provideCache(File file){
        return new Cache(file , 10*1000*1000);//10mg cache
    }

    @Provides
    @GreenLandApplicationScope
    public File provideCacheFile(Context context){
        return new File(context.getCacheDir() , "okhttp_cache");
    }

    @Provides
    @GreenLandApplicationScope
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor , Cache cache){
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
    }
}
