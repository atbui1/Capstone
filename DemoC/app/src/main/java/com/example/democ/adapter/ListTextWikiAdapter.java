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
import com.example.democ.iclick.IClickListTextWiki;

import java.util.ArrayList;
import java.util.List;

public class ListTextWikiAdapter extends RecyclerView.Adapter<ListTextWikiAdapter.ViewHolder> {
    List<String> mListText;
    Context mContext;
    IClickListTextWiki mIClickListTextWiki;

    public ListTextWikiAdapter(List<String> mListText, IClickListTextWiki mIClickListTextWiki) {
        this.mListText = mListText;
        this.mIClickListTextWiki = mIClickListTextWiki;
    }

    @NonNull
    @Override
    public ListTextWikiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_wiki, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTextWikiAdapter.ViewHolder holder, final int position) {
        final String listText = mListText.get(position);
        if (listText == null) {
            return;
        }
        holder.mTxtWiki.setText(mListText.get(position));
        holder.mLnlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListTextWiki.clickListText(listText, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListText != null) {
            return mListText.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtWiki;
        LinearLayout mLnlRoot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtWiki = (TextView) itemView.findViewById(R.id.txt_wiki);
            mLnlRoot = (LinearLayout) itemView.findViewById(R.id.lnl_root);
        }
    }
}
