package com.khosroabadi.myplantaqua.di.component;

import com.khosroabadi.myplantaqua.activity.ProductActivity;
import com.khosroabadi.myplantaqua.di.module.ProductActivityModule;
import com.khosroabadi.myplantaqua.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by khosroabadi on 10/16/2017.
 */

@ActivityScope
@Component(modules = ProductActivityModule.class , dependencies = GreenLandApplicationComponent.class)
public interface ProductActivityComponent {

    void injectProductActivity(ProductActivity productActivity);
}
