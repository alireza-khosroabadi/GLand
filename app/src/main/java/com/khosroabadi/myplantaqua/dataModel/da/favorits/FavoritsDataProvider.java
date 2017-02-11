package com.khosroabadi.myplantaqua.dataModel.da.favorits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.khosroabadi.myplantaqua.dataModel.dm.favorits.FavoritsBean;
import com.khosroabadi.myplantaqua.dataModel.utils.AquaPlantDataBase;
import com.khosroabadi.myplantaqua.dataModel.utils.DBConstantManager;
import com.khosroabadi.myplantaqua.dataModel.utils.interfaces.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alireza on 12/24/2016.
 */

public class FavoritsDataProvider extends AquaPlantDataBase implements DataProvider<FavoritsBean> {
    public FavoritsDataProvider(Context context) {
        super(context);
    }

    public FavoritsBean findByProductId(Integer product){
        FavoritsBean favoritsBean = new FavoritsBean();
        SQLiteDatabase database = getReadableDatabase();
        String[] params = {product.toString()};
        Cursor cursor= database.rawQuery("select * from " + DBConstantManager.TABLE_NAME.T_FAVORITS + " where " + FavoritsBean.AttrProductId + " = ?", params);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            favoritsBean =cursorToFavorits(cursor);
            cursor.moveToNext();
        }

        return favoritsBean;
    }


    private FavoritsBean cursorToFavorits(Cursor cursor){
        FavoritsBean favoritsBean = new FavoritsBean();
        favoritsBean.setId(cursor.getInt(cursor.getColumnIndex(FavoritsBean.AttrId)));
        favoritsBean.setProductId(cursor.getInt(cursor.getColumnIndex(FavoritsBean.AttrProductId)));

        return favoritsBean;
    }

    @Override
    public List<FavoritsBean> getFullList() {
        return null;
    }

    @Override
    public FavoritsBean getObject() {
        return null;
    }

    @Override
    public ArrayList<FavoritsBean> search(Map params) {
        return null;
    }

    @Override
    public Long insert(Integer id) {
        SQLiteDatabase database = getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoritsBean.AttrProductId , id);
        return  database.insert(DBConstantManager.TABLE_NAME.T_FAVORITS , null , contentValues);

    }

    @Override
    public FavoritsBean update(FavoritsBean favoritsBean) {
        return null;
    }


    @Override
    public boolean delete(Integer productId) {
       try {
           SQLiteDatabase database = getReadableDatabase();
           String[] whereArgs = {productId.toString()};
           database.delete(DBConstantManager.TABLE_NAME.T_FAVORITS, " " + FavoritsBean.AttrProductId + " =? ", whereArgs);
           return true;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }
}
