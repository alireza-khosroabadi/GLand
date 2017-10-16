package com.khosroabadi.myplantaqua.di.component;

import com.khosroabadi.myplantaqua.di.module.BestProductFragmentModule;
import com.khosroabadi.myplantaqua.di.scope.FragmentScope;
import com.khosroabadi.myplantaqua.fragments.BestProductFragment;

import dagger.Component;

/**
 * Created by Alireza on 10/16/2017.
 */
@FragmentScope
@Component(modules = BestProductFragmentModule.class ,dependencies = GreenLandApplicationComponent.class)
public interface BestFragmentComponent {

    void injectBestProductFragment(BestProductFragment bestProductFragment);
}
