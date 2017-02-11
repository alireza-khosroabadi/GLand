package com.khosroabadi.myplantaqua.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.dataModel.dm.deviceInfo.DeviceInfoBean;
import com.khosroabadi.myplantaqua.fragments.BestProductFragment;
import com.khosroabadi.myplantaqua.fragments.HomeSlideShowFragment;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.tools.DeviceInfoUtil;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

public class HomeActivity extends BaseActivity {

    private Toolbar mToolbar;
/*    private DrawerLayout drawerLayout;
    private NavigationView navigationView;*/
    private String response;
    private Boolean connectionAvailable= Boolean.TRUE;
    private ImageView morePlantImageView;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(HomeActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
//sendDeviceInfo();
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = layoutInflater.inflate(R.layout.activity_home, null, false);
        drawerLayout.addView(contentView, 0);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        initializeView();
       // List<LookupBean> lookupList = new ArrayList<>();



    }

    private void initializeView() {
        WSUtils wsUtils = new WSUtils(getApplicationContext());
        if(wsUtils.isNetworkAvailable()) {
            initializeSlideShow();
            initializePlantList();
            initializeFishList();
            initializeShrimpList();
        }else {
            /*LinearLayout connectionFaildHolder = (LinearLayout) findViewById(R.id.connection_faild_holder);
            connectionFaildHolder.setVisibility(View.VISIBLE);*/
            connectionAvailable = Boolean.FALSE;
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.home_activity_coordinatorLayout);
            Snackbar snackbar =  Snackbar.make(coordinatorLayout, R.string.unable_connect_to_internet , Snackbar.LENGTH_INDEFINITE);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.setActionTextColor(Color.GREEN);
            snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initializeView();
                }
            });
            snackbar.show();


        }
    }


    private void initializeSlideShow() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.home_slide_show_frame);
        frameLayout.setVisibility(View.VISIBLE);
        HomeSlideShowFragment homeSlideShowFragment = new HomeSlideShowFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.home_slide_show_frame , homeSlideShowFragment).commit();
    }

    private void initializePlantList() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.best_plant_holder);
        linearLayout.setVisibility(View.VISIBLE);
        morePlantImageView = (ImageView) findViewById(R.id.more_plant);
        morePlantImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(HomeActivity.this , ProductActivity.class);
                intent.putExtra(ConstantManager.CATEGORY_NAME, ConstantManager.CATEGORY.PLANT);
                intent.putExtra(ConstantManager.ORDER_DIRECTION , ConstantManager.ORDERBY.ORDER_DIR_DESC);
                intent.putExtra(ConstantManager.ORDER_BY , ConstantManager.ORDERBY.RATE);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString(ConstantManager.CATEGORY_NAME, ConstantManager.CATEGORY.PLANT);
        BestProductFragment bestProductFragment = new BestProductFragment();
        bestProductFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.best_plant_list , bestProductFragment).commit();
    }

    private void initializeFishList() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.best_fish_holder);
        linearLayout.setVisibility(View.VISIBLE);
        morePlantImageView = (ImageView) findViewById(R.id.more_fish);
        morePlantImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(HomeActivity.this , ProductActivity.class);
                intent.putExtra(ConstantManager.CATEGORY_NAME, ConstantManager.CATEGORY.FRESH_WHATER_FISH);
                intent.putExtra(ConstantManager.ORDER_DIRECTION , ConstantManager.ORDERBY.ORDER_DIR_DESC);
                intent.putExtra(ConstantManager.ORDER_BY , ConstantManager.ORDERBY.RATE);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString(ConstantManager.CATEGORY_NAME, ConstantManager.CATEGORY.FRESH_WHATER_FISH);
        BestProductFragment bestProductFragment = new BestProductFragment();
        bestProductFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.best_fish_list , bestProductFragment).commit();
    }


    private void initializeShrimpList() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.best_shrimp_holder);
        linearLayout.setVisibility(View.VISIBLE);
        morePlantImageView = (ImageView) findViewById(R.id.more_shrimp);
        morePlantImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(HomeActivity.this , ProductActivity.class);
                intent.putExtra(ConstantManager.CATEGORY_NAME, ConstantManager.CATEGORY.FRESH_WATER_SHRIMP);
                intent.putExtra(ConstantManager.ORDER_DIRECTION , ConstantManager.ORDERBY.ORDER_DIR_DESC);
                intent.putExtra(ConstantManager.ORDER_BY , ConstantManager.ORDERBY.RATE);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString(ConstantManager.CATEGORY_NAME, ConstantManager.CATEGORY.FRESH_WATER_SHRIMP);
        BestProductFragment bestProductFragment = new BestProductFragment();
        bestProductFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.best_shrimp_list , bestProductFragment).commit();
    }

    @Override
    protected void onResume() {

        super.onResume();

/*        SharedPreferences topic = getSharedPreferences(ConstantManager.TOPIC, Context.MODE_PRIVATE);
        if(!topic.getBoolean(ConstantManager.TOPIC , false)){
            registerDevice();
        }*/
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(HomeActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


 /*   private void  registerDevice(){
        WSUtils wsUtils = new WSUtils(getApplicationContext());
        wsUtils.sendDeviceInfo(DeviceInfoUtil.getDeviceInfo(HomeActivity.this), new WSUtils.RatingCallback() {
            @Override
            public void onSuccess(Boolean result) {
                SharedPreferences topic = getSharedPreferences(ConstantManager.TOPIC, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = topic.edit();
                editor.putBoolean(ConstantManager.TOPIC , true);
                editor.commit();
            }
        });
    }*/

/*    public void openFilter(View view){


        Intent openFilterIntent = new Intent(HomeActivity.this , FilterActivity.class);
        Bundle bndlAnimation = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext() , R.anim.activity_sliding_in_from_right , R.anim.activity_sliding_out_from_right).toBundle();
        startActivityForResult(openFilterIntent ,0 ,bndlAnimation)ک
    }*/
}
