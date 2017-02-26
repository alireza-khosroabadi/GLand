package com.khosroabadi.myplantaqua.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.transition.Transition;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.adapters.DetailsViewTransitionAdapter;
import com.khosroabadi.myplantaqua.customComponent.AwesomeTextView;
import com.khosroabadi.myplantaqua.dataModel.da.favorits.FavoritsDataProvider;
import com.khosroabadi.myplantaqua.dataModel.dm.favorits.FavoritsBean;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.dataModel.dm.properties.PropertiesBean;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.tools.ShareDataUtils;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/*import static com.khosroabadi.myplantaqua.R.drawable.plant;
import static com.khosroabadi.myplantaqua.R.id.fab;*/

public class ProductDetailActivity extends BaseActivity {

    private AppCompatImageView plantImage ;
    private TextView plantName , productDescription , errorText;
    private String productEnName,productName,productImageName;
    private LinearLayout errorLayout,mPlantDetailContentLinear;
    private CardView productInfoCard;
    private Toolbar toolbar;
    private Button btnRetry;
    private LinearLayout productDetailsLayout;
    private  final  String IMAGE_URL = ConstantManager.BASE_URL+"plantImage/";
    private AwesomeTextView shareTextView,likeTextView;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initializeView();
        int position = getIntent().getIntExtra(ConstantManager.PRODUCT_DETAILS_EXTRA_PARAM_ID , 0);

