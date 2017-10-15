package com.khosroabadi.myplantaqua.di.module;

import com.khosroabadi.myplantaqua.webservice.WsInterface;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by khosroabadi on 10/15/2017.
 */

@Module(includes = NetworkModule.class)
public class ApiServiceModule {
    private final static  String BASE_URL="http://91.98.30.138:8080/MyAquaPlant/";

    @Provides
    public WsInterface provideApiService(Retrofit retrofit){
        return retrofit.create(WsInterface.class);
    }

    @Provides
    public Retrofit provideRetrofite(OkHttpClient client){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
    }
}
