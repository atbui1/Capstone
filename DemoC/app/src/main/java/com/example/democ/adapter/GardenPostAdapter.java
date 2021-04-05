package com.example.democ.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.activity.CreatePostActivity;
import com.example.democ.iclick.IClickGarden;
import com.example.democ.model.GardenResult;

import java.util.ArrayList;

public class GardenPostAdapter extends RecyclerView.Adapter<GardenPostAdapter.ViewHolder> {
    ArrayList<GardenResult> mGardenList;
    IClickGarden iClickGarden;

    public GardenPostAdapter(ArrayList<GardenResult> mGardenList, IClickGarden iClickGarden) {
        this.mGardenList = mGardenList;
        this.iClickGarden = iClickGarden;
    }

    @NonNull
    @Override
    public GardenPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_garden_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GardenPostAdapter.ViewHolder holder, final int position) {
        final GardenResult gardenResult = mGardenList.get(position);
        if (gardenResult == null) {
            return;
        }
        holder.mTxtGardenName.setText(mGardenList.get(position).getName());
        holder.mLnlRootGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickGarden.clickGarden(gardenResult);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mGardenList != null) {
            return mGardenList.size();
        }
        return 0;
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
