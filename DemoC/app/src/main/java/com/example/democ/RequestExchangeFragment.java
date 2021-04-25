package com.example.democ;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.democ.adapter.RequestExchangeAdapter;
import com.example.democ.iclick.IClickExChange;
import com.example.democ.model.ExchangeData;
import com.example.democ.presenters.AllExchangePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.ReplyExchangeRequestPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.AllExchangeView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.ReplyExchangeRequestView;

import java.util.ArrayList;
import java.util.List;

public class RequestExchangeFragment extends Fragment implements  IClickExChange, AllExchangeView, PersonalView, ReplyExchangeRequestView,
        SwipeRefreshLayout.OnRefreshListener{

    private View mView;
    private RecyclerView mRecyclerViewRequestExchange;
    private RequestExchangeAdapter mRequestExchangeAdapter;
    private ArrayList<ExchangeData> mListExchange;

    private String mAccessToken;
    private AllExchangePresenter mAllExchangePresenter;
    private PersonalPresenter mPersonalPresenter;
    private ReplyExchangeRequestPresenter mReplyExchangeRequestPresenter;

    private static int mIntPosition = 0;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_request_exchange, container, false);
        initialView();
        initialData();
        return mView;
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getActivity().getApplication(), this);
        mPersonalPresenter.getInfoPersonal();

        mRecyclerViewRequestExchange = (RecyclerView) mView.findViewById(R.id.recycler_request_exchange);
        mRecyclerViewRequestExchange.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewRequestExchange.setLayoutManager(layoutManager);

        mAllExchangePresenter = new AllExchangePresenter(getActivity().getApplication(), getActivity(), this);
        mReplyExchangeRequestPresenter = new ReplyExchangeRequestPresenter(getActivity().getApplication(), getActivity(), this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.sick_green));
    }

    private void initialData() {

//        updateUI();
    }

    private void updateUI() {
        if (mRequestExchangeAdapter == null) {
            mRequestExchangeAdapter = new RequestExchangeAdapter(mListExchange, getContext().getApplicationContext(), this);
            mRequestExchangeAdapter.setData(mListExchange);
            mRecyclerViewRequestExchange.setAdapter(mRequestExchangeAdapter);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAA");
        } else {
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            mRequestExchangeAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void clickExchangeAccept(ExchangeData exchangeData, int positionClick) {
        String exchangeId = exchangeData.getId();
        mIntPosition = positionClick;
        mReplyExchangeRequestPresenter.replyExchangeRequest(exchangeId, 2, mAccessToken);

    }

    @Override
    public void clickExchangeRemove(ExchangeData exchangeData, int positionClick) {
        String exchangeId = exchangeData.getId();
        mIntPosition = positionClick;
        mReplyExchangeRequestPresenter.replyExchangeRequest(exchangeId, 3, mAccessToken);
    }

    @Override
    public void allExchangeSuccess(List<ExchangeData> exchangeData) {
        mListExchange = (ArrayList<ExchangeData>) exchangeData;
        System.out.println("VVVVVVVVVVVVVVVV        FFFFFFFFFFFFFFF     VVVVVVVVVVVVVVVVVVVVV");
        System.out.println("size: " +mListExchange.size());
        updateUI();
        System.out.println("VVVVVVVVVVVVVVVV        FFFFFFFFFFFFFFF     VVVVVVVVVVVVVVVVVVVVV");
    }

    @Override
    public void allExchangeFail() {

    }

    @Override
    public void showInfoPersonal(User user) {
        mAccessToken = user.getToken();
        mAllExchangePresenter.getAllExchange(user.getToken());
    }

    @Override
    public void onRefresh() {
        mRequestExchangeAdapter.setData(mListExchange);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
        System.out.println("refresh request ***********************");
        System.out.println("list size: " + mListExchange.size());
        System.out.println("refresh request ***********************");
    }

    @Override
    public void replyExchangeRequestSuccess() {
        mListExchange.remove(mIntPosition);
        mRequestExchangeAdapter.notifyItemRemoved(mIntPosition);
    }

    @Override
    public void replyExchangeRequestFail() {

    }
}
