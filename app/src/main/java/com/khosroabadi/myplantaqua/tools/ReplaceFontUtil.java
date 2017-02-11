package com.khosroabadi.myplantaqua.tools;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by a.khosroabadi on 11/1/2016.
 */

public class ReplaceFontUtil {

    public static void replaceDefaultFont(Context context ,String fontReplacedName , String fontNameInAsset){
        Typeface customFontTypeFace = Typeface.createFromAsset(context.getAssets() , fontNameInAsset);
        replaceFont(fontReplacedName , customFontTypeFace);
    }

    private static void replaceFont(String fontReplacedName, Typeface customFontTypeFace) {

        try {
            Field myField = Typeface.class.getDeclaredField(fontReplacedName);
            myField.setAccessible(true);
            myField.set(null , customFontTypeFace);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
