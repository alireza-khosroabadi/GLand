package com.khosroabadi.myplantaqua.di.module;

import com.khosroabadi.myplantaqua.activity.ProductActivity;
import com.khosroabadi.myplantaqua.adapters.ProductListAdapter;
import com.khosroabadi.myplantaqua.di.scope.ProductActivityScope;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

/**
 * Created by khosroabadi on 10/16/2017.
 */

@Module
public class ProductActivityModule {

    private ProductActivity productActivity;

    public ProductActivityModule(ProductActivity productActivity) {
        this.productActivity = productActivity;
    }

    @Provides
    @ProductActivityScope
    public ProductActivity productActivity(){
        return productActivity;
    }

}
