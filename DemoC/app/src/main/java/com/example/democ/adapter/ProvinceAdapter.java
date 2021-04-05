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
import com.example.democ.iclick.IClickProvince;
import com.example.democ.model.ProvinceData;

import java.util.ArrayList;
import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder> implements Filterable {
    ArrayList<ProvinceData> mListProvince;
    ArrayList<ProvinceData> mListProvinceOld;
    IClickProvince mIClickProvince;

    public ProvinceAdapter(ArrayList<ProvinceData> mListProvince, IClickProvince mIClickProvince) {
        this.mListProvince = mListProvince;
        this.mListProvinceOld = mListProvince;
        this.mIClickProvince = mIClickProvince;
    }

    @NonNull
    @Override
    public ProvinceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_garden_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceAdapter.ViewHolder holder, int position) {
        final ProvinceData provinceData = mListProvince.get(position);
        if (provinceData == null) {
            return;
        }
        holder.mTxtGardenName.setText(mListProvince.get(position).getName());
        holder.mLnlRootGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickProvince.clickProvince(provinceData);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListProvince != null) {
            return mListProvince.size();
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
                    mListProvince = mListProvinceOld;
                } else {
                    List<ProvinceData> listSearch = new ArrayList<>();
                    for (ProvinceData provinceData:mListProvinceOld) {
                        if (provinceData.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            listSearch.add(provinceData);
                        }
                    }
                    mListProvince = (ArrayList<ProvinceData>) listSearch;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListProvince;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListProvince = (ArrayList<ProvinceData>) filterResults.values;
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
