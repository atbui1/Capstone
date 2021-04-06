package com.example.democ.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.iclick.IClickWard;
import com.example.democ.model.WardData;

import java.util.ArrayList;
import java.util.List;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.ViewHolder> implements Filterable {
    ArrayList<WardData> mListWard;
    ArrayList<WardData> mListWardOld;
    IClickWard mIClickWard;

    public WardAdapter(ArrayList<WardData> mListWard, IClickWard mIClickWard) {
        this.mListWard = mListWard;
        this.mListWardOld = mListWard;
        this.mIClickWard = mIClickWard;
    }

    @NonNull
    @Override
    public WardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_garden_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WardAdapter.ViewHolder holder, int position) {
        final WardData wardData = mListWard.get(position);
        if (wardData == null) {
            return;
        }
        holder.mTxtGardenName.setText(wardData.getName());
        holder.mLnlRootGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickWard.clickWard(wardData);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListWard != null) {
            return mListWard.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    mListWard = mListWardOld;
                } else {
                    List<WardData> listSearch = new ArrayList<>();
                    for (WardData wardData:mListWardOld) {
                        if (wardData.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            listSearch.add(wardData);
                        }
                    }
                    mListWard = (ArrayList<WardData>) listSearch;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListWard;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListWard = (ArrayList<WardData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtGardenName;
        LinearLayout mLnlRootGarden;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtGardenName = (TextView) itemView.findViewById(R.id.txt_garden_name);
            mLnlRootGarden = (LinearLayout) itemView.findViewById(R.id.lnl_root_garden_post);
        }
    }
}
