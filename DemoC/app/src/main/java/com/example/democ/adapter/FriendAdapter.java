package com.example.democ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.iclick.IClickFriend;
import com.example.democ.model.FriendData;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private List<FriendData> mList;
    private Context mContext;
    private IClickFriend mIClickFriend;

    public FriendAdapter(List<FriendData> mList, IClickFriend mIClickFriend) {
        this.mList = mList;
        this.mIClickFriend = mIClickFriend;
    }

    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_friend, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FriendAdapter.ViewHolder holder, final int position) {
        final FriendData friendData = mList.get(position);
        if (friendData == null) {
            return;
        }
        holder.mTxtFriendName.setText(mList.get(position).getFriendName());
        holder.mLnlFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickFriend.clickFriend(friendData);
            }
        });
        holder.mLnlDeleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickFriend.clickDeleteFriend(friendData, holder.getAdapterPosition());
            }
        });
        if (mList.get(position).getAvatar() == null || mList.get(position).getAvatar().equals("")) {
            holder.mImgAvatar.setImageResource(R.drawable.avatardefault);
        } else {
            Picasso.with(mContext).load(mList.get(position).getAvatar())
                    .placeholder(R.drawable.avatardefault)
                    .error(R.drawable.avatardefault)
                    .into(holder.mImgAvatar);
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtFriendName;
        LinearLayout mLnlFriend, mLnlDeleteFriend;
        CircleImageView mImgAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtFriendName = (TextView) itemView.findViewById(R.id.txt_friend_name);
            mLnlFriend = (LinearLayout) itemView.findViewById(R.id.lnl_friend);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.img_avatar);
            mLnlDeleteFriend = (LinearLayout) itemView.findViewById(R.id.lnl_delete_friend);
        }
    }
}
