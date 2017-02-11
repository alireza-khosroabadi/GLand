package com.khosroabadi.myplantaqua.dataModel.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by a.khosroabadi on 11/8/2016.
 */

public class AquaPlantDataBase extends SQLiteOpenHelper {



    public AquaPlantDataBase(Context context) {
        super(context, DBConstantManager.DATABASE_NAME.MY_AQYA_DATABASE_NAME , null, DBConstantManager.DATABASE_VERSION.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DBConstantManager.TABLE_SCRIPT.T_FAVORITS);
        sqLiteDatabase.execSQL(DBConstantManager.TABLE_SCRIPT.T_FILTERCACHE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
