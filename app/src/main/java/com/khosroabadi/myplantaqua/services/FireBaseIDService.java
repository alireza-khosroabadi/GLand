package com.khosroabadi.myplantaqua.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.khosroabadi.myplantaqua.activity.HomeActivity;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.tools.DeviceInfoUtil;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

/**
 * Created by Alireza on 1/21/2017.
 */

public class FireBaseIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
/*        SharedPreferences topic = getSharedPreferences("Topic", Context.MODE_PRIVATE);
        if(topic.getBoolean("registered_topic" , false) == false) {
            FirebaseMessaging.getInstance().subscribeToTopic("PlanetAquaCustomer");
            SharedPreferences.Editor editor = topic.edit();
            editor.putBoolean("Topic" , true);
            editor.commit();
        }*/
        FirebaseMessaging.getInstance().subscribeToTopic(ConstantManager.TOPIC_NAME);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
            WSUtils wsUtils = new WSUtils(getApplicationContext());
            wsUtils.sendDeviceInfo(DeviceInfoUtil.getDeviceInfo(getApplicationContext(), refreshedToken), new WSUtils.RatingCallback() {
                @Override
                public void onSuccess(Boolean result) {
                     SharedPreferences topic = getSharedPreferences("Topic", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = topic.edit();
                    editor.putBoolean(ConstantManager.TOPIC, true);
                    editor.commit();
                }
            });
        }

}
