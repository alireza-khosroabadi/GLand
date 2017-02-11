package com.khosroabadi.myplantaqua.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.tools.CalculatorUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DIYFragment extends Fragment {


   private TextView aquariumVolumeLiter, mainGlassThickness, buttomGlassThickness,
    glassArea, glassWeight, aquariumWeight;
    private EditText mLenght, mHeigth, mWidth;
   private Button calculateBtn;

    public DIYFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View mView = inflater.inflate(R.layout.fragment_diy, container, false);


        mHeigth = (EditText) mView.findViewById(R.id.aqua_height);
        mLenght = (EditText) mView.findViewById(R.id.aqua_length);
        mWidth = (EditText) mView.findViewById(R.id.aqua_width);
        mainGlassThickness = (TextView) mView.findViewById(R.id.main_glass_thickness);
        buttomGlassThickness = (TextView) mView.findViewById(R.id.buttom_glass_thickness);
        aquariumVolumeLiter = (TextView) mView.findViewById(R.id.aquariumVolume_liter);
        glassArea = (TextView) mView.findViewById(R.id.glass_area);
        glassWeight = (TextView) mView.findViewById(R.id.glass_weight);
        aquariumWeight = (TextView) mView.findViewById(R.id.aquarium_weight);

        calculateBtn = (Button) mView.findViewById(R.id.diy_calculate_btn);

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String , String> result = new HashMap<String, String>();

                    if(mLenght.getText().toString().equals("") ||  mHeigth.getText().toString().equals("") || mWidth.getText().toString().equals("")){
                    CoordinatorLayout coordinatorLayout = (CoordinatorLayout) mView.findViewById(R.id.diy_coordinatorLayout);
                   Snackbar snackbar =  Snackbar.make(coordinatorLayout , R.string.calculator_values_is_null , Snackbar.LENGTH_LONG);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.GREEN);
                        snackbar.show();
                }else {
                        Double length = Double.parseDouble(mLenght.getText().toString());
                        Double height = Double.parseDouble(mHeigth.getText().toString());
                        Double width = Double.parseDouble(mWidth.getText().toString());
                        if(length<width){
                            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) mView.findViewById(R.id.diy_coordinatorLayout);
                            Snackbar snackbar =  Snackbar.make(coordinatorLayout , R.string.aquarium_lenght_lower_than_width , Snackbar.LENGTH_LONG);
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.GREEN);
                            snackbar.show();
                            return;
                        }

                        result = CalculatorUtils.getDIY(height,width,length);

                        aquariumVolumeLiter.setText(result.get(ConstantManager.CALCULATOR.AQUARIUM_VOLUME_LITER));
                        mainGlassThickness.setText(result.get(ConstantManager.CALCULATOR.MAIN_GLASS_THICKNESS));
                        buttomGlassThickness.setText(result.get(ConstantManager.CALCULATOR.BUTTOM_GLASS_THICKNESS));
                        glassArea.setText(result.get(ConstantManager.CALCULATOR.GLASS_AREA));
                        glassWeight.setText(result.get(ConstantManager.CALCULATOR.GLASS_WEIGHT));
                        aquariumWeight.setText(result.get(ConstantManager.CALCULATOR.AQUARIUM_WEIGHT));

                    }

            }
        });


        return mView;


    }




}
