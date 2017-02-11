package com.khosroabadi.myplantaqua.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khosroabadi.myplantaqua.R;

import java.util.List;

/**
 * Created by Alireza on 2/6/2017.
 */

public class ReferencesAdapter extends RecyclerView.Adapter<ReferencesAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> referencesList;
    private Context mContext;

    public ReferencesAdapter(List<String> referencesList, Context mContext) {
        this.referencesList = referencesList;
        this.mContext = mContext;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(layoutInflater.inflate(R.layout.gallery_cardview_content, parent , false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.referencesText.setText(referencesList.get(position));
    }


    @Override
    public int getItemCount() {
        return referencesList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView referencesText;

        public ViewHolder(View view) {
            super(view);
           // referencesText = (TextView) view.findViewById(R.id.references_text);
        }
    }
}
