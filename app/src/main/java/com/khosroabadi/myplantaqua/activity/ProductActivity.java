package com.khosroabadi.myplantaqua.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.adapters.ProductListAdapter;
import com.khosroabadi.myplantaqua.adapters.dataFactory.PaginationAdapterCallback;
import com.khosroabadi.myplantaqua.dataModel.da.filterCache.FilterCacheDataProvider;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.listener.PaginationScrollListener;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.tools.TransitionHelper;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProductActivity extends BaseActivity implements PaginationAdapterCallback{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout errorLayout;
    private ProductListAdapter mAdapter;
    private Button btnRetry;
    private TextView errorText;
    private TextView  mToolbarTitle;
    private String productCategory;
    private String filterParams;
    private ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES ;
    private int currentPage = PAGE_START;
    private Boolean isFabShowing = true;
    private FloatingActionButton fab;
    private String orderBy =ConstantManager.ORDERBY.NAME;
    private Integer orderDirection = ConstantManager.ORDERBY.ORDER_DIR_ASC;
    private String searchParam;
    private Integer selectedPRoductId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_product);
        productCategory = getIntent().getExtras().getString(ConstantManager.CATEGORY_NAME);
        if(getIntent().getExtras().get(ConstantManager.ORDER_DIRECTION) != null){
            orderBy = getIntent().getExtras().getString(ConstantManager.ORDER_BY);
            orderDirection = getIntent().getExtras().getInt(ConstantManager.ORDER_DIRECTION);
        }
        initInstancesDrawer();
        initializeRecyclerView();
        FilterCacheDataProvider filterCacheDataProvider = new FilterCacheDataProvider(getApplicationContext());
        filterCacheDataProvider.clearFilterItemTable();
        setupVindowAnimation();
        //  loacFirstPage(productCategory);
    }

    private void initInstancesDrawer() {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = getLayoutInflater().inflate(R.layout.activity_product, null , false);
        drawerLayout.addView(contentView,0);
        mToolbar = (Toolbar) findViewById(R.id.plant_activity_toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.public_toolbar_title);
        setTitle(productCategory);
//mToolbarTitle.setText();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        fabInitialize();

        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        errorText = (TextView) findViewById(R.id.error_txt_cause);
       // drawerLayout = (DrawerLayout) findViewById(R.id.drawer_plant_layout);
       // navigationView.setNavigationItemSelectedListener(this);
    }

    private void fabInitialize() {
        fab = (FloatingActionButton) findViewById(R.id.scrollupFab);
        hideFab();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
                hideFab();
            }
        });
    }

    private  void initializeRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.plant_list);
        boolean isPhone = getResources().getBoolean(R.bool.is_phone);
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ProductListAdapter(this);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading() && !isLastPage()){
                    if((visibleItemCount+firstVisibleItemPosition) >= totalItemCount &&firstVisibleItemPosition>=0 && totalItemCount >=getTotalPageCount()){
                        loadMoreItems();
                    }
                }

                if (dy > 0) {
                    hideFab();
                } else if (dy < 0) {
                    showFab();
                }

                if (firstVisibleItemPosition == 0){
                    hideFab();
                }

            }

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1; //Increment page index to load the next one
                loadNextPage(/*productCategory , filterParameter , orderBy*/);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        loadFirstPage(/*productCategory , filterParameter , orderBy*/);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFirstPage();
            }
        });
    }

    ProductListAdapter.OnItemClickListener onItemClickListener = new ProductListAdapter.OnItemClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onItemClick(View v, int position , int selectedAdapterPosition) {
            ImageView plantImageHolder= (ImageView) v.findViewById(R.id.plant_image_holder);
            RelativeLayout plantNameHolder = (RelativeLayout) v.findViewById(R.id.plant_main_holder_transition);

            View navigationBar = findViewById(android.R.id.navigationBarBackground);
           // View statusBar = findViewById(android.R.id.statusBarBackground);

            Pair<View , String> imageHolderPair = Pair.create((View) plantImageHolder , getString(R.string.plant_image_holder_transition));
            Pair<View , String> nameHolderPair = Pair.create((View) plantNameHolder , getString(R.string.plant_name_holder_transition));

           // Pair<View , String> navPair = Pair.create((View) navigationBar , getWindow().STATUS_BAR_BACKGROUND_TRANSITION_NAME);
            //Pair<View, String> statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
           // Pair<View , String> toolbarPar = Pair.create((View) mToolbar , getString(R.string.TransitionActionBar));
            selectedPRoductId = selectedAdapterPosition;
            Intent intent = new Intent(ProductActivity.this , ProductDetailActivity.class);
           intent.putExtra(ConstantManager.PRODUCT_DETAILS_EXTRA_PARAM_ID , position);
           ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ProductActivity.this ,
                    imageHolderPair , nameHolderPair /* ,navPair , statusPair, toolbarPar*/);
            ActivityCompat.startActivity(ProductActivity.this , intent , options.toBundle());
         //   transitionTo(intent);
        }
    };

