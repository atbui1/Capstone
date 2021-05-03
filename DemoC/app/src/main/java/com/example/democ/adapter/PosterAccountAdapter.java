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
import com.example.democ.iclick.IClickPostAccount;
import com.example.democ.model.PostData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PosterAccountAdapter extends RecyclerView.Adapter<PosterAccountAdapter.ViewHolder> {
    ArrayList<PostData> mListPost;
    Context mContext;
    IClickPostAccount mIClickPostAccount;
    private static String POST_SHARE = "Nhận rau";
    private static String POST_EXCHANGE = "Đổi rau";

    public PosterAccountAdapter(ArrayList<PostData> mListPost, Context mContext, IClickPostAccount mIClickPostAccount) {
        this.mListPost = mListPost;
        this.mContext = mContext;
        this.mIClickPostAccount = mIClickPostAccount;
    }

    @NonNull
    @Override
    public PosterAccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_poster_account, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterAccountAdapter.ViewHolder holder, int position) {
        final PostData postData = mListPost.get(position);
        if (postData == null) {
            return;
        }

        String postTime = mListPost.get(position).getCreatedDate();
        String subPostTime = postTime.substring(0, 10);
        holder.mTxtPostTime.setText(subPostTime);
        holder.mTxtPostContent.setText(mListPost.get(position).getContent());
        holder.mTxtPostUsername.setText(mListPost.get(position).getFullName());
        holder.mTxtVegetablePostQuantity.setText("Số lượng: " + String.valueOf(mListPost.get(position).getQuantity()));
        holder.mTxtPhoneNumber.setText("Liên hệ: " + mListPost.get(position).getPhoneNumber());

        if (mListPost.get(position).getAvatar() == null || mListPost.get(position).getAvatar().equals("")) {
            holder.mImgAvatar.setImageResource(R.drawable.avatardefault);
        } else {
            Picasso.with(mContext).load(mListPost.get(position).getAvatar())
                    .placeholder(R.drawable.avatardefault)
                    .error(R.drawable.avatardefault)
                    .into(holder.mImgAvatar);
        }

        if (mListPost.get(position).getImageVegetablesList().size() > 0) {
            int maxSize = mListPost.get(position).getImageVegetablesList().size() - 1;
            if (mListPost.get(position).getImageVegetablesList().get(maxSize).getUrl() != null) {
                Picasso.with(mContext).load(mListPost.get(position).getImageVegetablesList().get(maxSize).getUrl())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.caybacha)
                        .into(holder.mImgPostContent);
            } else {
                holder.mImgPostContent.setImageResource(R.mipmap.addimage64);
            }

        } else {
            holder.mImgPostContent.setImageResource(R.mipmap.addimage64);
        }

        if (postData.getStatius() == 1) {
            holder.mTxtBtnExchange.setText(POST_SHARE);
        } else if (postData.getStatius() == 2) {
            holder.mTxtBtnExchange.setText(POST_EXCHANGE);
        }
        /*click exchange*/
        holder.mTxtBtnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickPostAccount.clickPostAccount(postData);
            }
        });
        /*click call phone*/
        holder.mTxtPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickPostAccount.clickCallPhone(postData);
            }
        });
        /*click post detail*/
        holder.mImgPostContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickPostAccount.clickPostDetail(postData);
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
        ImageView mImgPostContent;
        CircleImageView mImgAvatar;
        TextView mTxtPostUsername, mTxtPostTime, mTxtPostContent, mTxtVegetablePostNeed,
                mTxtVegetablePostQuantity, mTxtDeletePost, mTxtEditPost, mTxtBtnExchange, mTxtPhoneNumber;
        LinearLayout mLnlBtnExchange, mLnlLeftMenu;
        Button mBtnPostExchange;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtPostUsername = (TextView) itemView.findViewById(R.id.txt_post_username);
            mTxtPostTime = (TextView) itemView.findViewById(R.id.txt_post_time);
            mTxtPostContent = (TextView) itemView.findViewById(R.id.txt_post_content);
            mImgPostContent = (ImageView) itemView.findViewById(R.id.img_post_content);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.img_avatar);
            mBtnPostExchange = (Button) itemView.findViewById(R.id.btn_exchange);
            mTxtVegetablePostNeed = (TextView) itemView.findViewById(R.id.txt_vegetable_need);
            mLnlLeftMenu = (LinearLayout) itemView.findViewById(R.id.lnl_left_menu);
            mLnlBtnExchange = (LinearLayout) itemView.findViewById(R.id.lnl_btn_exchange);
            mTxtVegetablePostQuantity = (TextView) itemView.findViewById(R.id.txt_post_vegetable_quantity);
            mTxtDeletePost = (TextView) itemView.findViewById(R.id.txt_post_remove);
            mTxtEditPost = (TextView) itemView.findViewById(R.id.txt_post_edit);
            mTxtBtnExchange = (TextView) itemView.findViewById(R.id.txt_btn_exchange);
            mTxtPhoneNumber = (TextView) itemView.findViewById(R.id.txt_post_phone_number);
        }
    }
}
