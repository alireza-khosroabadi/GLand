package com.khosroabadi.myplantaqua.dataModel.utils.interfaces;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by a.khosroabadi on 11/8/2016.
 */

public interface DataProvider<T> {

    public List<T> getFullList();
    public T getObject();
    public ArrayList<T> search(Map params);
    public Long insert(Integer id);
    public T update (T t);
    public boolean delete(Integer id);
}
