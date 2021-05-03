package com.example.democ.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.activity.PosterProfileActivity;
import com.example.democ.iclick.IClickPost;
import com.example.democ.model.Post;
import com.example.democ.R;
import com.example.democ.model.PostData;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.PersonalView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    ArrayList<PostData> mListPost;
    Context mContext;
    private IClickPost mIClickPost;
    UserManagement userManagement;
    static String mAccountId;
    private static String POST_SHARE = "Nhận rau";
    private static String POST_EXCHANGE = "Đổi rau";

//    public PostAdapter(ArrayList<PostData> mListPost, Context mContext) {
//        this.mListPost = mListPost;
//        this.mContext = mContext;
//    }


    public PostAdapter(ArrayList<PostData> mListPost, Context mContext, IClickPost mIClickPost) {
        this.mListPost = mListPost;
        this.mContext = mContext;
        this.mIClickPost = mIClickPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final PostData postData = mListPost.get(position);
        if (postData == null) {
            return;
        }

        String postTime = mListPost.get(position).getCreatedDate();
        String subPostTime = postTime.substring(0, 10);
        holder.mTxtPostTime.setText(subPostTime);
        holder.mTxtVegetablePostQuantity.setText("Số lượng: " + String.valueOf(mListPost.get(position).getQuantity()));
        holder.mTxtPostContent.setText(mListPost.get(position).getContent());
        holder.mTxtPostUsername.setText(mListPost.get(position).getFullName());
        holder.mTxtPhoneNumber.setText("Liên hệ: " + mListPost.get(position).getPhoneNumber());

        if (mListPost.get(position).getAvatar() == null || mListPost.get(position).getAvatar().equals("")) {
            holder.mImgImagePostUser.setImageResource(R.drawable.avatardefault);
        } else {
            Picasso.with(mContext).load(mListPost.get(position).getAvatar())
                    .placeholder(R.drawable.avatardefault)
                    .error(R.drawable.avatardefault)
                    .into(holder.mImgImagePostUser);
        }

        if (mListPost.get(position).getImageVegetablesList().size() > 0) {
            int maxImage = mListPost.get(position).getImageVegetablesList().size() - 1;
            if (mListPost.get(position).getImageVegetablesList().get(maxImage).getUrl() != null) {
                Picasso.with(mContext).load(mListPost.get(position).getImageVegetablesList().get(maxImage).getUrl())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.caybacha)
                        .into(holder.mImgPostContent);
            } else {
                holder.mImgPostContent.setImageResource(R.mipmap.addimage64);
            }
        } else {
            holder.mImgPostContent.setImageResource(R.mipmap.addimage64);
        }
        //show share or exchange
        if (mListPost.get(position).getStatius() == 1) {
            holder.mBtnPostExchange.setText(POST_SHARE);
        } else if (mListPost.get(position).getStatius() == 2) {
            holder.mBtnPostExchange.setText(POST_EXCHANGE);
        }

        //click exchange
        holder.mBtnPostExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickPost.clickBtnExchange(postData);
            }
        });
        // click user
        holder.mImgImagePostUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickPost.clickPosterUser(postData);
            }
        });
        //click report
        holder.mLnlLeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickPost.clickReportPost(postData);
            }
        });
        //click detail
        holder.mLnlImgPostContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickPost.clickPostDetail(postData);
            }
        });
        //click phone number
        holder.mTxtPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickPost.clickCallPhone(postData);
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
        TextView mTxtPostUsername, mTxtPostTime, mTxtPostContent, mTxtPostNumberLike,
                mTxtVegetablePostNeed, mTxtVegetablePostQuantity, mTxtPhoneNumber;
//        LinearLayout mLnlPostLike, mLnlPostComment, mLnlBtnExchange, mLnlLeftMenu, mLnlImagePostUser, mLnlImgPostContent;
        LinearLayout mLnlBtnExchange, mLnlLeftMenu, mLnlImagePostUser, mLnlImgPostContent;
        Button mBtnPostExchange;
        CircleImageView mImgImagePostUser;
        private int numberLike = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtPostUsername = (TextView) itemView.findViewById(R.id.txt_post_username);
            mTxtPostTime = (TextView) itemView.findViewById(R.id.txt_post_time);
            mTxtPostContent = (TextView) itemView.findViewById(R.id.txt_post_content);
            mImgPostContent = (ImageView) itemView.findViewById(R.id.img_post_content);
            mBtnPostExchange = (Button) itemView.findViewById(R.id.btn_exchange);
            mTxtVegetablePostNeed = (TextView) itemView.findViewById(R.id.txt_vegetable_need);
            mLnlImagePostUser = (LinearLayout) itemView.findViewById(R.id.lnl_image_post_user);
            //
            mLnlBtnExchange = (LinearLayout) itemView.findViewById(R.id.lnl_btn_exchange);
            mLnlLeftMenu = (LinearLayout) itemView.findViewById(R.id.lnl_left_menu);
            mImgImagePostUser = (CircleImageView) itemView.findViewById(R.id.img_post_user);
            mTxtVegetablePostQuantity = (TextView) itemView.findViewById(R.id.txt_post_vegetable_quantity);
            mLnlImgPostContent = (LinearLayout) itemView.findViewById(R.id.lnl_img_post_content);
            mTxtPhoneNumber = (TextView) itemView.findViewById(R.id.txt_post_phone_number);
        }
    }
}
