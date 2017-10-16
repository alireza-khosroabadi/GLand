package com.khosroabadi.myplantaqua.di.component;

import com.khosroabadi.myplantaqua.activity.ProductDetailActivity;
import com.khosroabadi.myplantaqua.di.module.ProductDetailsActivityModule;
import com.khosroabadi.myplantaqua.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by khosroabadi on 10/16/2017.
 */
@ActivityScope
@Component(modules = ProductDetailsActivityModule.class , dependencies = GreenLandApplicationComponent.class)
public interface ProductDetailsActivityComponent {

    void injectProductDetailsActivity(ProductDetailActivity productDetailActivity);

}
