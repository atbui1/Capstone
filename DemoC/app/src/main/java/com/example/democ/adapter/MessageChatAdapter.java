package com.example.democ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.iclick.IClickMessage;
import com.example.democ.model.MessageChat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.ViewHolder> {
    ArrayList<MessageChat> mMessagesList;
    Context context;
    private IClickMessage iClickMessage;
    int count = 0;

    public MessageChatAdapter(ArrayList<MessageChat> mMessagesList, Context context, IClickMessage iClickMessage) {
        this.mMessagesList = mMessagesList;
        this.context = context;
        this.iClickMessage = iClickMessage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_message, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final MessageChat messageChat = mMessagesList.get(position);
        Picasso.with(context).load(mMessagesList.get(position).getImageUserMessage()).into(holder.mImgMessageUser);
        holder.mTxtMessageUserName.setText(mMessagesList.get(position).getUsernameMessage());
        holder.mTxtMessageTime.setText(mMessagesList.get(position).getTimeMessage());
        holder.mLnlMessageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickMessage.clickMessage(messageChat);
            }
        });
        holder.mLnlMessageImageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count +1;
                if (count%2 == 0) {
                    holder.mImgMessageDelete.setImageResource(R.drawable.likefasle);
                } else {
                    holder.mImgMessageDelete.setImageResource(R.drawable.liketrue);
                }
                iClickMessage.clickDelete(position, count);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImgMessageUser, mImgMessageDelete;
        TextView mTxtMessageUserName, mTxtMessageTime;
        LinearLayout mLnlMessageLeft, mLnlMessageImageDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgMessageUser = (ImageView) itemView.findViewById(R.id.img_message_user);
            mImgMessageDelete = (ImageView) itemView.findViewById(R.id.img_message_delete);
            mTxtMessageUserName = (TextView) itemView.findViewById(R.id.txt_message_username);
            mTxtMessageTime = (TextView) itemView.findViewById(R.id.txt_message_time);
            mLnlMessageLeft = (LinearLayout) itemView.findViewById(R.id.lnl_message_left);
            mLnlMessageImageDelete = (LinearLayout) itemView.findViewById(R.id.lnl_message_image_delete);


        }

    }
}
