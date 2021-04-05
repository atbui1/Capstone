package com.example.democ.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.model.ImageVegetable;
import com.example.democ.model.VegetableData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VegetableAdapter extends RecyclerView.Adapter<VegetableAdapter.ViewHolder> {

    ArrayList<VegetableData> mVegetableList;
    Context mContext;
    private OnClickListener mOnClickListener;
    ArrayList<ImageVegetable> mImageList;

    public VegetableAdapter(ArrayList<VegetableData> mVegetableList, Context mContext) {
        this.mVegetableList = mVegetableList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VegetableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_vegetable, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetableAdapter.ViewHolder holder, final int position) {
        System.out.println("------------------- **************** --------------------------");
        System.out.println("bat dau chay VegetableAdapter");
        mImageList = new ArrayList<>();
        mImageList = (ArrayList<ImageVegetable>) mVegetableList.get(position).getImageVegetables();
        try {
            if (mImageList.size() > 0) {
                System.out.println("co img VegetableAdapter");
                if (mImageList.get(0).getUrl() != "") {
                    System.out.println("******************************");
                    System.out.println(mImageList.get(0).getUrl());
                    System.out.println("******************************");
//                    Picasso.Builder builder = new Picasso.Builder(mContext);
//                    builder.build().load("http://" + mImageList.get(0).getUrl())
//                            .placeholder(R.drawable.ic_launcher_background)
//                            .error(R.drawable.caybacha)
//                            .fit()
//                            .centerInside()
//                            .into(holder.mImgVegetable);
                    Picasso.with(mContext).load(mImageList.get(0).getUrl())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.caybacha)
                            .into(holder.mImgVegetable);
                    System.out.println("-----------------***************-------------------------");
                    System.out.println(mImageList.get(0).getUrl());
                    System.out.println("http://" + mImageList.get(0).getUrl());
                    System.out.println("-----------------***************-------------------------");
                } else {
                    holder.mImgVegetable.setImageResource(R.drawable.caybacha);
                }
////            Picasso.Builder builder = new Picasso.Builder(mContext);
////            builder.build().load("http://" + mVegetableList.get(position).getImageVegetables().get(position).getThumbnail())
////                    .placeholder(R.drawable.ic_launcher_background)
////                    .error(R.drawable.ic_launcher_background)
////                    .fit()
////                    .centerInside()
////                    .into(holder.mImgVegetable);
            } else {
                System.out.println("khong co img VegetableAdapter");
            }
        } catch (Exception ex) {

        }
//        Picasso.with(mContext).load(mVegetableList.get(position).getIdImage()).into(holder.mImgVegetable);
        holder.mTxtVegetableName.setText(mVegetableList.get(position).getName());
        holder.mTxtVegetableDescription.setText(mVegetableList.get(position).getDescription());
        holder.mTxtVegetableFeature.setText(mVegetableList.get(position).getFeature());

        //onclick
        holder.mLnlRootVegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = (mVegetableList != null) ? mVegetableList.size() : 0;
        return count;
    }

    //onclick interface
    public interface OnClickListener {
        void onClickListener(int position);
    }

    public void getPosition(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImgVegetable;
        TextView mTxtVegetableName, mTxtVegetableDescription, mTxtVegetableFeature;
        LinearLayout mLnlRootVegetable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgVegetable = (ImageView) itemView.findViewById(R.id.img_vegetable);
            mTxtVegetableName = (TextView) itemView.findViewById(R.id.txt_vegetable_name);
            mTxtVegetableDescription = (TextView) itemView.findViewById(R.id.txt_vegetable_description);
            mTxtVegetableFeature = (TextView) itemView.findViewById(R.id.txt_vegetable_feature);
            mLnlRootVegetable = (LinearLayout) itemView.findViewById(R.id.lnl_root_vegetable);
        }
    }
}
