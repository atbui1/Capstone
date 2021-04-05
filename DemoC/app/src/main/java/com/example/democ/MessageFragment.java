package com.example.democ;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.adapter.MessageChatAdapter;
import com.example.democ.iclick.IClickMessage;
import com.example.democ.model.MessageChat;

import java.util.ArrayList;

public class MessageFragment extends Fragment implements IClickMessage {

    private View mView;
    private RecyclerView mRecyclerMessage;
    private ArrayList<MessageChat> mMessageList;
    private MessageChatAdapter mMessageChatAdapter;
    private int stt = 0;
    private ImageView imgDelete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_message, container, false);

        initialView();
        initialData();
        return mView;
    }

    private void initialView() {
        mRecyclerMessage = (RecyclerView) mView.findViewById(R.id.recycler_message);
        mRecyclerMessage.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
//                LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerMessage.setLayoutManager(layoutManager);

        imgDelete = (ImageView) mView.findViewById(R.id.img_message_delete);

    }

    private void initialData() {

        mMessageList = new ArrayList<>();
        mMessageList.add(new MessageChat(
                "https://mtrend.vn/wp-content/uploads/2019/05/anh-co-trang-trung-quoc-33.jpg",
                "Thanh Long", "14-01-2021"));
        mMessageList.add(new MessageChat(
                "https://mtrend.vn/wp-content/uploads/2019/05/anh-co-trang-trung-quoc-34.jpg",
                "Bạch Hổ", "10-01-2021"));
        mMessageList.add(new MessageChat(
                "https://mtrend.vn/wp-content/uploads/2019/05/anh-co-trang-trung-quoc-33.jpg",
                "Chu Tước", "09-01-2021"));
        mMessageList.add(new MessageChat(
                "https://mtrend.vn/wp-content/uploads/2019/05/anh-co-trang-trung-quoc-27.jpg",
                "Huyền Quy", "12-12-2020"));
        mMessageList.add(new MessageChat(
                "https://mtrend.vn/wp-content/uploads/2019/05/anh-co-trang-trung-quoc-22.jpg",
                "Phượng Hoàng", "11-11-2020"));
        mMessageList.add(new MessageChat(
                "https://2sao.vietnamnetjsc.vn/images/2019/06/11/15/11/my-nhan-co-trang-17.jpg",
                "Kỳ Lân", "10-10-2020"));
        mMessageList.add(new MessageChat(
                "https://2sao.vietnamnetjsc.vn/images/2019/06/11/15/11/my-nhan-co-trang-17.jpg",
                "Kỳ Lân", "10-10-2020"));

        // goi ham update ui
        updateUI();
    }

    //update ui
    public void updateUI() {
        if (mMessageChatAdapter == null) {
            mMessageChatAdapter = new MessageChatAdapter((ArrayList<MessageChat>) mMessageList, mView.getContext(),
                    (IClickMessage) this);
            mRecyclerMessage.setAdapter(mMessageChatAdapter);
//            mMessageChatAdapter.getPosition(new MessageChatAdapter.OnClickListener() {
//                @Override
//                public void onClickListener(int position) {
//                    Toast.makeText(mView.getContext(), "AAAAAAAA", Toast.LENGTH_SHORT).show();
//                }
//            });
        } else {
            mMessageChatAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void clickMessage(MessageChat messageChat) {
        Toast.makeText(mView.getContext(), messageChat.getUsernameMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clickDelete(int pos, int count) {
        stt += 1;
        if (count % 2 == 0) {
            Toast.makeText(mView.getContext(), "hien so lan: " + count + "\n like 222222222222"
                    + "\n position: " + pos, Toast.LENGTH_SHORT).show();
//            imgDelete.setImageResource(R.drawable.likefasle);
        } else {
            Toast.makeText(mView.getContext(), "hien so lan: " + count + "\n like 1111111111111"
                    + "\n position: " + pos, Toast.LENGTH_SHORT).show();
//            imgDelete.setImageResource(R.drawable.liketrue);
        }
    }
}
