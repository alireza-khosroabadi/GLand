package com.khosroabadi.myplantaqua.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khosroabadi.myplantaqua.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Co2Fragment extends Fragment {


    public Co2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_co2, container, false);
    }

}
