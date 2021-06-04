package com.example.democ.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.activity.CreateGardenActivity;
import com.example.democ.activity.GardenActivity;
import com.example.democ.activity.UpdateAccountActivity;
import com.example.democ.activity.UpdateGardenActivity;
import com.example.democ.adapter.GardenAdapter;
import com.example.democ.iclick.IClickGardenFull;
import com.example.democ.model.GardenResult;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.presenters.DeleteGardenPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.AllGardenView;
import com.example.democ.views.DeleteGardenView;
import com.example.democ.views.PersonalView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GardenFragment extends Fragment implements View.OnClickListener, AllGardenView, PersonalView,
        IClickGardenFull, DeleteGardenView {

    private final static String KEY_GARDEN_SEND_UPDATE = "KEY_GARDEN_SEND_UPDATE";
    private final static String KEY_GARDEN_INFO = "KEY_GARDEN_INFO";

    private View mView;
    //    Floating action
    private FloatingActionButton mFabAddGarden;
    private RecyclerView mRecyclerGarden;
    private ArrayList<GardenResult> mGardenList;
    private GardenAdapter mGardenAdapter;
    private PersonalPresenter mPersonalPresenter;
    private AllGardenPresenter mAllGardenPresenter;
    private DeleteGardenPresenter mDeleteGardenPresenter;
    private User mUser;
    private int mIntPosition = 0;

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
        mDeleteGardenPresenter = new DeleteGardenPresenter(getActivity().getApplication(), getActivity(), this);
//        updateUI();
    }

    public void updateUI() {
        if (mGardenAdapter == null) {
            mGardenAdapter = new GardenAdapter((ArrayList<GardenResult>) mGardenList,
                    getContext().getApplicationContext(), this);
            mRecyclerGarden.setAdapter(mGardenAdapter);
        } else {
            mGardenAdapter.notifyDataSetChanged();
        }

    }

    private void clickOpenCreateGarden() {
        Intent intent = new Intent(getContext().getApplicationContext(), CreateGardenActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    @Override
    public void clickGarden(GardenResult gardenResult) {

        Intent intentGarden = new Intent(getContext(), GardenActivity.class);
//        intentGarden.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intentGarden.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_GARDEN_INFO, gardenResult);
        intentGarden.putExtras(bundle);
        startActivity(intentGarden);
    }

    @Override
    public void clickGardenDelete(GardenResult gardenResult, int position) {
        mIntPosition = position;
        showDialogDeleteGarden(gardenResult.getId());
    }

    public void showDialogDeleteGarden(final int gardenId) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_delete_garden);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        Button mBtnYes, mBtnNo;
        mBtnYes = (Button) dialog.findViewById(R.id.btn_delete_yes);
        mBtnNo = (Button) dialog.findViewById(R.id.btn_delete_no);
        mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGarden(gardenId);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void deleteGarden(int gardenId) {

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("token delete garden: " + mUser.getToken());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        mDeleteGardenPresenter.deleteGarden(gardenId, mUser.getToken());
    }
    @Override
    public void clickGardenUpdate(GardenResult gardenResult) {

        Intent intent = new Intent(getContext(), UpdateGardenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_GARDEN_SEND_UPDATE, gardenResult);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void deleteGardenSuccess() {
        mGardenList.remove(mIntPosition);
        mGardenAdapter.notifyItemRemoved(mIntPosition);
    }

    @Override
    public void deleteGardenFail() {
        showDialogDeleteGardenErr();
    }
    private void showDialogDeleteGardenErr() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Vui lòng xóa cây hiện có trong vườn trước");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
