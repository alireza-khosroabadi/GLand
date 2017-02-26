package com.khosroabadi.myplantaqua.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.adapters.dataFactory.PaginationAdapterCallback;
import com.khosroabadi.myplantaqua.customComponent.AwesomeTextView;
import com.khosroabadi.myplantaqua.dataModel.da.favorits.FavoritsDataProvider;
import com.khosroabadi.myplantaqua.dataModel.dm.favorits.FavoritsBean;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.khosroabadi.myplantaqua.tools.ShareDataUtils;
import com.khosroabadi.myplantaqua.webservice.WSUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.khosroabadi on 10/31/2016.
 */

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private  final  String IMAGE_URL = ConstantManager.BASE_URL+"plantImage/";
    private final List<ProductBean.Product> productBeanList;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private PaginationAdapterCallback mCallback;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private String errorMsg;
    int lastPosition = -1;

    public ProductListAdapter(Context mContext) {
        this.mContext = mContext;
        this.mCallback = (PaginationAdapterCallback) mContext;
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
        ProductListAdapter.ProductVH viewHolder;
        View v1 = inflater.inflate(R.layout.product_list_holder, parent, false);
        viewHolder = new ProductVH(v1);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductBean.Product product = this.productBeanList.get(position);
        switch (getItemViewType(position)) {
            case ITEM: {
                final ProductVH pHolder = (ProductVH) holder;
                pHolder.plantEnName.setText(product.getEnName());
                pHolder.rate.setText(product.getRate().toString());
                pHolder.plantId.setText(product.getId().toString());


                Glide.with(mContext)
                .load(IMAGE_URL+ product.getImageName())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(pHolder.plantImage);
                pHolder.plantImage.setBackgroundColor(Color.TRANSPARENT);
                pHolder.mProgress.setVisibility(View.GONE);
                final FavoritsDataProvider favoritsDataProvider = new FavoritsDataProvider(mContext);
                FavoritsBean favoritsBean = favoritsDataProvider.findByProductId(product.getId());


                pHolder.shareContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShareDataUtils shareDataUtils = new ShareDataUtils(mContext);
                        shareDataUtils.sendProductContent(product , IMAGE_URL+ product.getImageName());
                    }
                });

                    Animation animation = AnimationUtils.loadAnimation(mContext , (position > lastPosition) ? R.anim.product_list_anim_up_from_bottom
                            : R.anim.product_list_anim_down_from_top);
                //holder.itemView.startAnimation(animation);
                lastPosition = position;
                    pHolder.itemView.startAnimation(animation);

        if(favoritsBean.getId() !=null) {
            ((ProductVH) holder).favoritsTextView.setText(R.string.fa_heart);
            ((ProductVH) holder).favoritsTextView.setTextColor(mContext.getResources().getColor(R.color.md_red_800));
        }else{
            ((ProductVH) holder).favoritsTextView.setText(R.string.fa_heart);
            ((ProductVH) holder).favoritsTextView.setTextColor(mContext.getResources().getColor(R.color.md_grey_700));
        }


                pHolder.favoritsTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FavoritsBean favoritsBean = favoritsDataProvider.findByProductId(product.getId());
                        if (favoritsBean.getId() !=null) {
                            setFavorits(product, -1, pHolder);
                        } else {
                            setFavorits(product, 1, pHolder);
                        }
                    }
                });


            }
            break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;
                if(retryPageLoad){
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgress.setVisibility(View.GONE);
                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    mContext.getString(R.string.unknow_error));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgress.setVisibility(View.VISIBLE);
                }

                break;


        }


    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;
        private ProgressBar mProgress;

        public LoadingVH(View itemView) {
            super(itemView);
            mProgress = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:
                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
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
        public TextView plantId;
        public TextView plantEnName;
        public TextView rate;
        public ImageView plantImage;
        public AwesomeTextView favoritsTextView , shareContent;
        public CardView cardView;
        private ProgressBar mProgress;

        public ProductVH(View view){
            super(view);
            plantListHolder = (LinearLayout) view.findViewById(R.id.plant_main_holder);
            plantNameHolder = (LinearLayout) view.findViewById(R.id.plant_name_holder);
            plantImage = (ImageView) view.findViewById(R.id.plant_image_holder);
            cardView = (CardView) view.findViewById(R.id.plant_row_card);
            plantEnName = (TextView) view.findViewById(R.id.plant_enName);
            rate = (TextView) view.findViewById(R.id.rate);
            plantId = (TextView) view.findViewById(R.id.item_selected_id);
            favoritsTextView = (AwesomeTextView) view.findViewById(R.id.likeImageView);
            favoritsTextView.setFont(mContext);
            mProgress = (ProgressBar) view.findViewById(R.id.movie_progress);
            shareContent = (AwesomeTextView) view.findViewById(R.id.share_content);
            shareContent.setFont(mContext);
            plantNameHolder.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (onItemClickListener!=null){
                onItemClickListener.onItemClick(view , Integer.parseInt(plantId.getText().toString()) , getAdapterPosition());
            }
        }


    }


    public interface OnItemClickListener{
        void onItemClick(View view , int position , int adapterPosition);
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener){
        this.onItemClickListener = mOnItemClickListener;
    }


    private ProductVH setFavorits(final ProductBean.Product product , final Integer rateValue , final ProductVH holder ){
        WSUtils wsUtils = new WSUtils(mContext);
        if (wsUtils.isNetworkAvailable()) {
                wsUtils.raiting(product.getId() , rateValue ,
                new WSUtils.RatingCallback() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if(result) {
                            FavoritsDataProvider favoritsDataProvider = new FavoritsDataProvider(mContext);
                            if(rateValue>0){
                                favoritsDataProvider.insert(product.getId());
                                product.setRate(product.getRate()+rateValue);
                                holder.rate.setText((product.getRate()).toString());

                                holder.favoritsTextView.setText(R.string.fa_heart);
                                holder.favoritsTextView.setTextColor(mContext.getResources().getColor(R.color.md_red_800));

                            }else {
                                favoritsDataProvider.delete(product.getId());
                                product.setRate(product.getRate()+rateValue);
                                holder.rate.setText(product.getRate().toString());
                                holder.favoritsTextView.setText(R.string.fa_heart);
                                holder.favoritsTextView.setTextColor(mContext.getResources().getColor(R.color.md_grey_700));

                            }
                        }else {
                            //Toast.makeText(mContext , "Nashod" , Toast.LENGTH_LONG).show();
                        }

                    }
                });

        }
        return holder;
    }

    public void updateProduct(Integer selectedProductId){

/*        if (selectedProductId != null) {
*//*            ProductBean.Product product = productBeanList.get(selectedProductId);
            FavoritsDataProvider favoritsDataProvider = new FavoritsDataProvider(mContext);
            FavoritsBean favoritsBean = favoritsDataProvider.findByProductId(product.getId());
            if (favoritsBean.getId() != null) {
                ((ProductVH) productViewHolder).favoritsTextView.setText(R.string.fa_heart);
                ((ProductVH) productViewHolder).favoritsTextView.setTextColor(mContext.getResources().getColor(R.color.md_red_800));
                Integer newRae = Integer.valueOf(productViewHolder.rate.getText().toString())+1;
                ((ProductVH) productViewHolder).rate.setText(newRae.toString());
                notifyItemChanged(selectedProductId);
            } else {
                ((ProductVH) productViewHolder).favoritsTextView.setText(R.string.fa_heart);
                ((ProductVH) productViewHolder).favoritsTextView.setTextColor(mContext.getResources().getColor(R.color.md_grey_700));
                Integer newRae = Integer.valueOf(productViewHolder.rate.getText().toString())-1;
                ((ProductVH) productViewHolder).rate.setText(Integer.valueOf(productViewHolder.rate.getText().toString())-1.toString());
                notifyItemChanged(selectedProductId);
            }*//*
            updatedProduct=true;
            notifyItemChanged(selectedProductId);
        }*/

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

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(productBeanList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public ProductBean.Product getItem(int position){
        return productBeanList.get(position);
    }


}
