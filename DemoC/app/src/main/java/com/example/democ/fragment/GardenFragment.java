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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GardenFragment extends Fragment implements View.OnClickListener {

    private View mView;
    //    Floating action
    private FloatingActionButton mFabAddGarden;
    private RecyclerView mRecyclerGarden;
    private ArrayList<GardenResult> mGardenList;
    private GardenAdapter mGardenAdapter;
    private String mAccessToken;

    public GardenFragment(ArrayList<GardenResult> mGardenList, String mAccessToken) {
        this.mGardenList = mGardenList;
        this.mAccessToken = mAccessToken;
    }

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
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
//                LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerGarden.setLayoutManager(layoutManager);

        System.out.println("9999999999999999999999999999999999999999999999999999999");
        System.out.println("9999999999999999999999999999999999999999999999999999999");
        System.out.println(mAccessToken);
        System.out.println("9999999999999999999999999999999999999999999999999999999");
        System.out.println("9999999999999999999999999999999999999999999999999999999");
    }

    private void initialData() {
//        mGardenList = new ArrayList<>();
//        mGardenList.add(new Garden("vuon 1", "Quang trung"));
//        mGardenList.add(new Garden("vuon 2", "Quan 2"));
//        mGardenList.add(new Garden("vuon 3", "Quan 3"));
//        mGardenList.add(new Garden("vuon 4", "Quan 4"));
//        mGardenList.add(new Garden("vuon 5", "Quan 5"));

        updateUI();
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
}
