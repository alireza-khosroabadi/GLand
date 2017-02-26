package com.khosroabadi.myplantaqua.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.adapters.ProductImageGalleryAdapter;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProductGalleryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String imagePath;
    private List<String> prodructImageList;
    private ProductImageGalleryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_gallery);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        imagePath = getIntent().getStringExtra(ConstantManager.PRODUCT_IMAGE_PATH);
        String[] imagesplited = imagePath.split("/");
        imagePath = imagesplited[0];
        mRecyclerView = (RecyclerView) findViewById(R.id.product_image_gallery_recyclerView);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ProductImageGalleryAdapter(getApplicationContext());
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
        loadProductImage();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
       TextView mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText(getIntent().getStringExtra(ConstantManager.PRODUCT_PRODUCT_NAME));
//mToolbarTitle.setText();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getWindow().setBackgroundDrawable(null);
    }

    ProductImageGalleryAdapter.OnItemClickListener onItemClickListener = new ProductImageGalleryAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(View view, String imageName) {
            Intent intent = new Intent(ProductGalleryActivity.this, FullScreenProductImageActivity.class);
            intent.putExtra(getString(R.string.constant_image_name_extra_param) , imageName);
            startActivity(intent);
        }

    };

    private void loadProductImage(){
        WSUtils wsUtils = new WSUtils(getApplicationContext());
        wsUtils.getProductImage( imagePath , new WSUtils.ProductImageInterface() {
            @Override
            public void onSuccess(List<String> imageList) {
                prodructImageList = imageList;
                if (getApplicationContext() != null) {
                    prodructImageList = imageList;
                    adapter.addAll(prodructImageList);
                    // imageOnClickListener();
                    //viewPagerIndicatorInitialize();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

    }
    @Override
    protected void attachBaseContext(Context context){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
               // this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }
}
