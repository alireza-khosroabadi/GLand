package com.khosroabadi.myplantaqua.tools;

import android.app.Application;
import android.content.Intent;

import com.khosroabadi.myplantaqua.R;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

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
    @Override
    public void onCreate(){
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansWeb.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

       // ACRA.init(this);
    }
}
