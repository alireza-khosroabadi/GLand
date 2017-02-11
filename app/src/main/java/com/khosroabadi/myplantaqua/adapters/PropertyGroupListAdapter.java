package com.khosroabadi.myplantaqua.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.dataModel.dm.propertiesItem.PropertiesItemBean;
import com.khosroabadi.myplantaqua.dataModel.dm.propertyGroup.PropertyGroupBean;

import java.util.List;

/**
 * Created by Alireza on 12/8/2016.
 */

public class PropertyGroupListAdapter extends BaseAdapter {


    List<PropertyGroupBean> propertyGroupBeanList;
    Context mCOntext;


    public PropertyGroupListAdapter(Context mCOntext , List<PropertyGroupBean> propertyGroupBeanList) {
        this.mCOntext = mCOntext;
this.propertyGroupBeanList = propertyGroupBeanList;
    }


    @Override
    public int getCount() {
        if (propertyGroupBeanList != null) {
            return propertyGroupBeanList.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View mView = view;
        ViewHolder viewHolder;
        if (mView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) mCOntext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = layoutInflater.inflate(R.layout.filter_list_group, null);
            viewHolder.propGroup =(TextView) mView.findViewById(R.id.filter_group_name);
           // viewHolder.propId =(TextView) mView.findViewById(R.id.filter_group_id);
            mView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) mView.getTag();
        }
        viewHolder.propGroup.setText(propertyGroupBeanList.get(i).getValue());
        //viewHolder.propId.setText(propertyGroupBeanList.get(i).getId().toString());


        return mView;

    }
    public static class ViewHolder{

        public TextView propGroup;
        public TextView propId;

    }
}
