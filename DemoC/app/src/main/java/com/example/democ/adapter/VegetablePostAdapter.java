package com.example.democ.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.model.VegetableData;

import java.util.ArrayList;

public class VegetablePostAdapter extends RecyclerView.Adapter<VegetablePostAdapter.ViewHolder> {
    private ArrayList<VegetableData> mVegetableList;
    private IClickVegetable iClickVegetable;

    public VegetablePostAdapter(ArrayList<VegetableData> mVegetableList, IClickVegetable iClickVegetable) {
        this.mVegetableList = mVegetableList;
        this.iClickVegetable = iClickVegetable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_vegetable_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final VegetableData vegetableData = mVegetableList.get(position);
        if (vegetableData == null) {
            return;
        }
        holder.mTxtVegetableName.setText(mVegetableList.get(position).getName());
        holder.mTxtVegetableQuantity.setText(String.valueOf(mVegetableList.get(position).getQuantity()));
        holder.mTxtVegetableName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickVegetable.clickVegetable(vegetableData);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mVegetableList != null) {
            return mVegetableList.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtVegetableName, mTxtVegetableQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtVegetableName = (TextView) itemView.findViewById(R.id.txt_vegetable_name);
            mTxtVegetableQuantity = (TextView) itemView.findViewById(R.id.txt_vegetable_quantity);
        }
    }
}
