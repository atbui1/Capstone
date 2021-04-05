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
import com.example.democ.iclick.IClickDistrict;
import com.example.democ.model.DistrictData;

import java.util.ArrayList;
import java.util.List;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.ViewHolder> implements Filterable {
    ArrayList<DistrictData> mListDistrict;
    ArrayList<DistrictData> mListDistrictOld;
    IClickDistrict mIClickDistrict;

    public DistrictAdapter(ArrayList<DistrictData> mListDistrict, IClickDistrict mIClickDistrict) {
        this.mListDistrict = mListDistrict;
        this.mListDistrictOld = mListDistrict;
        this.mIClickDistrict = mIClickDistrict;
    }

    @NonNull
    @Override
    public DistrictAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_garden_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictAdapter.ViewHolder holder, int position) {
        final DistrictData districtData = mListDistrict.get(position);
        if (districtData == null) {
            return;
        }
        holder.mTxtGardenName.setText(districtData.getName());
        holder.mLnlRootGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickDistrict.clickDistrict(districtData);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListDistrict != null) {
            return mListDistrict.size();
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
                    mListDistrict = mListDistrictOld;
                } else {
                    List<DistrictData> listSearch = new ArrayList<>();
                    for (DistrictData districtData:mListDistrictOld) {
                        if (districtData.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            listSearch.add(districtData);
                        }
                    }
                    mListDistrict = (ArrayList<DistrictData>) listSearch;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListDistrict;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListDistrict = (ArrayList<DistrictData>) filterResults.values;
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
