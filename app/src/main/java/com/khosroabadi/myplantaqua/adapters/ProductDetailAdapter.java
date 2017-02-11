package com.khosroabadi.myplantaqua.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.dataModel.dm.properties.ProductdetailWrapper;
import com.khosroabadi.myplantaqua.dataModel.dm.properties.PropertiesBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alireza on 12/6/2016.
 */

public class ProductDetailAdapter extends BaseAdapter {

    public ProductDetailAdapter(Context mCOntext, List<PropertiesBean> propertiesBeanList) {
        this.mCOntext = mCOntext;
        this.propertiesBeanList = propertiesBeanList;
    }

    Context mCOntext;
    List<PropertiesBean> propertiesBeanList;

    @Override
    public int getCount() {
        return propertiesBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View mView =view;
        ViewHolder holder;
        if (view==null) {
            LayoutInflater layoutInflater = (LayoutInflater) mCOntext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = layoutInflater.inflate(R.layout.list_product_detail_group, null);
            holder = new ViewHolder();
            holder.propGroup = (TextView) mView.findViewById(R.id.productPropGroup);
            holder.propItem = (TextView) mView.findViewById(R.id.productPropItem);

            mView.setTag(holder);
        }else {
            holder = (ViewHolder) mView.getTag();
        }

        holder.propGroup.setText(propertiesBeanList.get(i).getPropertiesGroup());
        holder.propItem.setText(propertiesBeanList.get(i).getPropertiesItemValue());
        return mView;
    }


    public static class ViewHolder{

        public TextView propGroup;
        public TextView propItem;

    }
}