/*
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
          //  hideDrawer();
        else
            super.onBackPressed();
    }
*/

    @Override
    protected void attachBaseContext(Context context){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_action_toggle) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

/*    public void goToPlantDetail(View view){
        ImageView plantImageHolder= (ImageView) findViewById(R.id.plant_image_holder);
        LinearLayout plantNameHolder = (LinearLayout) findViewById(R.id.plant_name_holder);
        Pair<View , String> imageHolderPair = Pair.create((View) plantImageHolder , getString(R.string.plant_image_holder_transition));
        Pair<View , String> nameHolderPair = Pair.create((View) plantNameHolder , getString(R.string.plant_name_holder_transition));

        Intent intent = new Intent(ProductActivity.this , ProductDetailActivity.class);
        intent.putExtra(ConstantManager.PRODUCT_DETAILS_EXTRA_PARAM_ID , position);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ProductActivity.this , imageHolderPair , nameHolderPair);
        ActivityCompat.startActivity(ProductActivity.this , intent , options.toBundle());
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ConstantManager.FILTER_RESULT_CODE){
            Bundle bundle = data.getExtras();
            String result = bundle.getString("filterParams");
            try {
                Gson gson = new Gson();
                filterParams ="";
                List<Integer>   filterParameter = gson.fromJson(result , new TypeToken<List<Integer>>(){}.getType());
                if (filterParameter!=null && filterParameter.size()>0){
                    StringBuilder stringBuilder = new StringBuilder();
                for (Integer id : filterParameter){
                    stringBuilder.append(id+",");
                }
                    stringBuilder.replace(stringBuilder.lastIndexOf(",") , stringBuilder.lastIndexOf(",")+1 , "");
                    filterParams = stringBuilder.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            initializeRecyclerView();

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
       /* SharedPreferences prefs = getSharedPreferences(ConstantManager.UPDATE_PRODUCT_SP, MODE_PRIVATE);
        boolean productIsUpdate = prefs.getBoolean(ConstantManager.PRODUCT_IS_UPDATE, false);
        if (productIsUpdate) {
            mAdapter.updateProduct(selectedPRoductId);
            SharedPreferences.Editor editor = prefs.edit();
            s
        }*/
           // initializeRecyclerView(productCategory , filterParams , ProductBean.AttrName);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void openFilter(View view){

        Intent openFilterIntent = new Intent(ProductActivity.this , FilterActivity.class);
        openFilterIntent.putExtra(ConstantManager.PRODUCT_FILTER_CATEGROY_EXTRA_PARAM , productCategory);
       /* Bundle bndlAnimation = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext() , R.anim.activity_sliding_in_from_right , R.anim.activity_sliding_out_from_right).toBundle();
        startActivityForResult(openFilterIntent , ConstantManager.FILTER_RESULT_CODE ,bndlAnimation);
       */ transitionTo(openFilterIntent);
    }

    public void orderBy(View view){
        registerForContextMenu(view);
      openContextMenu(view);
    }

    public void showAll(View view){
      //  productCategory = getIntent().getExtras().getString(ConstantManager.CATEGORY_NAME);
        filterParams="";
        searchParam="";
        initializeRecyclerView();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_order_by , menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.order_name:
                orderBy = ConstantManager.ORDERBY.NAME;
                initializeRecyclerView();
                return true;
            case R.id.order_enName:
                orderBy = ConstantManager.ORDERBY.EN_NAME;
                initializeRecyclerView();
                return true;
            case R.id.order_rate:
                orderBy = ConstantManager.ORDERBY.RATE;
                orderDirection = ConstantManager.ORDERBY.ORDER_DIR_DESC;
                initializeRecyclerView();
                return true;
            default:
            return super.onContextItemSelected(item);
        }
    }

    private void setTitle(String category){
        if(productCategory.equals(ConstantManager.CATEGORY.PLANT))
            mToolbarTitle.setText(getString(R.string.plnat_list));
        if(productCategory.equals(ConstantManager.CATEGORY.FRESH_WATER_SHRIMP))
            mToolbarTitle.setText(getString(R.string.shrimp_list));
        if(productCategory.equals(ConstantManager.CATEGORY.FRESH_WHATER_FISH))
            mToolbarTitle.setText(R.string.fish_list);
        if(productCategory.equals(ConstantManager.CATEGORY.SNAIL))
            mToolbarTitle.setText(R.string.snail_list);
    }




    private void loadFirstPage(/*final String category ,final String filterParameter  ,final String orderBy */){
        hideErrorView();
        WSUtils wsUtils = new WSUtils(getApplicationContext());
        List<ProductBean> productBeanList = new ArrayList<>();
            wsUtils.getproductList(productCategory, PAGE_START, filterParams, orderBy, orderDirection, ConstantManager.ORDERBY.rowNumber, searchParam, new WSUtils.productListInterface() {
                @Override
                public void onSuccess(List<ProductBean.Product> productBeanList, Integer totalPage, Integer totalResult, Integer pageNumber) {
                    //setProductBeanList(productBeanList);

                    currentPage = PAGE_START;
                    isLastPage = false;
                    progressBar.setVisibility(View.GONE);
                    mAdapter.addAll(productBeanList);
                    TOTAL_PAGES = totalPage;
                    if ((currentPage <= TOTAL_PAGES) && (ConstantManager.ORDERBY.rowNumber <= totalResult))
                        mAdapter.addLoadingFooter();
                    else isLastPage = true;
                    hideErrorView();
                    if (productBeanList.size() < 1) {
                      /*  TextView textView = (TextView) findViewById(R.id.product_list_text_message);
                        textView.setText("Noting");
                        textView.setVisibility(View.VISIBLE);*/
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    showErrorView(t);
                }
            });
    }


    private void loadNextPage(/*final String category ,String filterParameter  ,final String orderBy*/){
        WSUtils wsUtils = new WSUtils(getApplicationContext());
        List<ProductBean> productBeanList = new ArrayList<>();
        wsUtils.getproductList(productCategory, currentPage ,filterParams , orderBy , orderDirection, ConstantManager.ORDERBY.rowNumber , searchParam, new WSUtils.productListInterface() {
            @Override
            public void onSuccess(List<ProductBean.Product> productBeanList, Integer totalPage, Integer totalResult, Integer pageNumber) {
                // setProductBeanList(productBeanList);

                mAdapter.removeLoadingFooter();
                isLoading = false;
                mAdapter.addAll(productBeanList);
                if (currentPage != TOTAL_PAGES) mAdapter.addLoadingFooter();
                else isLastPage = true;

            }

            @Override
            public void onFailure(Throwable t) {
                mAdapter.showRetry(true , fetchErrorMessage(t));
            }

        });
    }

    //magic happens here
    private void hideFab() {
        if (isFabShowing) {
            isFabShowing = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                final Point point = new Point();
               this.getWindow().getWindowManager().getDefaultDisplay().getSize(point);
                final float translation = fab.getY() - point.y;
                fab.animate().translationYBy(-translation).start();
            } else {
                Animation animation = AnimationUtils.makeOutAnimation(this, true);
                animation.setFillAfter(true);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fab.setClickable(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                fab.startAnimation(animation);
            }
        }
    }

    private void showFab() {
        if (!isFabShowing) {
            isFabShowing = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                fab.animate().translationY(0).start();
            } else {
                Animation animation = AnimationUtils.makeInAnimation(this, false);
                animation.setFillAfter(true);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fab.setClickable(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                fab.startAnimation(animation);
            }
        }
    }


    private  void showErrorView(Throwable t){

        if (errorLayout.getVisibility() == View.GONE){
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            errorText.setText(fetchErrorMessage(t));
        }

    }

    private void hideErrorView() {
        if(errorLayout.getVisibility() == View.VISIBLE){
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.toolbar_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               searchParam = query;
                initializeRecyclerView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private void setupVindowAnimation(){
        Slide slideExit = null;
        Slide slideEnter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slideExit = new Slide();
            slideExit.setSlideEdge(Gravity.LEFT);
            slideExit.setDuration(getResources().getInteger(R.integer.anim_duration_long));
            getWindow().setExitTransition(slideExit);

            slideEnter = new Slide();
            slideEnter.setSlideEdge(Gravity.RIGHT);
            slideEnter.setDuration(getResources().getInteger(R.integer.anim_duration_long));
            getWindow().setReenterTransition(slideExit);



        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unchecked") void transitionTo(Intent i) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivityForResult(i, ConstantManager.FILTER_RESULT_CODE, transitionActivityOptions.toBundle());
    }

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }
}
