package com.example.democ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.iclick.IClickExChange;
import com.example.democ.model.ExchangeData;
import com.example.democ.room.managements.UserManagement;

import java.util.ArrayList;

public class RequestExchangeAdapter extends RecyclerView.Adapter<RequestExchangeAdapter.ViewHolder> {

    private ArrayList<ExchangeData> mListExchange;
    private Context mContext;
    private IClickExChange mIClickExChange;

//    public RequestExchangeAdapter(ArrayList<ExchangeData> mListExchange, Context mContext) {
//        this.mListExchange = mListExchange;
//        this.mContext = mContext;
//    }


    public RequestExchangeAdapter(ArrayList<ExchangeData> mListExchange, Context mContext, IClickExChange mIClickExChange) {
        this.mListExchange = mListExchange;
        this.mContext = mContext;
        this.mIClickExChange = mIClickExChange;
    }

    @NonNull
    @Override
    public RequestExchangeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_request_exchange, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestExchangeAdapter.ViewHolder holder, final int position) {
        final ExchangeData exchangeData = mListExchange.get(position);
        if (exchangeData == null) {
            return;
        }
        holder.mTxtRequestName.setText("Bạn " + position);
        holder.mTxtRequestContent.setText("Muốn nhận " + mListExchange.get(position).getQuantity()
                + " " + mListExchange.get(position).getVegNameSend() + " từ bạn");

        holder.mLnlImgRequestAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mIClickExChange.clickExchange(exchangeData);

//                mListExchange.remove(position);
                System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                System.out.println("size: " + mListExchange.size());
                System.out.println("abcd: " + position);
                System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                mIClickExChange.clickExchange(exchangeData, position);
                mListExchange.remove(position);
                System.out.println("size abcd: " + mListExchange.size());
                System.out.println("DDDDDDDDDGGGGGGGGGGGGGGGGGGGG");

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListExchange != null) {
            return mListExchange.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtRequestName, mTxtRequestContent;
        LinearLayout mLnlImgRequestAgree, mLnlImageRequestRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtRequestName = (TextView) itemView.findViewById(R.id.txt_request_name);
            mTxtRequestContent = (TextView) itemView.findViewById(R.id.txt_request_content);
            mLnlImgRequestAgree = (LinearLayout) itemView.findViewById(R.id.lnl_img_request_agree);
            mLnlImageRequestRemove = (LinearLayout) itemView.findViewById(R.id.lnl_img_request_remove);
        }
    }
}
