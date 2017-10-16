package com.khosroabadi.myplantaqua.tools;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.di.component.DaggerGreenLandApplicationComponent;
import com.khosroabadi.myplantaqua.di.component.GreenLandApplicationComponent;
import com.khosroabadi.myplantaqua.di.module.ContextModule;
import com.khosroabadi.myplantaqua.webservice.WsInterface;
import com.squareup.picasso.Picasso;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Alireza on 10/31/2016.
 */

@ReportsCrashes(
        formUri = ConstantManager.ACRA.ACRA_URL,
        reportType = HttpSender.Type.JSON,
        httpMethod = HttpSender.Method.PUT,
        formUriBasicAuthLogin=ConstantManager.ACRA.ACRA_USER,
        formUriBasicAuthPassword=ConstantManager.ACRA.ACRA_PASS,
        mode = ReportingInteractionMode.DIALOG,
        resDialogTitle = R.string.application_crash_dialog_title,
        resDialogText = R.string.application_crash_dialog_message,
        resDialogTheme = R.style.MyAlertDialogStyle
)


public class MyApp extends Application {
/*
    private WsInterface apiService;
    private Picasso picasso;*/
    private GreenLandApplicationComponent component;

    public static MyApp get(Activity activity){
        return (MyApp) activity.getApplicationContext();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansWeb.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Timber.plant(new Timber.DebugTree());

        component = DaggerGreenLandApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

/*        apiService = component.getApiService();

        picasso = component.getPicasso();*/

       // ACRA.init(this);
    }


    public GreenLandApplicationComponent getGLandApplicationComponent(){
        return component;
    }
}
