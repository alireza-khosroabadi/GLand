package com.khosroabadi.myplantaqua.di.module;

import com.khosroabadi.myplantaqua.di.scope.FragmentScope;
import com.khosroabadi.myplantaqua.fragments.BestProductFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alireza on 10/16/2017.
 */

@Module
public class BestProductFragmentModule {

    private BestProductFragment bestProductFragment;

    public BestProductFragmentModule(BestProductFragment bestProductFragment) {
        this.bestProductFragment = bestProductFragment;
    }


    @FragmentScope
    @Provides
    public BestProductFragment provideBestProductFragment(){
        return this.bestProductFragment;
    }
}
