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

//        userManagement = new UserManagement(mContext);
//        userManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
//            @Override
//            public void onDataSuccess(User user) {
//                mAccountId = user.getAccountId();
//                System.out.println("-------------");
//                System.out.println(mAccountId);
//                System.out.println(postData.getAccountId());
//                System.out.println("-------------");
//                if (mAccountId.equals(mListPost.get(position).getAccountId())){
//                    System.out.println("*******");
//                    System.out.println(postData.getAccountId());
//                    System.out.println("*****");
//                    holder.mLnlBtnExchange.setVisibility(View.GONE);
//                } else {
////                    holder.mLnlBtnExchange.setVisibility(View.INVISIBLE);
//                }
//                int statusShare = postData.getStatius();
//                if (statusShare == 1) {
//                    holder.mBtnPostExchange.setText("Nhận");
//                } else if(statusShare == 2) {
//                    holder.mBtnPostExchange.setText("Trao đổi");
//                }
//            }
//
//            @Override
//            public void onDataFail() {
//
//            }
//        });

        holder.mTxtPostTime.setText(mListPost.get(position).getCreatedDate());
        holder.mTxtVegetablePostQuantity.setText("Số lượng: " + String.valueOf(mListPost.get(position).getQuantity()));
        holder.mTxtPostContent.setText(mListPost.get(position).getContent());
        holder.mTxtPostUsername.setText(mListPost.get(position).getFullName());
        Picasso.with(mContext).load("https://mtrend.vn/wp-content/uploads/2019/05/anh-co-trang-trung-quoc-8.jpg")
                .into(holder.mImgImagePostUser);

        int maxImage = mListPost.get(position).getImageVegetablesList().size() - 1;

//img
        if (mListPost.get(position).getImageVegetablesList().size() > 0) {
            Picasso.with(mContext).load(mListPost.get(position).getImageVegetablesList().get(maxImage).getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(holder.mImgPostContent);
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

    }

    @Override
    public int getItemCount() {
        if (mListPost != null) {
            return mListPost.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImgPostContent, mImgPostLikeStatus;
        TextView mTxtPostUsername, mTxtPostTime, mTxtPostContent, mTxtPostNumberLike, mTxtVegetablePostNeed, mTxtVegetablePostQuantity;
        LinearLayout mLnlPostLike, mLnlPostComment, mLnlBtnExchange, mLnlLeftMenu, mLnlImagePostUser;
        Button mBtnPostExchange;
        CircleImageView mImgImagePostUser;
        private int numberLike = 0;

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
            mLnlImagePostUser = (LinearLayout) itemView.findViewById(R.id.lnl_image_post_user);
            //
            mLnlBtnExchange = (LinearLayout) itemView.findViewById(R.id.lnl_btn_exchange);
            mLnlLeftMenu = (LinearLayout) itemView.findViewById(R.id.lnl_left_menu);
            mImgImagePostUser = (CircleImageView) itemView.findViewById(R.id.img_post_user);
            mTxtVegetablePostQuantity = (TextView) itemView.findViewById(R.id.txt_post_vegetable_quantity);

//            mImgPostUser.setOnClickListener((View.OnClickListener) this);
            mImgPostContent.setOnClickListener((View.OnClickListener) this);
            mTxtPostUsername.setOnClickListener((View.OnClickListener) this);
            mLnlPostLike.setOnClickListener((View.OnClickListener) this);
            mLnlPostComment.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case R.id.img_post_user:
////                    Toast.makeText(view.getContext(),
////                            "Xem trang cá nhân của " + mTxtPostUsername.getText() , Toast.LENGTH_SHORT)
////                            .show();
//                    Intent intent = new Intent(mContext.getApplicationContext(), PosterProfileActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                    intent.putExtra("OWNER_PROFILE", false);
//                    mContext.startActivity(intent);
//                    break;
                case R.id.txt_post_username:
                    Toast.makeText(view.getContext(),
                            mTxtPostUsername.getText() + "sssssssssssssss", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case R.id.img_post_content:
                    Toast.makeText(view.getContext(),
                            "Zoom hình", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case R.id.lnl_post_like:
                    numberLike = numberLike + 1;
                    if (numberLike % 2 != 0) {
                        Toast.makeText(view.getContext(),
                                numberLike + " lan 111111111111", Toast.LENGTH_SHORT)
                                .show();
                        mImgPostLikeStatus.setImageResource(R.drawable.liketrue);
                    } else {
                        Toast.makeText(view.getContext(),
                                numberLike + " lan 22222222222222", Toast.LENGTH_SHORT)
                                .show();
                        mImgPostLikeStatus.setImageResource(R.drawable.likefasle);
                    }
                    break;
                case R.id.lnl_post_comment:
                    Toast.makeText(view.getContext(),
                            "xem bình luận bài đăng của " + mTxtPostUsername.getText(), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    }
}
