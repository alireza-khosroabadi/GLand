package com.khosroabadi.myplantaqua.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.adapters.PropertyGroupListAdapter;
import com.khosroabadi.myplantaqua.adapters.PropertyItemListAdapter;
import com.khosroabadi.myplantaqua.dataModel.da.filterCache.FilterCacheDataProvider;
import com.khosroabadi.myplantaqua.dataModel.dm.propertiesItem.PropertiesItemBean;
import com.khosroabadi.myplantaqua.dataModel.dm.propertyGroup.PropertyGroupBean;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FilterActivity extends BaseActivity {

   // private Toolbar mToolbar ;
   // TextView mToolbarTitle;
   private List<PropertyGroupBean> propertyGroupBeanList;
    private List<PropertiesItemBean> propertiesItemBeanList;
   private ListView itemListView;
   private ListView groupListView;
   private List<Integer> filterParams ;
   private LinearLayout btnFilter;
    private Boolean filterPressed = Boolean.FALSE;
   private final List<String> listItem= new ArrayList<>();
    private PropertyGroupListAdapter propertyGroupListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        btnFilter = (LinearLayout) findViewById(R.id.btn_filter);
        setupVindowAnimation();
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Gson gson = new Gson();
                intent.putExtra("filterParams", gson.toJson(filterParams));
                setResult(ConstantManager.FILTER_RESULT_CODE, intent);
                ConstantManager.filterParams.clear();
                filterPressed=Boolean.TRUE;
                onBackPressed();
               // overridePendingTransition(R.anim.activity_sliding_out_from_left, R.anim.activity_sliding_in_from_left);

            }
        });
        String propertyGroupCategory = getIntent().getExtras().getString(ConstantManager.PRODUCT_FILTER_CATEGROY_EXTRA_PARAM);
        getPropertyGroupFromServer(propertyGroupCategory);
        groupListView = (ListView) findViewById(R.id.filter_group_list);

        final List<String> listGroup= new ArrayList<>();

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

    }

    @Override
    protected void attachBaseContext(Context context){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    private void setItemListView(final Integer propertyGroupId){
        WSUtils wsUtils = new WSUtils(getApplicationContext());
        List<PropertiesItemBean> propertyItemServiceModelList = new ArrayList<>();
        if (wsUtils.isNetworkAvailable()) {
            wsUtils.getPropertyItemList(propertyGroupId, new WSUtils.propertyItemInterfce() {
                @Override
                public void onSuccess(List<PropertiesItemBean> propertyItemList) {

                    final PropertyItemListAdapter propertyItemListAdapter = new PropertyItemListAdapter(getApplicationContext(), propertyItemList);
                    itemListView = (ListView) findViewById(R.id.filter_item_list);
                    itemListView.setAdapter(propertyItemListAdapter);
                    itemListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    filterParams = propertyItemListAdapter.getSelectedItems();

                }
            });
        }

    }


    private void getPropertyGroupFromServer(final String category){
        WSUtils wsUtils = new WSUtils(getApplicationContext());
        List<PropertyGroupBean> productBeanList = new ArrayList<>();
        if (wsUtils.isNetworkAvailable()) {
            wsUtils.getPropertyGroupList(category, new WSUtils.propertyGroupInterfce() {
                @Override
                public void onSuccess(List<PropertyGroupBean> propertyGroupList) {

                    propertyGroupListAdapter = new PropertyGroupListAdapter(getApplicationContext(), propertyGroupList);
                    groupListView.setAdapter(propertyGroupListAdapter);
                    setPropertyGroupBeanList(propertyGroupList);
                    if (propertyGroupBeanList != null && propertyGroupList.size() >0)
                        setItemListView(propertyGroupBeanList.get(0).getId());
                    groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            setItemListView(propertyGroupBeanList.get(position).getId());
                        }
                    });
                }
            });
        }
        else {

        }
    }


    public void setPropertyGroupBeanList(List<PropertyGroupBean> propertyGroupBeanList)
    {
        this.propertyGroupBeanList = propertyGroupBeanList;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!filterPressed) {
            FilterCacheDataProvider filterCacheDataProvider = new FilterCacheDataProvider(getApplicationContext());
            filterCacheDataProvider.clearFilterItemTable();
        }
    }


    private void setupVindowAnimation(){
        Slide slide = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
            slide.setSlideEdge(Gravity.RIGHT);
            slide.setDuration(getResources().getInteger(R.integer.anim_duration_long));
           // getWindow().setReenterTransition(slide);
            getWindow().setExitTransition(slide);

        }


    }
}
