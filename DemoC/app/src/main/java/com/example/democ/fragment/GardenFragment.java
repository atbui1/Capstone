package com.example.democ.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.activity.CreateGardenActivity;
import com.example.democ.activity.UpdateAccountActivity;
import com.example.democ.adapter.GardenAdapter;
import com.example.democ.model.GardenResult;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.AllGardenView;
import com.example.democ.views.PersonalView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GardenFragment extends Fragment implements View.OnClickListener, AllGardenView, PersonalView {

    private View mView;
    //    Floating action
    private FloatingActionButton mFabAddGarden;
    private RecyclerView mRecyclerGarden;
    private ArrayList<GardenResult> mGardenList;
    private GardenAdapter mGardenAdapter;
    private PersonalPresenter mPersonalPresenter;
    private AllGardenPresenter mAllGardenPresenter;
    private User mUser;

//    public GardenFragment(ArrayList<GardenResult> mGardenList, String mAccessToken) {
//        this.mGardenList = mGardenList;
//        this.mAccessToken = mAccessToken;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_garden, container, false);
        initialView();
        initialData();

        return  mView;
    }

    private void initialView() {
        mFabAddGarden = (FloatingActionButton) mView.findViewById(R.id.fab_add_garden);
        mFabAddGarden.setOnClickListener(this);

        mRecyclerGarden = (RecyclerView) mView.findViewById(R.id.recycler_garden);
        mRecyclerGarden.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerGarden.setLayoutManager(layoutManager);

    }

    private void initialData() {

        mPersonalPresenter = new PersonalPresenter(getActivity().getApplication(), this);
        mPersonalPresenter.getInfoPersonal();
        mAllGardenPresenter = new AllGardenPresenter(getActivity().getApplication(), getActivity(), this);
//        updateUI();
    }

    public void updateUI() {
        if (mGardenAdapter == null) {
            mGardenAdapter = new GardenAdapter((ArrayList<GardenResult>) mGardenList,
                    getContext().getApplicationContext());
            mRecyclerGarden.setAdapter(mGardenAdapter);
        } else {
            mGardenAdapter.notifyDataSetChanged();
        }

    }

    private void clickOpenCreateGarden() {
        Intent intent = new Intent(getContext().getApplicationContext(), CreateGardenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab_add_garden:
                clickOpenCreateGarden();
                break;
        }
    }

    @Override
    public void getAllGardenSuccess(List<GardenResult> listAllGarden) {
        mGardenList = (ArrayList<GardenResult>) listAllGarden;
        updateUI();
    }

    @Override
    public void getAllGardenFail() {

    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mAllGardenPresenter.getAllGarden(user.getToken());
    }
}
