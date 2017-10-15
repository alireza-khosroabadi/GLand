package com.khosroabadi.myplantaqua.di.component;

import com.khosroabadi.myplantaqua.di.module.ApiServiceModule;
import com.khosroabadi.myplantaqua.di.module.PicassoModule;
import com.khosroabadi.myplantaqua.webservice.WsInterface;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by khosroabadi on 10/15/2017.
 */

@Component(modules = {ApiServiceModule.class , PicassoModule.class})
public interface GreenLandApplicationComponent {

    Picasso getPicasso();
    WsInterface getApiService();
}
