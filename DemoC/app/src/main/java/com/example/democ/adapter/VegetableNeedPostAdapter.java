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
import com.example.democ.iclick.IClickVegetableNeed;
import com.example.democ.model.VegetableNeedAll;

import java.util.ArrayList;

public class VegetableNeedPostAdapter extends RecyclerView.Adapter<VegetableNeedPostAdapter.ViewHolder> {
    private ArrayList<VegetableNeedAll> mListVegetable;
    private IClickVegetableNeed iClickVegetableNeed;
    private Context mContext;

    public VegetableNeedPostAdapter(ArrayList<VegetableNeedAll> mListVegetable, IClickVegetableNeed iClickVegetableNeed) {
        this.mListVegetable = mListVegetable;
        this.iClickVegetableNeed = iClickVegetableNeed;
    }

    public VegetableNeedPostAdapter(ArrayList<VegetableNeedAll> mListVegetable, IClickVegetableNeed iClickVegetableNeed, Context mContext) {
        this.mListVegetable = mListVegetable;
        this.iClickVegetableNeed = iClickVegetableNeed;
        this.mContext = mContext;
    }

    public void setmListVegetable(ArrayList<VegetableNeedAll> mListVegetable) {
        this.mListVegetable = new ArrayList<>();
        this.mListVegetable = mListVegetable;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VegetableNeedPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_vegetable_need_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetableNeedPostAdapter.ViewHolder holder, int position) {
        final VegetableNeedAll vegetableNeedAll = mListVegetable.get(position);
        if (vegetableNeedAll == null) {
            return;
        }
        holder.mTxtVegetableName.setText(mListVegetable.get(position).getText());

        holder.bind(mListVegetable.get(position));
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
        LinearLayout mLnlRoot;
        ImageView mImgChecked;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtVegetableName = (TextView) itemView.findViewById(R.id.txt_vegetable_name);
            mLnlRoot = (LinearLayout) itemView.findViewById(R.id.lnl_root);
            mImgChecked = (ImageView) itemView.findViewById(R.id.img_checked);
        }
//        getting the select item
        void bind(final VegetableNeedAll vegetableNeedAll) {
            mImgChecked.setVisibility(vegetableNeedAll.isChecked() ? View.VISIBLE : View.GONE);
            mLnlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vegetableNeedAll.setChecked(!vegetableNeedAll.isChecked());
                    mImgChecked.setVisibility(vegetableNeedAll.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }

    }
//    getting all items selected
    public ArrayList<VegetableNeedAll> getAllSelected() {
        return mListVegetable;
    }

//    getting select when btn click
    public ArrayList<VegetableNeedAll> getSelected() {
        ArrayList<VegetableNeedAll> selectedList = new ArrayList<>();
        for (int i = 0; i < mListVegetable.size(); i++) {
            if (mListVegetable.get(i).isChecked()) {
                selectedList.add(mListVegetable.get(i));
            }
        }
        return selectedList;
    }

}
