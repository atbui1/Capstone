package com.example.democ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.model.PostData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostByAccountAdapter extends RecyclerView.Adapter<PostByAccountAdapter.ViewHolder> {
    ArrayList<PostData> mListPost;
    Context mContext;

    public PostByAccountAdapter(ArrayList<PostData> mListPost, Context mContext) {
        this.mListPost = mListPost;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PostByAccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostByAccountAdapter.ViewHolder holder, int position) {
        String postTime = mListPost.get(position).getCreatedDate();
        String subPostTime = postTime.substring(0, 9);
        holder.mTxtPostTime.setText(subPostTime);
        holder.mTxtPostContent.setText(mListPost.get(position).getContent());
        holder.mTxtPostUsername.setText(mListPost.get(position).getFullName());
        int maxSize = mListPost.get(position).getImageVegetablesList().size() - 1;
        Picasso.with(mContext).load(mListPost.get(position).getImageVegetablesList().get(maxSize).getUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.caybacha)
                .into(holder.mImgPostContent);
        if (holder.mLnlBtnExchange.getVisibility() == View.VISIBLE) {
            holder.mLnlBtnExchange.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (mListPost != null) {
            return mListPost.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImgPostUser, mImgPostContent, mImgPostLikeStatus;
        TextView mTxtPostUsername, mTxtPostTime, mTxtPostContent, mTxtPostNumberLike, mTxtVegetablePostNeed;
        LinearLayout mLnlPostLike, mLnlPostComment, mLnlBtnExchange, mLnlLeftMenu;
        Button mBtnPostExchange;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgPostUser = (ImageView) itemView.findViewById(R.id.img_post_user);
            mTxtPostUsername = (TextView) itemView.findViewById(R.id.txt_post_username);
            mTxtPostTime = (TextView) itemView.findViewById(R.id.txt_post_time);
            mTxtPostContent = (TextView) itemView.findViewById(R.id.txt_post_content);
            mImgPostContent = (ImageView) itemView.findViewById(R.id.img_post_content);
            mTxtPostNumberLike = (TextView) itemView.findViewById(R.id.txt_number_like_post);
            mLnlPostLike = (LinearLayout) itemView.findViewById(R.id.lnl_post_like);
            mLnlPostComment = (LinearLayout) itemView.findViewById(R.id.lnl_post_comment);
            mImgPostLikeStatus = (ImageView) itemView.findViewById(R.id.img_post_like_status);
            mBtnPostExchange = (Button) itemView.findViewById(R.id.btn_exchange);
            mTxtVegetablePostNeed = (TextView) itemView.findViewById(R.id.txt_vegetable_need);
            mLnlLeftMenu = (LinearLayout) itemView.findViewById(R.id.lnl_left_menu);
            mLnlBtnExchange = (LinearLayout) itemView.findViewById(R.id.lnl_btn_exchange);
        }
    }
}