package com.khosroabadi.myplantaqua.di.component;

import com.khosroabadi.myplantaqua.activity.ProductActivity;
import com.khosroabadi.myplantaqua.adapters.ProductListAdapter;
import com.khosroabadi.myplantaqua.di.module.ProductActivityModule;
import com.khosroabadi.myplantaqua.di.scope.ProductActivityScope;
import com.khosroabadi.myplantaqua.webservice.WsInterface;

import dagger.Component;

/**
 * Created by khosroabadi on 10/16/2017.
 */

@ProductActivityScope
@Component(modules = ProductActivityModule.class , dependencies = GreenLandApplicationComponent.class)
public interface ProductActivityComponent {

    void injectProductActivity(ProductActivity productActivity);
}
