package com.khosroabadi.myplantaqua.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.squareup.picasso.Picasso;

public class FullScreenProductImageActivity extends AppCompatActivity {

    ImageView imageView;
    private  final  String IMAGE_URL = ConstantManager.BASE_URL+"plantImage/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_product_image);
        String imageName = getIntent().getExtras().getString(getString(R.string.constant_image_name_extra_param));
        imageView = (ImageView) findViewById(R.id.full_screen_image_view);
        Picasso.with(this)
                .load(IMAGE_URL+imageName)
                .into(imageView);

    }
}
