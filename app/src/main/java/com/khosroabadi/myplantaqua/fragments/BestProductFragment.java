package com.khosroabadi.myplantaqua.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.activity.ProductDetailActivity;
import com.khosroabadi.myplantaqua.adapters.dataFactory.BestProductListAdapter;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.listener.PaginationScrollListener;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BestProductFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BestProductListAdapter mAdapter;
    private String productCategory;
    private ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES ;
    private int currentPage = PAGE_START;

    public BestProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_best_product, container, false);
        productCategory = getArguments().getString(ConstantManager.CATEGORY_NAME);
        if (savedInstanceState == null)
         initializeRecyclerView(view);

        return view;
    }



    private  void initializeRecyclerView(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_best_product_list);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new BestProductListAdapter(getActivity());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(onItemClickListener);
        progressBar = (ProgressBar) view.findViewById(R.id.main_progress);
/*        mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

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

            }

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1; //Increment page index to load the next one
                loadNextPage(*//*productCategory , filterParameter , orderBy*//*);
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
        });*/
        loadFirstPage(/*productCategory , filterParameter , orderBy*/);
    }

    BestProductListAdapter.OnItemClickListener onItemClickListener = new BestProductListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            ImageView plantImageHolder= (ImageView) v.findViewById(R.id.best_product_image_holder);
            LinearLayout plantNameHolder = (LinearLayout) v.findViewById(R.id.best_product_main_holder);

            View navigationBar = v.findViewById(android.R.id.navigationBarBackground);
            // View statusBar = findViewById(android.R.id.statusBarBackground);

            Pair<View , String> imageHolderPair = Pair.create((View) plantImageHolder , getString(R.string.plant_image_holder_transition));
            Pair<View , String> nameHolderPair = Pair.create((View) plantNameHolder , getString(R.string.plant_name_holder_transition));

            Pair<View , String> navPair = Pair.create((View) navigationBar , getActivity().getWindow().STATUS_BAR_BACKGROUND_TRANSITION_NAME);
            //Pair<View, String> statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
          //  Pair<View , String> toolbarPar = Pair.create((View) mToolbar , getString(R.string.TransitionActionBar));

            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra(ConstantManager.PRODUCT_DETAILS_EXTRA_PARAM_ID , position);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity() ,
                    imageHolderPair , nameHolderPair /* ,navPair , statusPair, toolbarPar*/);
            ActivityCompat.startActivity(getActivity() , intent , options.toBundle());
        }
    };

    private void loadFirstPage(/*final String category ,final String filterParameter  ,final String orderBy */){
        WSUtils wsUtils = new WSUtils(getActivity());
        List<ProductBean> productBeanList = new ArrayList<>();
        wsUtils.getproductList(productCategory, PAGE_START ,null, ConstantManager.ORDERBY.RATE , ConstantManager.ORDERBY.ORDER_DIR_DESC , ConstantManager.ORDERBY.rowNumber ,null, new WSUtils.productListInterface() {
            @Override
            public void onSuccess(List<ProductBean.Product> productBeanList, Integer totalPage, Integer totalResult, Integer pageNumber) {
                // setProductBeanList(productBeanList);
                currentPage = PAGE_START;
                isLastPage = false;
                progressBar.setVisibility(View.GONE);
                mAdapter.addAll(productBeanList);
                TOTAL_PAGES = totalPage;
               // if ((currentPage <= TOTAL_PAGES) && (ConstantManager.ORDERBY.rowNumber <= totalResult) ) mAdapter.addLoadingFooter();
                isLastPage = true;
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

/*    private void loadNextPage(*//*final String category ,String filterParameter  ,final String orderBy*//*){
        WSUtils wsUtils = new WSUtils(getActivity());
        List<ProductBean> productBeanList = new ArrayList<>();
        wsUtils.getproductList(productCategory, currentPage ,null , ConstantManager.ORDERBY.RATE , ConstantManager.ORDERBY.ORDER_DIR_DESC , ConstantManager.ORDERBY.rowNumber , null, new WSUtils.productListInterface() {
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
            public void onFailure(Throwable throwable) {

            }
        });
    }*/
}
