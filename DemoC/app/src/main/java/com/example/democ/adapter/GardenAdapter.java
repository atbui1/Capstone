package com.example.democ.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.democ.R;
import com.example.democ.activity.GardenActivity;
import com.example.democ.iclick.IClickGardenFull;
import com.example.democ.model.GardenResult;

import java.util.ArrayList;

public class GardenAdapter extends RecyclerView.Adapter<GardenAdapter.ViewHolder> {

    ArrayList<GardenResult> mGardenList;
    Context context;
    IClickGardenFull mIClickGardenFull;

    public GardenAdapter(ArrayList<GardenResult> mGardenList, Context context, IClickGardenFull mIClickGardenFull) {
        this.mGardenList = mGardenList;
        this.context = context;
        this.mIClickGardenFull = mIClickGardenFull;
    }

    @NonNull
    @Override
    public GardenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_garden, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final GardenAdapter.ViewHolder holder, final int position) {

        final GardenResult gardenResult = mGardenList.get(position);
        if (gardenResult == null) {
            return;
        }

        holder.mTxtGardenName.setText(mGardenList.get(position).getName());
        holder.mTxtGardenAddress.setText(mGardenList.get(position).getAddress());


//        holder.mLnlGardenRoot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "garden name: " + mGardenList.get(position).getId(), Toast.LENGTH_SHORT).show();
//                Intent intentGarden = new Intent(context.getApplicationContext(), GardenActivity.class);
//                intentGarden.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                Bundle bundle = new Bundle();
//                bundle.putString("GARDEN_NAME", mGardenList.get(position).getName());
//                bundle.putString("GARDEN_ADDRESS", mGardenList.get(position).getAddress());
//                bundle.putInt("GARDEN_ID", mGardenList.get(position).getId());
////                intentGarden.putExtras(bundle);
//                intentGarden.putExtra("infoGardenTo", bundle);
//                context.startActivity(intentGarden);
//            }
//        });

        holder.mLnlGardenRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickGardenFull.clickGarden(gardenResult);
            }
        });
        holder.mTxtUpdateGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickGardenFull.clickGardenUpdate(gardenResult);
            }
        });
        holder.mTxtDeleteGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickGardenFull.clickGardenDelete(gardenResult, holder.getAdapterPosition());
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
        LinearLayout mLnlGardenRoot;
        TextView mTxtGardenName, mTxtGardenAddress, mTxtDeleteGarden, mTxtUpdateGarden;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtGardenName = (TextView) itemView.findViewById(R.id.txt_garden_name);
            mTxtGardenAddress = (TextView) itemView.findViewById(R.id.txt_garden_address);
            mLnlGardenRoot = (LinearLayout) itemView.findViewById(R.id.lnl_garden_root);
            mTxtDeleteGarden = (TextView) itemView.findViewById(R.id.txt_delete_garden);
            mTxtUpdateGarden = (TextView) itemView.findViewById(R.id.txt_update_garden);

        }
    }
}
