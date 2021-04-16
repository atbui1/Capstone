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
import com.example.democ.iclick.IClickWikiTitle;
import com.example.democ.model.WikiData;
import com.example.democ.model.WikiDataTitle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchWikiTitleAdapter extends RecyclerView.Adapter<SearchWikiTitleAdapter.ViewHolder> {

    private ArrayList<WikiDataTitle> mListWiki;
    private Context mContext;
    private IClickWikiTitle mIClickWikiTitle;

    public SearchWikiTitleAdapter(ArrayList<WikiDataTitle> mListWiki, IClickWikiTitle mIClickWikiTitle) {
        this.mListWiki = mListWiki;
        this.mIClickWikiTitle = mIClickWikiTitle;
    }

    @NonNull
    @Override
    public SearchWikiTitleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_search_wiki_title, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchWikiTitleAdapter.ViewHolder holder, int position) {
        final WikiDataTitle wikiData = mListWiki.get(position);
        if (wikiData == null) {
            return;
        }
        holder.mTxtVegetableName.setText(mListWiki.get(position).getTitle());
        if (mListWiki.get(position).getImage().equals("")) {
            holder.mImgVegetable.setImageResource(R.mipmap.addimage64);
            System.out.println("if:" + mListWiki.get(position).getImage());
        } else {
            Picasso.with(mContext).load("https:" + mListWiki.get(position).getImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .fit()
                    .centerInside()
                    .into(holder.mImgVegetable);
            System.out.println("else:" + mListWiki.get(position).getImage());
        }
        holder.mLnlRootVegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickWikiTitle.clickWikiTitle(wikiData);
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
