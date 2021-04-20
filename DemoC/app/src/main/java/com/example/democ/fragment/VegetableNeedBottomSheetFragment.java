package com.example.democ.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.adapter.VegetableNeedPostAdapter;
import com.example.democ.iclick.IClickVegetableNeed;
import com.example.democ.model.VegetableNeedAll;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class VegetableNeedBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private List<VegetableNeedAll> mListVegetable;
    private IClickVegetableNeed iClickVegetableNeed;
    private VegetableNeedPostAdapter mVegetableNeedPostAdapter;
    private BottomSheetDialog mBottomSheetDialog;
    private LinearLayout mLnlSelect, mLnlDelete;

    private List<VegetableNeedAll> mListVegetableNeedSelected;

    //aaa
    IVegetableNeedListener mIVegetableNeedListener;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_vegetable_need_delete:
//                mVegetableNeedPostAdapter.deleteSelected();
//                mBottomSheetDialog.dismiss();
                break;
        }
    }

    public interface IVegetableNeedListener {
        void getVegetableNeed(List<VegetableNeedAll> vegetableNeedAll);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIVegetableNeedListener = (IVegetableNeedListener) getActivity();
    }
    //aaa

    public VegetableNeedBottomSheetFragment(List<VegetableNeedAll> mListVegetable) {
        this.mListVegetable = mListVegetable;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_vegetable_need_bottom_sheet, null);
        mBottomSheetDialog.setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_vegetable_need_bottom_sheet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        final BottomSheetDialog finalBottomSheetDialog = mBottomSheetDialog;
        mVegetableNeedPostAdapter = new VegetableNeedPostAdapter((ArrayList<VegetableNeedAll>) mListVegetable, new IClickVegetableNeed() {
            @Override
            public void clickVegetableNeed(VegetableNeedAll vegetableNeedAll) {
                iClickVegetableNeed.clickVegetableNeed(vegetableNeedAll);
                finalBottomSheetDialog.dismiss();
            }
        });
        recyclerView.setAdapter(mVegetableNeedPostAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        mLnlSelect = (LinearLayout) view.findViewById(R.id.lnl_vegetable_need_selected);
        mLnlDelete = (LinearLayout) view.findViewById(R.id.lnl_vegetable_need_delete);
        mLnlDelete.setOnClickListener(this);

        //aaa
        mLnlSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVegetableNeed();
            }
        });
        //aaa

        return mBottomSheetDialog;
    }

    private void sendVegetableNeed() {
        System.out.println("VegetableNeedBottomSheetFragment AAAAAAAAAAAAAAAAAAAAA");
        String vegetableName = null, vegetableId = null;
        VegetableNeedAll vegetableNeedAll = null;
        mListVegetableNeedSelected = new ArrayList<>();
        if (mVegetableNeedPostAdapter.getSelected().size() > 0) {
            for (int i = 0; i < mVegetableNeedPostAdapter.getSelected().size(); i++) {
                vegetableId = mVegetableNeedPostAdapter.getSelected().get(i).getId();
                vegetableName = mVegetableNeedPostAdapter.getSelected().get(i).getText();
                vegetableNeedAll = new VegetableNeedAll(vegetableId, vegetableName);
                mListVegetableNeedSelected.add(vegetableNeedAll);
            }
            System.out.println("sendVegetableNeed VegetableNeedBottomSheetFragment");
            System.out.println("chay vao if");
            System.out.println("sendVegetableNeed VegetableNeedBottomSheetFragment");
        } else {
            System.out.println("sendVegetableNeed VegetableNeedBottomSheetFragment");
            System.out.println("chay vao else");
            System.out.println("sendVegetableNeed VegetableNeedBottomSheetFragment");
        }
        System.out.println("666666666666666666666666666666666666666666666");
        System.out.println("so luong trong mListVegetableNeedSelected: " + mListVegetableNeedSelected.size());
        for (int a = 0; a < mListVegetableNeedSelected.size(); a ++) {
            System.out.println("id: " + mListVegetableNeedSelected.get(a).getId());
            System.out.println("name: " + mListVegetableNeedSelected.get(a).getText());
        }
        System.out.println("77777777777777777   mListVegetable  7777777777777777777777");
        System.out.println("VegetableNeedBottomSheetFragment AAAAAAAAAAAAAAAAAAAAA");

        mIVegetableNeedListener.getVegetableNeed(mListVegetableNeedSelected);

        mBottomSheetDialog.dismiss();
    }
}
