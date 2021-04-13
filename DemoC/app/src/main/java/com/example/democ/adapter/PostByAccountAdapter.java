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
import com.example.democ.iclick.IClickPost;
import com.example.democ.iclick.IClickPostAccount;
import com.example.democ.model.PostData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostByAccountAdapter extends RecyclerView.Adapter<PostByAccountAdapter.ViewHolder> {
    ArrayList<PostData> mListPost;
    Context mContext;
    IClickPostAccount mIClickPostAccount;

    public PostByAccountAdapter(ArrayList<PostData> mListPost, Context mContext, IClickPostAccount mIClickPostAccount) {
        this.mListPost = mListPost;
        this.mContext = mContext;
        this.mIClickPostAccount = mIClickPostAccount;
    }

    @NonNull
    @Override
    public PostByAccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_post_account, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostByAccountAdapter.ViewHolder holder, int position) {
        final PostData postData = mListPost.get(position);
        if (postData == null) {
            return;
        }

        String postTime = mListPost.get(position).getCreatedDate();
        String subPostTime = postTime.substring(0, 10);
        holder.mTxtPostTime.setText(subPostTime);
        holder.mTxtPostContent.setText(mListPost.get(position).getContent());
        holder.mTxtPostUsername.setText(mListPost.get(position).getFullName());
        holder.mTxtVegetablePostQuantity.setText("Số lượng " + String.valueOf(mListPost.get(position).getQuantity()));
        int maxSize = mListPost.get(position).getImageVegetablesList().size() - 1;

        if (mListPost.get(position).getImageVegetablesList().size() > 0) {
            Picasso.with(mContext).load(mListPost.get(position).getImageVegetablesList().get(maxSize).getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(holder.mImgPostContent);
        } else {
            holder.mImgPostContent.setImageResource(R.mipmap.addimage64);
        }


//        if (holder.mLnlBtnExchange.getVisibility() == View.VISIBLE) {
//            holder.mLnlBtnExchange.setVisibility(View.GONE);
//        }
        holder.mLnlLeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickPostAccount.clickPostAccount(postData);
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxxxxxxxx");
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxxxxxxxx");
                System.out.println("a: " + postData.getAccountId());
                System.out.println("b: " + postData.getFullName());
                System.out.println("c: " + postData.getContent());
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxxxxxxxx");
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxxxxxxxx");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListPost != null) {
            return mListPost.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImgPostContent, mImgPostLikeStatus;
        TextView mTxtPostUsername, mTxtPostTime, mTxtPostContent, mTxtPostNumberLike, mTxtVegetablePostNeed, mTxtVegetablePostQuantity;
        LinearLayout mLnlPostLike, mLnlPostComment, mLnlBtnExchange, mLnlLeftMenu;
        Button mBtnPostExchange;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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
            mTxtVegetablePostQuantity = (TextView) itemView.findViewById(R.id.txt_post_vegetable_quantity);
        }
    }
}
