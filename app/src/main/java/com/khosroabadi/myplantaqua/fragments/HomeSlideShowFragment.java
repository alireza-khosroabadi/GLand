package com.khosroabadi.myplantaqua.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.adapters.SlideShowPagerAdapter;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.webservice.WSUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeSlideShowFragment extends Fragment {


    private ViewPager mViewPager;
    private List<ProductBean.Product> productList;
    private  final  String IMAGE_URL = ConstantManager.BASE_URL+"plantImage/";
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;



    public HomeSlideShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_slide_show, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.home_viewpager);
        loadRandomProduct(view);






    return view ;

    }

    private void viewPagerIndicatorInitialize(View view) {
        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);

        indicator.setViewPager(mViewPager);

        final float density = getResources().getDisplayMetrics().density;
        //Set circle indicator radius
        indicator.setRadius(3 * density);

        NUM_PAGES =productList.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    private void loadRandomProduct(final View view){
        WSUtils wsUtils = new WSUtils(getActivity());
        wsUtils.getRandomProduct(new WSUtils.productListInterface() {
            @Override
            public void onSuccess(List<ProductBean.Product> productBeanList, Integer totalPage, Integer totalResult, Integer pageNumber) {
                productList = productBeanList;
                if (getActivity() != null) {
                    SlideShowPagerAdapter slideShowPagerAdapter = new SlideShowPagerAdapter(getActivity().getApplicationContext(), productBeanList);
                    mViewPager.setAdapter(slideShowPagerAdapter);
                    currentPage = 0;
                    viewPagerIndicatorInitialize(view);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

    }


}
