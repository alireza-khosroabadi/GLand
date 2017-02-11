package com.khosroabadi.myplantaqua.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.khosroabadi.myplantaqua.dataModel.dm.deviceInfo.DeviceInfoBean;

/**
 * Created by Alireza on 1/20/2017.
 */

public class DeviceInfoUtil {

    public static DeviceInfoBean getDeviceInfo(Context mContext , String pushId){
        DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
        deviceInfoBean.setAndroidVersion(Build.VERSION.RELEASE);
        deviceInfoBean.setApiLevel(Build.VERSION.SDK_INT);
        deviceInfoBean.setBoard(Build.BOARD);
        deviceInfoBean.setBrand(Build.BRAND);
        deviceInfoBean.setBuildNumber(Build.VERSION.INCREMENTAL);
        deviceInfoBean.setDevice(Build.DEVICE);
        deviceInfoBean.setHardware(Build.HARDWARE);
        deviceInfoBean.setManufactorer(Build.MANUFACTURER);
        deviceInfoBean.setModel(Build.MODEL);
        deviceInfoBean.setSerial(Build.SERIAL);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager window = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        window.getDefaultDisplay().getMetrics(dm);
        Display display = window.getDefaultDisplay();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double)width / (double)dens;
        double hi = (double)height / (double)dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x+y);
        deviceInfoBean.setResolution(String.valueOf(width)+"*"+String.valueOf(height));
        deviceInfoBean.setDentisy(mContext.getResources().getDisplayMetrics().toString());
        deviceInfoBean.setPhysicalSize(String.valueOf(screenInches));

        deviceInfoBean.setDentisy(String.valueOf(dens));

        deviceInfoBean.setPushID(pushId);
        deviceInfoBean.setActive(true);
        deviceInfoBean.setTopic(ConstantManager.TOPIC_NAME);

        TelephonyManager tManager = (TelephonyManager)mContext.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        deviceInfoBean.setUuid(tManager.getDeviceId());

        return deviceInfoBean;

    }
}
