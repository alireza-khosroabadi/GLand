package com.khosroabadi.myplantaqua.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.activity.FilterActivity;
import com.khosroabadi.myplantaqua.dataModel.da.filterCache.FilterCacheDataProvider;
import com.khosroabadi.myplantaqua.dataModel.dm.filterCache.FilterCacheBean;
import com.khosroabadi.myplantaqua.dataModel.dm.propertiesItem.PropertiesItemBean;
import com.khosroabadi.myplantaqua.tools.ConstantManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Alireza on 12/8/2016.
 */

public class PropertyItemListAdapter extends BaseAdapter {


    List<PropertiesItemBean> propertiesItemBeanList;
    Context mCOntext;
    static List<Integer> selectedItems;
    FilterCacheDataProvider filterCacheDataProvider ;

    @Inject
    public PropertyItemListAdapter(FilterActivity mCOntext , FilterCacheDataProvider filterCacheDataProvider) {
        this.mCOntext = mCOntext;
        this.propertiesItemBeanList = new ArrayList<PropertiesItemBean>();

        this.filterCacheDataProvider = filterCacheDataProvider;
    }

    public void addAll(List<PropertiesItemBean> propertiesItemBeanList){
        this.propertiesItemBeanList.addAll(propertiesItemBeanList);
    }

    public void clear(){
        this.propertiesItemBeanList.clear();
    }

    @Override
    public int getCount() {
        return propertiesItemBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return propertiesItemBeanList.get(position).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Integer position = i;
        View mView = view;
        final ViewHolder viewHolder;
        if (mView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) mCOntext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = layoutInflater.inflate(R.layout.filter_list_item, null);
           // viewHolder.propItem =(TextView) mView.findViewById(R.id.filter_list_item_name);
            FilterCacheBean filterCacheBean = filterCacheDataProvider.findBySelectedItemId(propertiesItemBeanList.get(position).getId());
            if(filterCacheBean == null) {
                viewHolder.checkedTextView = (CheckedTextView) mView.findViewById(R.id.filter_list_item_name);
                viewHolder.checkedTextView.setCheckMarkDrawable(null);
                viewHolder.checkedTextView.setChecked(false);
            }else {
                viewHolder.checkedTextView = (CheckedTextView) mView.findViewById(R.id.filter_list_item_name);
                viewHolder.checkedTextView.setCheckMarkDrawable(R.drawable.ic_checked);
                viewHolder.checkedTextView.setChecked(true);
            }
            mView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) mView.getTag();
        }
      //  viewHolder.propItem.setText(propertiesItemBeanList.get(i).getValue());
        viewHolder.checkedTextView.setText(propertiesItemBeanList.get(i).getValue());

viewHolder.checkedTextView.setOnClickListener(new View.OnClickListener(){

    @Override
    public void onClick(View view) {
        FilterCacheBean filterCacheBean = filterCacheDataProvider.findBySelectedItemId(propertiesItemBeanList.get(position).getId());
        if(filterCacheBean!= null){
            viewHolder.checkedTextView.setCheckMarkDrawable(null);
            viewHolder.checkedTextView.setChecked(false);
            ConstantManager.filterParams.remove(propertiesItemBeanList.get(position).getId());
            filterCacheDataProvider.delete(propertiesItemBeanList.get(position).getId());
        }else{
            viewHolder.checkedTextView.setCheckMarkDrawable(R.drawable.ic_checked);
            viewHolder.checkedTextView.setChecked(true);
            ConstantManager.filterParams.add(propertiesItemBeanList.get(position).getId());
            filterCacheDataProvider.insert(propertiesItemBeanList.get(position).getId() , propertiesItemBeanList.get(position).getPropertyGroupId());
        }
    }
});
        return mView;

    }
    public static class ViewHolder{

        public TextView propItem;
        public CheckedTextView checkedTextView;

    }

    public List<Integer> getSelectedItems (){
        return ConstantManager.filterParams;
    }
}
