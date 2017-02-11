package com.khosroabadi.myplantaqua.dataModel.utils;

import com.khosroabadi.myplantaqua.dataModel.dm.favorits.FavoritsBean;
import com.khosroabadi.myplantaqua.dataModel.dm.filterCache.FilterCacheBean;

/**
 * Created by Alireza on 12/2/2016.
 */

public class DBConstantManager {

    public static class DATABASE_VERSION{
        public static final Integer DB_VERSION = 1;
    }

    public static class DATABASE_NAME{
        public static final String MY_AQYA_DATABASE_NAME = "aquaDB.db";
    }

    public static class TABLE_NAME{
/*        public static final String T_PROPERTIES ="T_PROPERTIES";
        public static final String T_CATEGORY = "T_CATEGORY";
        public static final String T_PRODUCTSIMAGE = "T_PRODUCTSIMAGE";
        public static final String T_PRODUCTS = "T_PRODUCTS";
        public static final String T_PROPERTIESITEM = "T_PROPERTIESITEM" ;
        public static final String T_PROPERTYGROUP = "T_PROPERTYGROUP";*/
        public static final String T_FAVORITS = "T_FAVORITS";
        public static final String T_FILTERCACHE = "T_FILTERCASHE";
    }


    public static class TABLE_SCRIPT {

        public static final String T_FAVORITS = "CREATE TABLE ["+TABLE_NAME.T_FAVORITS+"] (" +
                "[" + FavoritsBean.AttrId +"] INTEGER PRIMARY KEY AUTOINCREMENT," +
                "[" + FavoritsBean.AttrProductId + "] INTEGER)" ;

        public static final String T_FILTERCACHE = "CREATE TABLE ["+TABLE_NAME.T_FILTERCACHE+"] (" +
                "[" + FilterCacheBean.AttrId +"] INTEGER PRIMARY KEY AUTOINCREMENT," +
                "[" + FilterCacheBean.AttrSelectedItemId + "] INTEGER," +
                "[" + FilterCacheBean.AttrGroupId + "] INTEGER)" ;


   /*     public static final String T_PROPERTIES = "CREATE TABLE [T_PROPERTIES] (" +
                "[id] INTEGER PRIMARY KEY," +
                "[categoryId] INTEGER," +
                "[ownerId] INTEGER," +
                "[propertiesItemId] INTEGER," +
                "[description] TEXT (200))";

        public static final String T_CATEGORY = "CREATE TABLE [T_CATEGORY] (" +
                "[id] INTEGER PRIMARY KEY," +
                "[category] TEXT (30))";

        public static final String T_PRODUCTSIMAGE = "CREATE TABLE [T_PRODUCTSIMAGE] (" +
                "[id] INTEGER PRIMARY KEY," +
                "[ownerId] INTEGER," +
                "[categoryId] INTEGER," +
                "[name] TEXT (50))";

        public static final String T_PRODUCTS = "CREATE TABLE [T_PRODUCTS] (" +
                "[id] INTEGER PRIMARY KEY," +
                "[categoryId] INTEGER," +
                "[name] TEXT (50)," +
                "[enName] TEXT (50)," +
                "[rate] INTEGER," +
                "[favorites] INTEGER DEFAULT 0,"+
                "[description] TEXT (200))";

        public static final String T_PROPERTIESITEM = "CREATE TABLE [T_PROPERTIESITEM] (" +
                "[id] INTEGER PRIMARY KEY," +
                "[propertyGroupId] INTEGER," +
                "[value] TEXT (50))";

        public static final String T_PROPERTYGROUP = "CREATE TABLE [T_PROPERTYGROUP] (" +
                "[id] INTEGER PRIMARY KEY," +
                "[category] TEXT (50)," +
                "[type] TEXT (50)," +
                "[value] TEXT (50))";*/

    }

}
