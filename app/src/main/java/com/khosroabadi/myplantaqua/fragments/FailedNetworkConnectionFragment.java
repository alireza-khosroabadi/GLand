package com.khosroabadi.myplantaqua.fragments;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.activity.HomeActivity;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FailedNetworkConnectionFragment extends Fragment {

    TextView retryBtn;
    WSUtils wsUtils ;


    public FailedNetworkConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        wsUtils = new WSUtils(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_faild_network_connection, container, false);
        retryBtn = (TextView) view.findViewById(R.id.network_fail_btn);


        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wsUtils.isNetworkAvailable()){
                    goToHomeActivity();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wsUtils.isNetworkAvailable()){
            goToHomeActivity();
        }
    }


    private void goToHomeActivity(){
        Intent intent = new Intent(getActivity().getApplication() , HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
