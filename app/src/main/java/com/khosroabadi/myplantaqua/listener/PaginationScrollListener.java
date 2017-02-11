package com.khosroabadi.myplantaqua.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Alireza on 12/23/2016.
 */

public  abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager linearLayoutManager ;

    public PaginationScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {}

    protected abstract void loadMoreItems();
    public abstract int getTotalPageCount();
    public abstract boolean isLastPage();
    public abstract boolean isLoading();
}
