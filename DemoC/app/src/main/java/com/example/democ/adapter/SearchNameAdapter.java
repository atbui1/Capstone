package com.example.democ.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.model.VegetableData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class SearchNameAdapter extends RecyclerView.Adapter<SearchNameAdapter.ViewHolder> {

    ArrayList<VegetableData> mVegetableList;
    Context mContext;
    IClickVegetable mIClickVegetable;

    public SearchNameAdapter(ArrayList<VegetableData> mVegetableList, Context mContext) {
        this.mVegetableList = mVegetableList;
        this.mContext = mContext;
    }

    public SearchNameAdapter(ArrayList<VegetableData> mVegetableList, Context mContext, IClickVegetable mIClickVegetable) {
        this.mVegetableList = mVegetableList;
        this.mContext = mContext;
        this.mIClickVegetable = mIClickVegetable;
    }

    public SearchNameAdapter(ArrayList<VegetableData> mVegetableList, IClickVegetable mIClickVegetable) {
        this.mVegetableList = mVegetableList;
        this.mIClickVegetable = mIClickVegetable;
    }

    @NonNull
    @Override
    public SearchNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_search, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNameAdapter.ViewHolder holder, int position) {
         final VegetableData vegetableData = mVegetableList.get(position);
        if (vegetableData == null) {
            return;
        }
        holder.mTxtVegetableName.setText(mVegetableList.get(position).getName());
//        holder.mTxtVegetableDescription.setText(mVegetableList.get(position).getDescription());
//        holder.mTxtVegetableFeature.setText(mVegetableList.get(position).getFeature());
        try {
            if (vegetableData.getImageVegetables().size() >0) {
                int maxSize = mVegetableList.get(position).getImageVegetables().size() - 1;
                System.out.println("link url: " + vegetableData.getImageVegetables().get(maxSize).getUrl());
                if (mVegetableList.get(position).getImageVegetables().get(maxSize).getUrl() != null) {
                    Picasso.with(mContext).load(mVegetableList.get(position).getImageVegetables().get(maxSize).getUrl())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.caybacha)
                            .into(holder.mImgVegetable);
                } else {
                    Picasso.with(mContext).load(mVegetableList.get(0).getImageVegetables().get(0).getUrl())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.caybacha)
                            .into(holder.mImgVegetable);
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, "aaaaaaaaaaaaa: ", ex);
        }
//        if (mVegetableList.get(position).getImageVegetables().get(0).getUrl() != null) {
//            Picasso.with(mContext).load(mVegetableList.get(position).getImageVegetables().get(maxSize).getUrl())
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .error(R.drawable.caybacha)
//                    .into(holder.mImgVegetable);
//        }
        holder.mLnlRootVegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickVegetable.clickVegetable(vegetableData);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mVegetableList != null && mVegetableList.size() > 0) {
            return mVegetableList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtVegetableName;//, mTxtVegetableDescription;//, mTxtVegetableFeature;
        ImageView mImgVegetable;
        LinearLayout mLnlRootVegetable;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtVegetableName = (TextView) itemView.findViewById(R.id.txt_vegetable_name);
//            mTxtVegetableDescription = (TextView) itemView.findViewById(R.id.txt_vegetable_description);
//            mTxtVegetableFeature = (TextView) itemView.findViewById(R.id.txt_vegetable_feature);
            mImgVegetable = (ImageView) itemView.findViewById(R.id.img_vegetable);
            mLnlRootVegetable = (LinearLayout) itemView.findViewById(R.id.lnl_root_vegetable);
        }
    }
}
