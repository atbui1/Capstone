package com.example.democ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.iclick.IClickExChange;
import com.example.democ.model.ExchangeData;

import java.util.List;

public class HistoryExchangeAdapter extends RecyclerView.Adapter<HistoryExchangeAdapter.ViewHolder> {
    List<ExchangeData> mListHistory;
    Context mContext;
    IClickExChange mIClickExChange;

    public HistoryExchangeAdapter(List<ExchangeData> mListHistory, IClickExChange mIClickExChange) {
        this.mListHistory = mListHistory;
        this.mIClickExChange = mIClickExChange;
    }

    public HistoryExchangeAdapter(List<ExchangeData> mListHistory, Context mContext, IClickExChange mIClickExChange) {
        this.mListHistory = mListHistory;
        this.mContext = mContext;
        this.mIClickExChange = mIClickExChange;
    }

    @NonNull
    @Override
    public HistoryExchangeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_history_exchange, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryExchangeAdapter.ViewHolder holder, final int position) {
        final ExchangeData exchangeData = mListHistory.get(position);
        if (exchangeData == null) {
            return;
        }
        holder.mTxtInfoExchange.setText("Bạn " + exchangeData.getFullNameHost() + " đã nhận " + exchangeData.getVegNameReceive());
        if (exchangeData.getStatus() == 1) {
            holder.mTxtExchangeStatus.setText("Trạng thái: Chưa cho, nhận rau");
            holder.mTxtExchangeStatus.setTextColor(ContextCompat.getColor(mContext, R.color.dark_yellow));
        } else if (exchangeData.getStatus() == 2) {
            holder.mTxtExchangeStatus.setText("Trạng thái: đã cho, nhận rau");
            holder.mTxtExchangeStatus.setTextColor(ContextCompat.getColor(mContext, R.color.sick_green));
        }
        holder.mLnlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickExChange.clickExchangeAccept(exchangeData, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListHistory != null) {
            return mListHistory.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLnlRoot;
        TextView mTxtInfoExchange, mTxtExchangeStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLnlRoot = (LinearLayout) itemView.findViewById(R.id.lnl_root);
            mTxtInfoExchange = (TextView) itemView.findViewById(R.id.txt_exchange_info);
            mTxtExchangeStatus = (TextView) itemView.findViewById(R.id.txt_exchange_status);
        }
    }
}
