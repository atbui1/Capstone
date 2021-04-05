package com.example.democ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableSearchDescription;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchDescriptionAdapter extends RecyclerView.Adapter<SearchDescriptionAdapter.ViewHolder> {

    ArrayList<VegetableSearchDescription> mVegetableList;
    Context mContext;

    public SearchDescriptionAdapter(ArrayList<VegetableSearchDescription> mVegetableList, Context mContext) {
        this.mVegetableList = mVegetableList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SearchDescriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_search, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchDescriptionAdapter.ViewHolder holder, int position) {
        holder.mTxtVegetableName.setText(mVegetableList.get(position).getName());
        holder.mTxtVegetableDescription.setText(mVegetableList.get(position).getDescription());
//        holder.mTxtVegetableFeature.setText(mVegetableList.get(position).getFeature());
        int maxSize = mVegetableList.get(position).getImageVegetables().size() - 1;
        if (mVegetableList.get(position).getImageVegetables().get(maxSize).getUrl() != null) {
            Picasso.with(mContext).load(mVegetableList.get(position).getImageVegetables().get(maxSize).getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(holder.mImgVegetable);
        }
    }

    @Override
    public int getItemCount() {
        if (mVegetableList != null) {
            return mVegetableList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtVegetableName, mTxtVegetableDescription;//, mTxtVegetableFeature;
        ImageView mImgVegetable;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtVegetableName = (TextView) itemView.findViewById(R.id.txt_vegetable_name);
            mTxtVegetableDescription = (TextView) itemView.findViewById(R.id.txt_vegetable_description);
//            mTxtVegetableFeature = (TextView) itemView.findViewById(R.id.txt_vegetable_feature);
            mImgVegetable = (ImageView) itemView.findViewById(R.id.img_vegetable);
        }
    }
}
