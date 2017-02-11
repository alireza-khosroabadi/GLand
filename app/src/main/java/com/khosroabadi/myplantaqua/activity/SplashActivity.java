package com.khosroabadi.myplantaqua.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Window;

import com.google.firebase.messaging.FirebaseMessaging;
import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.dataModel.dm.deviceInfo.DeviceInfoBean;
import com.khosroabadi.myplantaqua.fragments.FailedNetworkConnectionFragment;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.tools.DeviceInfoUtil;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        checkPermission();

    }

    private void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                    Manifest.permission.READ_PHONE_STATE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_PHONE_STATE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else {
goToHomeActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToHomeActivity();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    private void goToHomeActivity(){
                new Handler().postDelayed(new Runnable() {
                    WSUtils wsUtils = new WSUtils(getApplicationContext());
                    @Override
                    public void run() {
                        try {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            if (!wsUtils.isNetworkAvailable()) {

                                FailedNetworkConnectionFragment failedNetworkConnectionFragment = new FailedNetworkConnectionFragment();
                                fragmentTransaction.add(R.id.fragment_container, failedNetworkConnectionFragment, null);
                                fragmentTransaction.commit();
                            } else {

                                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            }
                            //      }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } , ConstantManager.SPLASH_TIME);

    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context context){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }


}
