package com.example.democ.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.democ.R;
import com.example.democ.activity.CreateGardenActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class EmptyFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private FloatingActionButton mAddGarden;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_empty, container, false);
        initialView();
        initialData();
        return mView;
    }

    private void initialView() {
        mAddGarden = (FloatingActionButton) mView.findViewById(R.id.fab_add_garden_empty);
        mAddGarden.setOnClickListener(this);
    }

    private void initialData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_garden_empty:
                Intent intent = new Intent(getContext().getApplicationContext(), CreateGardenActivity.class);
                startActivity(intent);
                break;
        }
    }
}