        windowTransition();
        getropertiesFromServer(position);
        initCollapsingToolbar();
    }

    private void initializeView() {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toolbar = (Toolbar) findViewById(R.id.product_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        plantImage = (AppCompatImageView) findViewById(R.id.product_detail_image_holder);
        plantName = (TextView) findViewById(R.id.product_name) ;
        productDescription = (TextView) findViewById(R.id.product_description);
        productDetailsLayout = (LinearLayout) findViewById(R.id.product_details);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorText = (TextView) findViewById(R.id.error_txt_cause);
        productInfoCard = (CardView) findViewById(R.id.product_info_card);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);

        initializeTextViewIcons();
        plantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this , ProductGalleryActivity.class);
                intent.putExtra(ConstantManager.PRODUCT_IMAGE_PATH , productImageName);
                intent.putExtra(ConstantManager.PRODUCT_PRODUCT_NAME , productName);
                startActivity(intent);
            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getIntent().getIntExtra(ConstantManager.PRODUCT_DETAILS_EXTRA_PARAM_ID , 0);
                getropertiesFromServer(position);
            }
        });
    }

    private void hideSystemUI(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void initializeTextViewIcons() {
        final Integer productId = getIntent().getIntExtra(ConstantManager.PRODUCT_DETAILS_EXTRA_PARAM_ID , 0);
        shareTextView = (AwesomeTextView) findViewById(R.id.share_product);
        shareTextView.setFont(getApplicationContext());
        final ShareDataUtils shareDataUtils = new ShareDataUtils(this);
        shareTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductBean productBean = new ProductBean();
                ProductBean.Product product = productBean.new Product();
                product.setId(productId);

                shareDataUtils.sendProductContent(product , IMAGE_URL+ productImageName);
            }
        });

        likeTextView = (AwesomeTextView) findViewById(R.id.likeTextView);
        likeTextView.setFont(getApplicationContext());

     /*   likeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritsDataProvider favoritsDataProvider = new FavoritsDataProvider(getApplicationContext());
                FavoritsBean favoritsBean = favoritsDataProvider.findByProductId(productId);
                if (favoritsBean.getId() !=null) {
                    setFavorits(productId, -1);
                } else {
                    setFavorits(productId, 1);
                }
            }
        });*/
    }


    private void setFavorits(final Integer productId , final Integer rateValue ){
        WSUtils wsUtils = new WSUtils(getApplicationContext());
        if (wsUtils.isNetworkAvailable()) {
            wsUtils.raiting(productId , rateValue ,
                    new WSUtils.RatingCallback() {
                        @Override
                        public void onSuccess(Boolean result) {
                            if(result) {
                                FavoritsDataProvider favoritsDataProvider = new FavoritsDataProvider(getApplicationContext());
                                if(rateValue>0){
                                    favoritsDataProvider.insert(productId);
                                    likeTextView.setText(R.string.fa_heart);
                                    likeTextView.setTextColor(getResources().getColor(R.color.md_red_800));

                                }else {
                                    favoritsDataProvider.delete(productId);
                                    likeTextView.setText(R.string.fa_heart);
                                    likeTextView.setTextColor(getResources().getColor(R.color.md_grey_700));
                                }
                            }else {
                                //Toast.makeText(mContext , "Nashod" , Toast.LENGTH_LONG).show();
                            }

                        }
                    });

        }
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/IRANSansWeb.ttf");
        collapsingToolbar.setCollapsedTitleTypeface(tf);
        collapsingToolbar.setExpandedTitleTypeface(tf);
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.md_black_1000));
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.md_white_1000));
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.product_detail_main_appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(productEnName);
                   // collapsingToolbar.setCollapsedTitleTextColor(R.color.md_white_1000);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void windowTransition(){
        if (Build.VERSION.SDK_INT>=21) {
            getWindow().getEnterTransition().addListener(new DetailsViewTransitionAdapter() {

                @Override
                public void onTransitionEnd(Transition transition) {
                    // mFavoritesButton.animate().alpha(1.0f);
                    mPlantDetailContentLinear.animate().alpha(3.0f);
                    getWindow().getEnterTransition().removeListener(this);
                }
            });
        }
    }


    private void getropertiesFromServer(final Integer productId){
        WSUtils wsUtils = new WSUtils(getApplicationContext());
       // List<PropertiesBean> propertiesBeanList = new ArrayList<>();
        if (wsUtils.isNetworkAvailable()) {
            wsUtils.getProductPropertiesList(productId, new WSUtils.PropertiesInterface() {
                @Override
                public void onSuccess(List<PropertiesBean> propertiesBeanList) {
                    // setProductBeanList(productBeanList);
                    if (propertiesBeanList != null && propertiesBeanList.size() > 0){
                        hideErrorView();
                        plantName.setText(propertiesBeanList.get(0).getProductName());
                        productEnName = propertiesBeanList.get(0).getProductEnName();
                        productDescription.setText(propertiesBeanList.get(0).getDescription());
                        productImageName = propertiesBeanList.get(0).getImageName();
                        productName = propertiesBeanList.get(0).getProductName();
                        Glide.with(getApplicationContext())
                            .load(IMAGE_URL+"/"+productImageName)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.img_back)
                            .into(plantImage);
                        plantImage.setBackgroundColor(Color.TRANSPARENT);
                        FavoritsDataProvider favoritsDataProvider = new FavoritsDataProvider(getApplicationContext());
                        Integer productId = getIntent().getIntExtra(ConstantManager.PRODUCT_DETAILS_EXTRA_PARAM_ID , 0);
                        FavoritsBean favoritsBean = favoritsDataProvider.findByProductId(productId);
                        if(favoritsBean.getId() !=null){
                            likeTextView.setText(R.string.fa_heart);
                            likeTextView.setTextColor(getResources().getColor(R.color.md_red_800));
                        }else {
                            likeTextView.setText(R.string.fa_heart);
                            likeTextView.setTextColor(getResources().getColor(R.color.md_grey_700));
                        }
                    for (PropertiesBean propertiesBean : propertiesBeanList)

                {

                    LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View contentView = layoutInflater.inflate(R.layout.product_property_details, null, false);
                    TextView propertyGroup = (TextView) contentView.findViewById(R.id.properties_group);
                    TextView propertyItem = (TextView) contentView.findViewById(R.id.properties_item);

                    Typeface font = Typeface.createFromAsset(getAssets(), "fonts/IRANSansWeb.ttf");

                    propertyGroup.setTypeface(font);
                    propertyItem.setTypeface(font);

                    propertyGroup.setText(propertiesBean.getPropertiesGroup());
                    propertyItem.setText(propertiesBean.getPropertiesItemValue());

                    productDetailsLayout.addView(contentView);

                }
                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    showErrorView(t);
                }
            });
        }else {
            showErrorView(null);
        }
    }


    private  void showErrorView(Throwable t){

        if (errorLayout.getVisibility() == View.GONE){
            errorLayout.setVisibility(View.VISIBLE);
            errorText.setText(fetchErrorMessage(t));
            productInfoCard.setVisibility(View.GONE);
        }

    }

    private void hideErrorView() {
        if(errorLayout.getVisibility() == View.VISIBLE){
            errorLayout.setVisibility(View.GONE);
            productInfoCard.setVisibility(View.VISIBLE);
        }
    }


    private String fetchErrorMessage(Throwable t) {
        String errorMsg = getResources().getString(R.string.unknow_error);
        WSUtils wsUtils = new WSUtils(getApplicationContext());
        if(!wsUtils.isNetworkAvailable()) {
            errorMsg = getResources().getString(R.string.unable_connect_to_internet);
        }
        if(t instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }
        if (t instanceof SocketTimeoutException){
            errorMsg = getResources().getString(R.string.connecting_server_fail);
        }

        return errorMsg;
    }

/*    private List<ProductdetailWrapper> getProductDetails(Integer productId){


     //   PropertiesDataAdapter propertiesDataAdapter = new PropertiesDataAdapter(getApplicationContext());
        List<ProductdetailWrapper> propertiesList = propertiesDataAdapter.findProductProperties(productId);
        return propertiesList;
    }*/

}
