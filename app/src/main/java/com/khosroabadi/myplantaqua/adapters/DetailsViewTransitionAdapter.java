package com.khosroabadi.myplantaqua.adapters;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;

/**
 * Created by a.khosroabadi on 11/6/2016.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public abstract class DetailsViewTransitionAdapter implements Transition.TransitionListener {
    @Override
    public void onTransitionStart(Transition transition) {

    }

    @Override
    public void onTransitionEnd(Transition transition) {

    }

    @Override
    public void onTransitionCancel(Transition transition) {

    }

    @Override
    public void onTransitionPause(Transition transition) {

    }

    @Override
    public void onTransitionResume(Transition transition) {

    }

    public abstract void onTransitionEnd(android.support.transition.Transition transition);
}
