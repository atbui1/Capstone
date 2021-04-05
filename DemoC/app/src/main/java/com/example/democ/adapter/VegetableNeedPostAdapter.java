package com.example.democ.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.iclick.IClickVegetableNeed;
import com.example.democ.model.VegetableNeedAll;

import java.util.ArrayList;

public class VegetableNeedPostAdapter extends RecyclerView.Adapter<VegetableNeedPostAdapter.ViewHolder> {
    private ArrayList<VegetableNeedAll> mListVegetable;
    private IClickVegetableNeed iClickVegetableNeed;

    public VegetableNeedPostAdapter(ArrayList<VegetableNeedAll> mListVegetable, IClickVegetableNeed iClickVegetableNeed) {
        this.mListVegetable = mListVegetable;
        this.iClickVegetableNeed = iClickVegetableNeed;
    }

    @NonNull
    @Override
    public VegetableNeedPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_vegetable_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetableNeedPostAdapter.ViewHolder holder, int position) {
        final VegetableNeedAll vegetableNeedAll = mListVegetable.get(position);
        if (vegetableNeedAll == null) {
            return;
        }
        holder.mTxtVegetableName.setText(mListVegetable.get(position).getText());
        holder.mTxtVegetableName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickVegetableNeed.clickVegetableNeed(vegetableNeedAll);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListVegetable != null) {
            return mListVegetable.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtVegetableName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtVegetableName = (TextView) itemView.findViewById(R.id.txt_vegetable_name);
        }
    }
}
