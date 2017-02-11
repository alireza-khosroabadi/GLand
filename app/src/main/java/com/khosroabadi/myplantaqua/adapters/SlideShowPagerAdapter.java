package com.khosroabadi.myplantaqua.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.tools.ConstantManager;

import java.util.List;

/**
 * Created by Alireza on 1/4/2017.
 */

public class SlideShowPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ProductBean.Product> productList;
    private TextView textView;
    private  final  String IMAGE_URL = ConstantManager.BASE_URL+"plantImage/";

    public SlideShowPagerAdapter(Context context , List<ProductBean.Product> productList) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.home_slide_image_view , container , false);
        AppCompatImageView imageView = (AppCompatImageView) view.findViewById(R.id.sliding_image);
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/IRANSansWeb.ttf");
        textView = (TextView) view.findViewById(R.id.sliding_product_name);
        textView.setText(productList.get(position).getName());
        textView.setTypeface(font);
        Glide.with(mContext)
                .load(IMAGE_URL+ productList.get(position).getImageName())
                .into(imageView);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }



}
