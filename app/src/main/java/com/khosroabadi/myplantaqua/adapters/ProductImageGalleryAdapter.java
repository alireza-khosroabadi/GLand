package com.khosroabadi.myplantaqua.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.tools.ConstantManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Alireza on 2/6/2017.
 */

public class ProductImageGalleryAdapter extends RecyclerView.Adapter<ProductImageGalleryAdapter.ViewHolder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<String> imageList;
    private ProductImageGalleryAdapter.OnItemClickListener onItemClickListener;
    private  final  String IMAGE_URL = ConstantManager.BASE_URL+"plantImage/";


    public ProductImageGalleryAdapter(Context mContext, List<String> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.gallery_cardview_content , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.imageName.setText(imageList.get(position));
        Picasso.with(mContext)
                .load(IMAGE_URL+ imageList.get(position))
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        ImageView productImage;
        TextView imageName;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.product_gallery_cardView_image);
            imageName = (TextView) itemView.findViewById(R.id.product_gallery_cardView_image_name);
        }


        @Override
        public void onClick(View view) {
            if (onItemClickListener!=null){
                onItemClickListener.onItemClick(view , imageName.getText().toString() );
            }
        }

    }


    public interface OnItemClickListener{
        void onItemClick(View view , String imageName );
    }

    public void setOnItemClickListener(final ProductImageGalleryAdapter.OnItemClickListener mOnItemClickListener){
        this.onItemClickListener = mOnItemClickListener;
    }
}
