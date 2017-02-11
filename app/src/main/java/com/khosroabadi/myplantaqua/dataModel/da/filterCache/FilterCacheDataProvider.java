package com.khosroabadi.myplantaqua.dataModel.da.filterCache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.khosroabadi.myplantaqua.dataModel.dm.filterCache.FilterCacheBean;
import com.khosroabadi.myplantaqua.dataModel.utils.AquaPlantDataBase;
import com.khosroabadi.myplantaqua.dataModel.utils.DBConstantManager;
import com.khosroabadi.myplantaqua.dataModel.utils.interfaces.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alireza on 12/24/2016.
 */

public class FilterCacheDataProvider extends AquaPlantDataBase implements DataProvider<FilterCacheBean> {
    public FilterCacheDataProvider(Context context) {
        super(context);
    }

    public FilterCacheBean findBySelectedItemId(Integer itemId){
        FilterCacheBean filterCacheBean =null;
        SQLiteDatabase database = getReadableDatabase();
        String[] params = {itemId.toString()};
        Cursor cursor= database.rawQuery("select * from " + DBConstantManager.TABLE_NAME.T_FILTERCACHE + " where " + FilterCacheBean.AttrSelectedItemId + " = ?", params);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            filterCacheBean =cursorToFilterCache(cursor);
            cursor.moveToNext();
        }

        return filterCacheBean;
    }

    public Integer groupItemSelectedCount(Integer groupId){
        FilterCacheBean filterCacheBean = new FilterCacheBean();
        SQLiteDatabase database = getReadableDatabase();
        String[] params = {groupId.toString()};
        Cursor cursor= database.rawQuery("select * from " + DBConstantManager.TABLE_NAME.T_FILTERCACHE + " where " + FilterCacheBean.AttrGroupId + " = ?", params);
        cursor.moveToFirst();
        Integer counter = 0;
        while (!cursor.isAfterLast()){
            counter++;
        }

        return counter;
    }


    private FilterCacheBean cursorToFilterCache(Cursor cursor){
        FilterCacheBean filterCacheBean = new FilterCacheBean();
        filterCacheBean.setId(cursor.getInt(cursor.getColumnIndex(FilterCacheBean.AttrId)));
        filterCacheBean.setGroupId(cursor.getInt(cursor.getColumnIndex(FilterCacheBean.AttrGroupId)));
        filterCacheBean.setSelectedItemId(cursor.getInt(cursor.getColumnIndex(FilterCacheBean.AttrSelectedItemId)));

        return filterCacheBean;
    }

    @Override
    public List<FilterCacheBean> getFullList() {
        return null;
    }

    @Override
    public FilterCacheBean getObject() {
        return null;
    }

    @Override
    public ArrayList<FilterCacheBean> search(Map params) {
        return null;
    }

    @Override
    public Long insert(Integer id) {
        return null;
    }


    public Long insert(Integer selectedItem , Integer groupId) {
        SQLiteDatabase database = getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FilterCacheBean.AttrGroupId , groupId);
        contentValues.put(FilterCacheBean.AttrSelectedItemId , selectedItem);
        return  database.insert(DBConstantManager.TABLE_NAME.T_FILTERCACHE , null , contentValues);

    }

    @Override
    public FilterCacheBean update(FilterCacheBean filterCacheBean) {
        return null;
    }


    @Override
    public boolean delete(Integer productId) {
       try {
           SQLiteDatabase database = getReadableDatabase();
           String[] whereArgs = {productId.toString()};
           database.delete(DBConstantManager.TABLE_NAME.T_FILTERCACHE, " " + FilterCacheBean.AttrSelectedItemId + " =? ", whereArgs);
           return true;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }


    public void clearFilterItemTable(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+DBConstantManager.TABLE_NAME.T_FILTERCACHE);
    }
}
