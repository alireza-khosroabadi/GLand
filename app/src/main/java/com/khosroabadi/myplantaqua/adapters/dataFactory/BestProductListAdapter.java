package com.khosroabadi.myplantaqua.adapters.dataFactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khosroabadi.myplantaqua.BuildConfig;
import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.fragments.BestProductFragment;
import com.khosroabadi.myplantaqua.tools.ConstantManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by a.khosroabadi on 10/31/2016.
 */

public class BestProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private  final  String IMAGE_URL = ConstantManager.BASE_URL+"plantImage/";
    private final List<ProductBean.Product> productBeanList;
    private boolean isLoadingAdded = false;
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    @Inject
    public BestProductListAdapter(BestProductFragment mContext) {
        this.mContext = mContext.getContext();
        //    this.productBeanList=productBeanList;
        this.productBeanList = new ArrayList<>();

    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View view = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(view);
                break;
        }
        return viewHolder;
           // view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_holder, parent, false);

      //  return new ViewHolder(view);

    }



    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        BestProductListAdapter.ProductVH viewHolder;
        View v1 = inflater.inflate(R.layout.best_product_list_holder, parent, false);
        viewHolder = new ProductVH(v1);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductBean.Product product = this.productBeanList.get(position);
        switch (getItemViewType(position)) {
            case ITEM: {
                final ProductVH pHolder = (ProductVH) holder;
                pHolder.plantName.setText(product.getName());
                if(product.getRate()!=null && !product.getRate().equals(""))
                pHolder.rate.setText(product.getRate().toString());
                if(product.getId()!=null && !product.getId().equals(""))
                pHolder.plantId.setText(product.getId().toString());


                Glide.with(mContext)
                        .load(IMAGE_URL + product.getImageName())
                        .into(pHolder.plantImage);

            }
            break;
            case LOADING:
                break;
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
            ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(mContext,R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

        }
    }

    @Override
    public int getItemCount() {
       return  (productBeanList == null || productBeanList.size()<=0) ? 0 : productBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        return (position ==productBeanList.size()-1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public class ProductVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        public LinearLayout plantListHolder;
        public LinearLayout plantNameHolder;
        public TextView plantName;
        public TextView plantId;
        public TextView rate;
        public ImageView plantImage;
        public CardView cardView;

        public ProductVH(View view){
            super(view);
            plantListHolder = (LinearLayout) view.findViewById(R.id.best_product_main_holder);
            plantNameHolder = (LinearLayout) view.findViewById(R.id.best_product_name_holder);
            plantName = (TextView) view.findViewById(R.id.best_product_name);
            plantImage = (ImageView) view.findViewById(R.id.best_product_image_holder);
            cardView = (CardView) view.findViewById(R.id.best_product_row_card);
            rate = (TextView) view.findViewById(R.id.best_product_rate);
            plantId = (TextView) view.findViewById(R.id.item_selected_id);
            plantNameHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (onItemClickListener!=null){
                onItemClickListener.onItemClick(view , Integer.parseInt(plantId.getText().toString()));
            }
        }


    }


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener){
        this.onItemClickListener = mOnItemClickListener;
    }


    public void add(ProductBean.Product productBean){
        productBeanList.add(productBean);
        notifyItemInserted(productBeanList.size() - 1);
    }

    public void addAll(List<ProductBean.Product> products){
        if(products != null && products.size()>0) {
            for (ProductBean.Product productBean : products) {
                add(productBean);
            }
        }
    }

    public void removeAll(){
        productBeanList.clear();
    }

    public void remove(ProductBean.Product productBean){
        int position = productBeanList.indexOf(productBean);
        if (position>-1){
            productBeanList.remove(productBean);
            notifyItemRemoved(position);
        }
    }

    public void clear(){
        isLoadingAdded = false;
        while (getItemCount() > 0){
            remove(getItem(0));
        }
    }

    public Boolean isEmpty(){
        return getItemCount()==0;
    }

    public void addLoadingFooter(){
        isLoadingAdded =true;
        ProductBean productBean = new ProductBean();
        add(productBean.new Product());
    }

    public void removeLoadingFooter(){
        isLoadingAdded = false;

        int position = productBeanList.size() - 1;
        ProductBean.Product item = getItem(position);

        if(item != null){
            productBeanList.remove(item);
            notifyItemRemoved(position);
        }
    }

    public ProductBean.Product getItem(int position){
        return productBeanList.get(position);
    }


}
