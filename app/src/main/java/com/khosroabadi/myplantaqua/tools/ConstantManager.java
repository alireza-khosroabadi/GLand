package com.khosroabadi.myplantaqua.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.khosroabadi on 11/8/2016.
 */

public class ConstantManager {

    public static final int SPLASH_TIME = 1 * 1000;
    public static final Integer FILTER_RESULT_CODE=1;
    //public static final String BASE_URL="http://192.168.1.102:8080/MyAquaPlant/";
    public static final String BASE_URL="http://91.98.30.138:8080/MyAquaPlant/";
    public static final String CATEGORY_NAME="category_name";
    public static final String ORDER_BY = "orderBy";
    public static final String ORDER_DIRECTION = "order_direction";
    public static final String PRODUCT_DETAILS_EXTRA_PARAM_ID = "product_id";
    public static final String PRODUCT_FILTER_CATEGROY_EXTRA_PARAM = "product_filter_category";
    public static final String TOPIC = "Topic";
    public static final String TOPIC_NAME="PlanetAquaCustomer";
    public static final String UPDATE_PRODUCT_SP="update_product";
    public static final String PRODUCT_IS_UPDATE="product_is_update";
    public static final String PRODUCT_IMAGE_PATH="product_image_path";



    public static List<Integer> filterParams = new ArrayList<>();

    public static class ACRA{
        public static final String ACRA_URL="http://91.98.30.138:5984/acra-aquaplantapp/_design/acra-storage/_update/report";
        public static final String ACRA_USER="plantaquauser";
        public static final String ACRA_PASS="A870948768e";
    }

    public static class CATEGORY{
        public static final String CATEGORY="Category";
        public static final String PLANT = "Plant";
        public static final String SHRIMP = "Shrimp";
        public static final String FRESH_WATER_SHRIMP = "FreshWaterShrimp";
        public static final String SALT_WATER_SHRIMP = "SaltWaterShrimp";
        public static final String FISH = "Fish";
        public static final String FRESH_WHATER_FISH = "FreshwaterFish";
        public static final String SALT_WHATER_FISH = "SaltwaterFish";
        public static final String SNAIL = "Snail";
    }

    public  static  class ORDERBY{
        public static final String NAME = "FaTitle";
        public static final String EN_NAME = "EnTitle";
        public static final String RATE = "Rate";
        public static final Integer ORDER_DIR_ASC=0;
        public static final Integer ORDER_DIR_DESC=1;
        public static final Integer rowNumber =25;
    }


    public  class CALCULATOR{
        public static final String LENGTH = "length";
        public static final String WIDTH = "width";
        public static final String HEIGHT = "height";
        public static final String AQUARIUM_VOLUME_LITER = "aquarium_volume_liter";
        public static final String MAIN_GLASS_THICKNESS = "main_glass_thickness";
        public static final String BUTTOM_GLASS_THICKNESS = "buttom_glass_thickness";
        public static final String GLASS_AREA = "glass_area";
        public static final String GLASS_WEIGHT = "glass_weight";
        public static final String AQUARIUM_WEIGHT = "aquarium_weight";
    }
}
