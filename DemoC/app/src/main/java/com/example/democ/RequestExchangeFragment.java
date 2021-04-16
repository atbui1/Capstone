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

import com.example.democ.adapter.RequestExchangeAdapter;
import com.example.democ.iclick.IClickExChange;
import com.example.democ.model.ExchangeData;
import com.example.democ.presenters.AllExchangePresenter;
import com.example.democ.presenters.DeleteExchangeRequestPresenter;
import com.example.democ.presenters.IsAcceptExchangePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.AllExchangeView;
import com.example.democ.views.DeleteExchangeRequestView;
import com.example.democ.views.IsAcceptExchangeView;
import com.example.democ.views.PersonalView;

import java.util.ArrayList;
import java.util.List;

public class RequestExchangeFragment extends Fragment implements  IClickExChange, AllExchangeView,
        IsAcceptExchangeView, DeleteExchangeRequestView, PersonalView {

    private View mView;
    private RecyclerView mRecyclerViewRequestExchange;
    private RequestExchangeAdapter mRequestExchangeAdapter;
    private ArrayList<ExchangeData> mListExchange;

    private String mAccessToken;
    private IsAcceptExchangePresenter mIsAcceptExchangePresenter;
    private DeleteExchangeRequestPresenter mDeleteExchangeRequestPresenter;
    private AllExchangePresenter mAllExchangePresenter;
    private PersonalPresenter mPersonalPresenter;

    private static int mIntPositionAdmit = 0;
    private static int mIntPositionRemove = 0;

    public RequestExchangeFragment(ArrayList<ExchangeData> mListExchange) {
        this.mListExchange = mListExchange;
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
        mPersonalPresenter = new PersonalPresenter(getActivity().getApplication(), this);
        mPersonalPresenter.getInfoPersonal();

        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("list all exxchange: " + mListExchange.size());
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        mRecyclerViewRequestExchange = (RecyclerView) mView.findViewById(R.id.recycler_request_exchange);
        mRecyclerViewRequestExchange.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewRequestExchange.setLayoutManager(layoutManager);

        mAllExchangePresenter = new AllExchangePresenter(getActivity().getApplication(), getActivity(), this);
        mIsAcceptExchangePresenter = new IsAcceptExchangePresenter(getActivity().getApplication(), getActivity(), this );
        mDeleteExchangeRequestPresenter = new DeleteExchangeRequestPresenter(getActivity().getApplication(), getActivity(), this);

    }

    private void initialData() {

        updateUI();
    }

    private void updateUI() {
        if (mRequestExchangeAdapter == null) {
            mRequestExchangeAdapter = new RequestExchangeAdapter(mListExchange, getContext().getApplicationContext(), this);
            mRequestExchangeAdapter.setData(mListExchange);
            mRecyclerViewRequestExchange.setAdapter(mRequestExchangeAdapter);
        } else {
            mRequestExchangeAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void clickExchangeAccept(ExchangeData exchangeData, int positionClick) {
        //SSSSSSSSSSSSSSS
        String exchangeId = exchangeData.getId();
        mIntPositionAdmit = positionClick;
        //SSSSSSSSSSSSSSS
        int status = 2;

        mIsAcceptExchangePresenter.isAcceptExchange(exchangeId, status, mAccessToken);
    }

    @Override
    public void clickExchangeRemove(ExchangeData exchangeData, int positionClick) {
        String exchangeId = exchangeData.getId();
        mIntPositionRemove = positionClick;
        mDeleteExchangeRequestPresenter.deleteExchangeRequest(exchangeId, mAccessToken);
    }

    @Override
    public void isAcceptExchangeSuccess() {
        mListExchange.remove(mIntPositionAdmit);
        mRequestExchangeAdapter.notifyItemRemoved(mIntPositionAdmit);
    }

    @Override
    public void isAcceptExchangeFail() {
        System.out.println("VVVVVVVVVVVVVVVV        FFFFFFFFFFFFFFF     VVVVVVVVVVVVVVVVVVVVV");
        System.out.println("VVVVVVVVVVVVVVVV        FFFFFFFFFFFFFFF     VVVVVVVVVVVVVVVVVVVVV");
    }

    @Override
    public void deleteExchangeRequestSuccess() {
        mListExchange.remove(mIntPositionRemove);
        mRequestExchangeAdapter.notifyItemRemoved(mIntPositionRemove);
    }

    @Override
    public void deleteExchangeRequestFail() {

    }
    @Override
    public void allExchangeSuccess(List<ExchangeData> exchangeData) {
//        mListExchange = (ArrayList<ExchangeData>) exchangeData;
    }

    @Override
    public void allExchangeFail() {

    }

    @Override
    public void showInfoPersonal(User user) {
        mAccessToken = user.getToken();
    }
}
