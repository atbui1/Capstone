package com.example.democ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.iclick.IClickSearchPostName;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchName;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchShareNameAdapter extends RecyclerView.Adapter<SearchShareNameAdapter.ViewHolder> {
    List<PostSearchName> mList;
    Context mContext;
    IClickSearchPostName mIClickSearchPostName;

    public SearchShareNameAdapter(List<PostSearchName> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public SearchShareNameAdapter(List<PostSearchName> mList, Context mContext, IClickSearchPostName mIClickSearchPostName) {
        this.mList = mList;
        this.mContext = mContext;
        this.mIClickSearchPostName = mIClickSearchPostName;
    }

    @NonNull
    @Override
    public SearchShareNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_search, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchShareNameAdapter.ViewHolder holder, int position) {
        final PostSearchName postSearchName = mList.get(position);
        if (postSearchName == null) {
            return;
        }

        holder.mTxtVegetableName.setText(mList.get(position).getVegName());

        if (mList.get(position).getImageVegetablesList().size() > 0) {
            int maxSize = mList.get(position).getImageVegetablesList().size() - 1;
            if (mList.get(position).getImageVegetablesList().get(maxSize).getUrl() != null) {
                Picasso.with(mContext).load(mList.get(position).getImageVegetablesList().get(maxSize).getUrl())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.caybacha)
                        .into(holder.mImgVegetable);
            }
        } else {
            holder.mImgVegetable.setImageResource(R.mipmap.addimage64);
        }

        holder.mLnlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickSearchPostName.iClickSearchShareName(postSearchName);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtVegetableName;
        ImageView mImgVegetable;
        LinearLayout mLnlRoot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtVegetableName = (TextView) itemView.findViewById(R.id.txt_vegetable_name);
            mImgVegetable = (ImageView) itemView.findViewById(R.id.img_vegetable);
            mLnlRoot = (LinearLayout) itemView.findViewById(R.id.lnl_root_vegetable);
        }
    }
}
