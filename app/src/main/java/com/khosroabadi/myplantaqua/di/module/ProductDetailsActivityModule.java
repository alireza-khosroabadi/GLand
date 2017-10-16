package com.khosroabadi.myplantaqua.di.module;

import com.khosroabadi.myplantaqua.activity.ProductDetailActivity;
import com.khosroabadi.myplantaqua.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by khosroabadi on 10/16/2017.
 */

@Module
public class ProductDetailsActivityModule {

    private ProductDetailActivity productDetailActivity;

    public ProductDetailsActivityModule(ProductDetailActivity productDetailActivity) {
        this.productDetailActivity = productDetailActivity;
    }

    @Provides
    @ActivityScope
    public ProductDetailActivity productDetailActivity(){
        return productDetailActivity;
    }

}
