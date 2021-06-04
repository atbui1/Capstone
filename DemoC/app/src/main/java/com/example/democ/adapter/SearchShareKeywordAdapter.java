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
import com.example.democ.iclick.IClickSearchKeyword;
import com.example.democ.model.PostSearchKeyword;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchShareKeywordAdapter extends RecyclerView.Adapter<SearchShareKeywordAdapter.ViewHolder> {
    List<PostSearchKeyword> mList;
    Context mContext;
    IClickSearchKeyword mIClickSearchKeyword;

    public SearchShareKeywordAdapter(List<PostSearchKeyword> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public SearchShareKeywordAdapter(List<PostSearchKeyword> mList, Context mContext, IClickSearchKeyword mIClickSearchKeyword) {
        this.mList = mList;
        this.mContext = mContext;
        this.mIClickSearchKeyword = mIClickSearchKeyword;
    }

    @NonNull
    @Override
    public SearchShareKeywordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_search, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PostSearchKeyword postSearchKeyword = mList.get(position);
        if (postSearchKeyword == null) {
            return;
        }

        holder.mTxtVegetableName.setText(mList.get(position).getVegName());
        if (mList.get(position).getType() == 1) {
            holder.mTxtPostType.setText("Bài viết: Chia sẻ rau");
        } else if (mList.get(position).getType() == 2) {
            holder.mTxtPostType.setText("Bài viết: Trao đổi rau");
        }

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
                mIClickSearchKeyword.iClickPostSearchKeyword(postSearchKeyword);
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
        TextView mTxtVegetableName, mTxtPostType;
        ImageView mImgVegetable;
        LinearLayout mLnlRoot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtVegetableName = (TextView) itemView.findViewById(R.id.txt_vegetable_name);
            mImgVegetable = (ImageView) itemView.findViewById(R.id.img_vegetable);
            mLnlRoot = (LinearLayout) itemView.findViewById(R.id.lnl_root_vegetable);
            mTxtPostType = (TextView) itemView.findViewById(R.id.txt_post_type);
        }
    }
}
