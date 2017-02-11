package com.khosroabadi.myplantaqua.adapters.dataFactory;

import android.content.Context;

import java.util.List;

/**
 * Created by Alireza on 12/4/2016.
 */

public interface AdapterDataFactoryInterFace<V> {

    public List<V> getProductList(ProductCategoryEnum category , Context context);
}
