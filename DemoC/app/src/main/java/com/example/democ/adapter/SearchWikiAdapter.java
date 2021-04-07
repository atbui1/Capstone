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
import com.example.democ.iclick.IClickWiki;
import com.example.democ.model.WikiData;

import java.util.ArrayList;

public class SearchWikiAdapter extends RecyclerView.Adapter<SearchWikiAdapter.ViewHolder> {

    private ArrayList<WikiData> mListWiki;
    private Context mContext;
    private IClickWiki mIClickWiki;

    public SearchWikiAdapter(ArrayList<WikiData> mListWiki, IClickWiki mIClickWiki) {
        this.mListWiki = mListWiki;
        this.mIClickWiki = mIClickWiki;
    }

    @NonNull
    @Override
    public SearchWikiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_search, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchWikiAdapter.ViewHolder holder, int position) {
        final WikiData wikiData = mListWiki.get(position);
        if (wikiData == null) {
            return;
        }
        holder.mTxtVegetableName.setText(mListWiki.get(position).getName());
        holder.mImgVegetable.setImageResource(R.mipmap.addimage64);
        holder.mLnlRootVegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickWiki.clickWiki(wikiData);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListWiki != null) {
            return mListWiki.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtVegetableName;
        ImageView mImgVegetable;
        LinearLayout mLnlRootVegetable;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtVegetableName = (TextView) itemView.findViewById(R.id.txt_vegetable_name);
            mImgVegetable = (ImageView) itemView.findViewById(R.id.img_vegetable);
            mLnlRootVegetable = (LinearLayout) itemView.findViewById(R.id.lnl_root_vegetable);
        }
    }
}
