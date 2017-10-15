package com.khosroabadi.myplantaqua.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Alireza on 10/15/2017.
 */

@Scope
@Retention(RetentionPolicy.CLASS)
@GreenLandApplicationScope
public @interface GreenLandApplicationScope {
}
