package com.khosroabadi.myplantaqua.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.customComponent.AwesomeTextView;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.squareup.picasso.Picasso;

public class FullScreenProductImageActivity extends AppCompatActivity {

    ImageView imageView;
    ScaleGestureDetector scaleGDetector;
    AwesomeTextView close;

    float scale=1f;

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
        imageView.setBackgroundColor(Color.TRANSPARENT);
        close = (AwesomeTextView) findViewById(R.id.full_screen_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getWindow().setBackgroundDrawable(null);
        scaleGDetector=new ScaleGestureDetector(this, new ScaleListener());
    }


    @Override

    public boolean onTouchEvent(MotionEvent ev) {

        scaleGDetector.onTouchEvent(ev);

        return true;

    }



    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener{

        public boolean onScaleBegin(ScaleGestureDetector sgd){



            return true;



        }

        public void onScaleEnd(ScaleGestureDetector sgd){

            /*imageView.setScaleX(1f);

            imageView.setScaleY(1f);*/

        }

        public boolean onScale(ScaleGestureDetector sgd){

            // Multiply scale factor

            scale*= sgd.getScaleFactor();
            scale =  Math.max(1f, Math.min(scale, 4f));
            // Scale or zoom the imageview

            imageView.setScaleX(scale);

            imageView.setScaleY(scale);

            Log.i("Main",String.valueOf(scale));

            return true;

        }

    }
}
