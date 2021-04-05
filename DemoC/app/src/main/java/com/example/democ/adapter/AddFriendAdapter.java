package com.example.democ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.model.AddFriendRequest;

import java.util.ArrayList;

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.ViewHolder> {
    ArrayList<AddFriendRequest> mListAddFriend;
    Context mContext;

    public AddFriendAdapter(ArrayList<AddFriendRequest> mListAddFriend, Context mContext) {
        this.mListAddFriend = mListAddFriend;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AddFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_request_add_friend, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFriendAdapter.ViewHolder holder, int position) {
        holder.mTxtRequestName.setText(mListAddFriend.get(position).getAccountSendName());
    }

    @Override
    public int getItemCount() {
        if (mListAddFriend != null) {
            return mListAddFriend.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtRequestName, mTxtContent;
        LinearLayout mLnlOk, mLnlNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtRequestName = (TextView) itemView.findViewById(R.id.txt_request_name);
            mTxtContent = (TextView) itemView.findViewById(R.id.txt_request_content);
            mLnlOk = (LinearLayout) itemView.findViewById(R.id.lnl_ok);
            mLnlNo = (LinearLayout) itemView.findViewById(R.id.lnl_no);
        }
    }
}
