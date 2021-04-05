package com.example.democ;


import android.os.Bundle;
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
import com.example.democ.presenters.IsAcceptExchangePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.AllExchangeView;
import com.example.democ.views.DeleteExchangeRequestView;
import com.example.democ.views.IsAcceptExchangeView;
import com.example.democ.views.PersonalView;

import java.util.ArrayList;
import java.util.List;


public class RequestExchangeFragment extends Fragment implements  IClickExChange, AllExchangeView,
        IsAcceptExchangeView, DeleteExchangeRequestView {

    private View mView;
    private RecyclerView mRecyclerViewRequestExchange;
    private RequestExchangeAdapter mRequestExchangeAdapter;
    private ArrayList<ExchangeData> mListExchange;

    private String mAccessToken;
    private ArrayList<String> mListExchangeId;
    private IsAcceptExchangePresenter mIsAcceptExchangePresenter;
    private AllExchangePresenter mAllExchangePresenter;

    private static int mIntPositionClick;

    public RequestExchangeFragment(ArrayList<ExchangeData> mListExchange, String mAccessToken) {
        this.mListExchange = mListExchange;
        this.mAccessToken = mAccessToken;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_request_exchange, container, false);
        initialView();
        initialData();
        return mView;
    }

    private void initialView() {
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("list all exxchange: " + mListExchange.size());
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        mRecyclerViewRequestExchange = (RecyclerView) mView.findViewById(R.id.recycler_request_exchange);
        mRecyclerViewRequestExchange.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewRequestExchange.setLayoutManager(layoutManager);

        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("token: " + mAccessToken);
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");

        mListExchangeId = new ArrayList<>();
        mIsAcceptExchangePresenter = new IsAcceptExchangePresenter(getActivity().getApplication(), getActivity(), this );
        mAllExchangePresenter = new AllExchangePresenter(getActivity().getApplication(), getActivity(), this);

    }

    private void initialData() {

        updateUI();
    }

    private void updateUI() {
        if (mRequestExchangeAdapter == null) {
            mRequestExchangeAdapter = new RequestExchangeAdapter(mListExchange, getContext().getApplicationContext(), this);
            mRecyclerViewRequestExchange.setAdapter(mRequestExchangeAdapter);
        } else {
            mRequestExchangeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void isAcceptExchangeSuccess() {
        System.out.println("VVVVVVVVVVVVVVVVV   SSSSSSSSSSSSSSSSSSSSS  VVVVVVVVVVVVVVVVVV");
        mAllExchangePresenter.getAllExchange(mAccessToken);
        System.out.println("VVVVVVVVVVVVVVVVV   SSSSSSSSSSSSSSSSSSSSS  VVVVVVVVVVVVVVVVVV");
    }

    @Override
    public void isAcceptExchangeFail() {
        System.out.println("VVVVVVVVVVVVVVVV        FFFFFFFFFFFFFFF     VVVVVVVVVVVVVVVVVVVVV");
        System.out.println("VVVVVVVVVVVVVVVV        FFFFFFFFFFFFFFF     VVVVVVVVVVVVVVVVVVVVV");
    }

    @Override
    public void clickExchange(ExchangeData exchangeData, int positionClick) {
        //SSSSSSSSSSSSSSS
        String exchangeId = exchangeData.getId();
        mListExchangeId = new ArrayList<>();
        mListExchangeId.add(exchangeId);

        mIntPositionClick = positionClick;
        //SSSSSSSSSSSSSSS
        int status = 2;
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        System.out.println("token: " + mAccessToken);
        System.out.println("status: " + status);
        System.out.println("id: " + exchangeId);
        System.out.println("mlistExchangeID: " + mListExchangeId);
        System.out.println("vi tri click: " + mIntPositionClick);
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        mIsAcceptExchangePresenter.isAcceptExchange(status, mListExchangeId, mAccessToken);
    }

    @Override
    public void allExchangeSuccess(List<ExchangeData> exchangeData) {
//        mListExchange = (ArrayList<ExchangeData>) exchangeData;
//        mListExchange.remove(mIntPositionClick);
        mRequestExchangeAdapter.notifyItemRemoved(mIntPositionClick);

    }

    @Override
    public void allExchangeFail() {

    }

    @Override
    public void deleteExchangeRequestSuccess() {

    }

    @Override
    public void deleteExchangeRequestFail() {

    }
}
