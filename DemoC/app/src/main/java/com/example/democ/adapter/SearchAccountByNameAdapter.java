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
import com.example.democ.iclick.IClickAccountName;
import com.example.democ.model.AccountSearchByName;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAccountByNameAdapter extends RecyclerView.Adapter<SearchAccountByNameAdapter.ViewHolder> {

    List<AccountSearchByName> mListAccount;
    Context mContext;
    IClickAccountName mIClickAccountName;

    public SearchAccountByNameAdapter(List<AccountSearchByName> mListAccount, Context mContext, IClickAccountName mIClickAccountName) {
        this.mListAccount = mListAccount;
        this.mContext = mContext;
        this.mIClickAccountName = mIClickAccountName;
    }

    @NonNull
    @Override
    public SearchAccountByNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemVIew = layoutInflater.inflate(R.layout.item_row_search_name_account, parent, false);
        return new ViewHolder(itemVIew);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAccountByNameAdapter.ViewHolder holder, final int position) {
        final AccountSearchByName accountSearchByName = mListAccount.get(position);
        if (accountSearchByName == null) {
            return;
        }
        holder.mTxtAccountName.setText(accountSearchByName.getAccountName());
        if (mListAccount.get(position).getAvatar() == null || mListAccount.get(position).getAvatar().equals("")) {
            holder.mImgAvatar.setImageResource(R.drawable.avatardefault);
        } else {
            Picasso.with(mContext).load(mListAccount.get(position).getAvatar())
                    .placeholder(R.drawable.avatardefault)
                    .error(R.drawable.avatardefault)
                    .into(holder.mImgAvatar);
        }

        /*click root*/
        holder.mLnlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickAccountName.clickAccountName(accountSearchByName);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListAccount != null) {
            return mListAccount.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtAccountName;
        CircleImageView mImgAvatar;
        LinearLayout mLnlRoot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtAccountName = (TextView) itemView.findViewById(R.id.txt_account_name);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.img_avatar);
            mLnlRoot = (LinearLayout) itemView.findViewById(R.id.lnl_root);
        }
    }
}
