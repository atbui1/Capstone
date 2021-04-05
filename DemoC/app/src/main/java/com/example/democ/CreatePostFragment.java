package com.example.democ;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.democ.adapter.GardenAdapter;
import com.example.democ.model.GardenResult;

import java.util.ArrayList;
import java.util.List;

public class CreatePostFragment extends Fragment implements View.OnClickListener {
   private View mView;
   private Button mBtnCreatePost;
   private EditText mEdtPostContent, mEdtPostVegetableQuantity;
   private TextView mTxtPostVegetableName, mTxtPostGarden;

   private String mPostContent, mPostVegetableName, mPostVegetableQuantity, mPostGarden;
   private ArrayList<GardenResult> mGardenList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_create_post, container, false);
        initialView();
        initialData();
        return mView;
    }

    private void initialView() {

        mBtnCreatePost = (Button) mView.findViewById(R.id.btn_create_post);
        mBtnCreatePost.setOnClickListener((View.OnClickListener) this);
        mEdtPostContent = (EditText) mView.findViewById(R.id.edt_post_content);
        mEdtPostVegetableQuantity = (EditText) mView.findViewById(R.id.edt_post_vegetable_quantity);
        mTxtPostVegetableName = (TextView) mView.findViewById(R.id.txt_post_vegetable_name);
        mTxtPostVegetableName.setOnClickListener(this);
        mTxtPostGarden = (TextView) mView.findViewById(R.id.txt_post_garden);
        mTxtPostGarden.setOnClickListener(this);

        mGardenList = new ArrayList<>();

    }

    private void initialData() {
        mPostContent = mEdtPostContent.getText().toString();
        mPostVegetableQuantity = mEdtPostVegetableQuantity.getText().toString();
        mPostGarden = mTxtPostGarden.getText().toString();
        mPostVegetableName = mTxtPostVegetableName.getText().toString();
    }

    public void showGardenDialog(Context context, ArrayList<GardenResult> gardenResults) {
        final Dialog dialog = new Dialog(context);
        GardenAdapter gardenAdapter = new GardenAdapter(gardenResults, context);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_login_fail);

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_post:
                Toast.makeText(getActivity().getApplicationContext(), "btn create post", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_post_garden:
                Toast.makeText(getActivity().getApplicationContext(), "toi activity garden", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity().getApplicationContext(), AddImagePostActivity.class);
//                startActivity(intent);
                showGardenDialog(getActivity().getApplicationContext(), mGardenList);;
                break;
            case R.id.txt_post_vegetable_name:
                if (mPostGarden != null) {
                    Toast.makeText(getActivity().getApplicationContext(), "phai chon vuon rau", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "toi vuon rau", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
