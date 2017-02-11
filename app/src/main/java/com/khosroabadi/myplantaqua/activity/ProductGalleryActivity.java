package com.khosroabadi.myplantaqua.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.adapters.ProductImageGalleryAdapter;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

import java.util.List;

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
    }

    ProductImageGalleryAdapter.OnItemClickListener onItemClickListener = new ProductImageGalleryAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(View view, String imageName) {
            Intent intent = new Intent(ProductGalleryActivity.this, FullScreenProductImageActivity.class);

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

}
