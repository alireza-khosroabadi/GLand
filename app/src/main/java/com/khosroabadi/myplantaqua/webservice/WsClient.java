package com.khosroabadi.myplantaqua.webservice;

import com.khosroabadi.myplantaqua.tools.ConstantManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alireza on 12/4/2016.
 */

public class WsClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit== null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantManager.BASE_URL)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getRequestHeader() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }
}
