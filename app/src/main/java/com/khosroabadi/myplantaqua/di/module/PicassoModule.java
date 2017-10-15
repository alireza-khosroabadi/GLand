package com.khosroabadi.myplantaqua.di.module;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.khosroabadi.myplantaqua.di.scope.GreenLandApplicationScope;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by khosroabadi on 10/15/2017.
 */

@Module(includes = {ContextModule.class , NetworkModule.class})
public class PicassoModule {

    @Provides
    @GreenLandApplicationScope
    public Picasso providePicasso(Context context , OkHttp3Downloader downloader){
        return new Picasso.Builder(context)
                .downloader(downloader)

                .build();
    }

    @Provides
    @GreenLandApplicationScope
    public OkHttp3Downloader provideOkHttp3Downloader(OkHttpClient client){
        return new OkHttp3Downloader(client);
    }
}
