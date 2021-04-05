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
import com.example.democ.model.ExchangeData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.ViewHolder> {

    ArrayList<ExchangeData> mlistExchange;
    Context mContext;

    public ExchangeAdapter(ArrayList<ExchangeData> mlistExchange, Context mContext) {
        this.mlistExchange = mlistExchange;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ExchangeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeAdapter.ViewHolder holder, int position) {

        holder.mTxtPostTime.setText(mlistExchange.get(position).getCreatedDate());
//        holder.mTxtPostContent.setText(mlistExchange.get(position).getContent());
//        holder.mTxtPostUsername.setText(mlistExchange.get(position).getFullName());
        Picasso.with(mContext).load("https://mtrend.vn/wp-content/uploads/2019/05/anh-co-trang-trung-quoc-8.jpg").into(holder.mImgPostUser);

//        if (mlistExchange.get(position).getImageVegetablesList().get(0).getUrl() != null) {
//            Picasso.with(mContext).load(mlistExchange.get(position).getImageVegetablesList().get(0).getUrl())
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .error(R.drawable.caybacha)
//                    .into(holder.mImgPostContent);
//        }
    }

    @Override
    public int getItemCount() {
        if (mlistExchange != null) {
            return mlistExchange.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImgPostUser, mImgPostContent, mImgPostLikeStatus;
        TextView mTxtPostUsername, mTxtPostTime, mTxtPostContent, mTxtPostNumberLike, mTxtVegetablePostNeed;
        LinearLayout mLnlPostLike, mLnlPostComment;
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
        }
    }
}
