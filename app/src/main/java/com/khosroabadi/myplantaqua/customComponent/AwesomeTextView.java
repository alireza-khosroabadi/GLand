package com.khosroabadi.myplantaqua.customComponent;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by Alireza on 11/23/2016.
 */

public class AwesomeTextView extends TextView {


    public AwesomeTextView(Context context) {
        super(context);
    }

    public AwesomeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AwesomeTextView(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        String fontPath = "fonts/fontawesome-webfont.ttf";
        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), fontPath);
        this.setTypeface(typeFace);
    }

   public void setFont(Context context){
       Typeface typeface = Typeface.createFromAsset(context.getAssets() , "fonts/fontawesome-webfont.ttf");
       setTypeface(typeface );
   }


}
