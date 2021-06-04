package com.example.democ.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.fragment.SearchByNameBottomSheetFragment;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.model.GardenResult;
import com.example.democ.model.ImageVegetable;
import com.example.democ.model.VegetableData;
import com.example.democ.presenters.CreateVegetablePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.SearchByNamePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.CreateVegetableView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.SearchByNameView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateVegetableActivity extends AppCompatActivity implements View.OnClickListener, CreateVegetableView,
        SearchByNameView, PersonalView,
        SearchByNameBottomSheetFragment.IGetDataSearchWikiListener,
        SearchByNameBottomSheetFragment.IGetDataSearchWikiTitleListener {

    private final static String KEY_GARDEN_INFO = "KEY_GARDEN_INFO";
    private ImageView mImgCreateVegetable;
    private EditText mEdtVegetableName, mEdtVegetableDescription, mEdtVegetableFeature, mEdtVegetableQuantity, mEdtSearchValue;
    private Button mBtnCreateVegetable;
    private TextView mTxtEditInfoVegetable;
    private LinearLayout mLnlSearch, mLnlBack, mLnlEditInfoVegetable;

    private int mIntGardenId;
    private String mStrGardenName, mStrGardenAddress, mToken;
    private User mUser;
    private PersonalPresenter mPersonalPresenter;
    private CreateVegetablePresenter mCreateVegetablePresenter;
    //list part image
    private List<MultipartBody.Part> mListImagePart;
    private List<ImageVegetable> mListImageVegetable;

    private MultipartBody.Part mRequestImage = null;
    private static final int CAMERA_REQUEST = 0;
    private static int request_code_image = 123;

    //new seacrh
    private String mStrSearchValue = "";
//    private final static String SEARCH_NAME = "Tìm kiếm theo tên";
    private final static String KEY_VEGETABLE_CREATE = "KEY_VEGETABLE_CREATE";
    private ArrayList<VegetableData> mListVegetable;
    private SearchByNamePresenter mSearchByNamePresenter;
    private String mStrNameSearch = "", mStrSynonymOfFeature = "", mStrIdDescription = "", mStrIdDescriptionTmp = "";
    private boolean mBlIsFixed = false;
    private static String URL_SEARCH_NAME = "url_search_name";
    private static String URL_TELEPHONE = "url_telephone";
    private String mMediaPath, mStrLinkUrl = "", mStrUrl = "", mStrFeature = "", mStrFeatureOfLisText = "";
    private SearchByNameBottomSheetFragment mSearchByNameBottomSheetFragment;
    private Dialog mDialogAddVegetable;

    private GardenResult mGardenResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vegetable);

        initialView();
        initialData();
    }

    private void initialView() {

        checkPermission();

        mImgCreateVegetable = (ImageView) findViewById(R.id.img_create_vegetable);
        mImgCreateVegetable.setOnClickListener(this);
        mEdtVegetableName = (EditText) findViewById(R.id.edt_vegetable_name);
        mEdtVegetableDescription = (EditText) findViewById(R.id.edt_vegetable_description);
        mEdtVegetableFeature = (EditText) findViewById(R.id.edt_vegetable_feature);
        mEdtVegetableQuantity = (EditText) findViewById(R.id.edt_vegetable_quantity);
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mBtnCreateVegetable = (Button) findViewById(R.id.btn_create_vegetable);
        mBtnCreateVegetable.setOnClickListener(this);
        mEdtSearchValue = (EditText) findViewById(R.id.edt_search_value);
        mStrSearchValue = mEdtSearchValue.getText().toString().trim();
        mLnlSearch = (LinearLayout) findViewById(R.id.lnl_search);
        mLnlSearch.setOnClickListener(this);

        mLnlEditInfoVegetable = (LinearLayout) findViewById(R.id.lnl_edit_info_vegetable);
        mLnlEditInfoVegetable.setVisibility(View.GONE);
        mTxtEditInfoVegetable = (TextView) findViewById(R.id.txt_edit_info_vegetable);
        mTxtEditInfoVegetable.setOnClickListener(this);

        mCreateVegetablePresenter = new CreateVegetablePresenter(getApplication(), this, this);
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mSearchByNamePresenter = new SearchByNamePresenter(getApplication(), this, this);

    }

    private void initialData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mGardenResult = (GardenResult) bundle.getSerializable(KEY_GARDEN_INFO);
            mIntGardenId = mGardenResult.getId();
            mStrGardenName = mGardenResult.getName();
            mStrGardenAddress = mGardenResult.getAddress();
        }

        System.out.println("AAAAAAAAAAAAAAAAAA****************************************AAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
        System.out.println("garden Id: " + mIntGardenId);
        System.out.println("garden name: " + mStrGardenName);
        System.out.println("garden address: " + mStrGardenAddress);
        System.out.println("AAAAAAAAAAAAAAAAAA****************************************AAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
        mListImageVegetable = new ArrayList<>();
        mStrSearchValue = mEdtSearchValue.getText().toString().trim();

    }

    /*number quantity err*/
    private  void showDialogQuantityErr() {
        final Dialog dialog = new Dialog(CreateVegetableActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        TextView txtDetail;
        Button btnOk;
        txtDetail = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnOk = (Button) dialog.findViewById(R.id.btn_close);
        txtDetail.setText("Vui lòng nhập số lượng");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    /*incomplete information*/
    private  void showDialogInputInfoErr() {
        final Dialog dialog = new Dialog(CreateVegetableActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        TextView txtDetail;
        Button btnOk;
        txtDetail = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnOk = (Button) dialog.findViewById(R.id.btn_close);
        txtDetail.setText("Vui lòng điền đủ thông tin");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    /*show dialog add vegetable*/
    private  void showDialogAddVegetable() {
        mDialogAddVegetable = new Dialog(CreateVegetableActivity.this);
        mDialogAddVegetable.setContentView(R.layout.dialog_add_vegetable);
        mDialogAddVegetable.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogAddVegetable.setCanceledOnTouchOutside(false);

        mDialogAddVegetable.show();
    }
    /*dialog create vegetable err*/
    private  void showDialogCreateVegetableErr() {
        final Dialog dialog = new Dialog(CreateVegetableActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        TextView txtDetail;
        Button btnOk;
        txtDetail = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnOk = (Button) dialog.findViewById(R.id.btn_close);
        txtDetail.setText("Thêm rau không thành công, vui lòng thử lại");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /*open album or camera*/
    private void showDialogTakeOfImage() {
        final Dialog dialog = new Dialog(CreateVegetableActivity.this);
        dialog.setContentView(R.layout.dialog_take_of_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        TextView mTxtImgCamera, mTxtImgStorage;
        Button mBtnClose;

        mTxtImgCamera = (TextView) dialog.findViewById(R.id.txt_img_camera);
        mTxtImgStorage = (TextView) dialog.findViewById(R.id.txt_imh_storage);
        mBtnClose = (Button) dialog.findViewById(R.id.btn_close);

        mTxtImgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, CAMERA_REQUEST);
                dialog.dismiss();
            }
        });
        mTxtImgStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentStorage = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentStorage, 2);
                dialog.dismiss();
            }
        });
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    /*create vegetable*/
    public void createVegetable() {
        String mName = mEdtVegetableName.getText().toString();
        String mDescription = mEdtVegetableDescription.getText().toString();
        String mFeature = mEdtVegetableFeature.getText().toString();
        int mQuantity = 0;
        try {
            mQuantity = Integer.parseInt(mEdtVegetableQuantity.getText().toString());
        } catch (NumberFormatException ex) {
        }

        if (mQuantity == 0) {
            showDialogQuantityErr();
            return;
        } else if (mName.equals("") || mDescription.equals("") || mFeature.equals("")) {
            showDialogInputInfoErr();
            return;
        }
        //new
        RequestBody requestLinkImage = null;

        if (mStrUrl.equals(URL_TELEPHONE)) {
            //image
            mListImagePart = new ArrayList<>();
            File file = new File(mMediaPath);
            String file_path = file.getAbsolutePath();
            String[] file_path_arr = file_path.split("\\.");
            file_path = file_path_arr[0] + System.currentTimeMillis() + "." + file_path_arr[1];
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            mRequestImage = MultipartBody.Part.createFormData("NewImages", file_path, requestBody);
            mListImagePart.add(mRequestImage);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println("chay vao if");
            System.out.println("media path if: " + file_path);
            mStrLinkUrl = "";
            System.out.println("link anh khi chon anh tu dien thoai: " + mStrLinkUrl);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

            requestLinkImage = RequestBody.create(MediaType.parse("text/plain"), mStrLinkUrl);


        } else {
            mRequestImage = null;
//            mStrIdDescription = "";
//            mStrSearchValue = "";
//            mStrSearchValue = mStrSearchValue;

            if (!mStrFeatureOfLisText.equals("")) {
                System.out.println("********AAAAAAAAAA***********if111111111111111111111111111111111111");
                String[] str_feature_arr = mStrFeatureOfLisText.split("\n\r");
                if (str_feature_arr != null) {
                    System.out.println("******BBBBBBBBBBBBBB*********IF222222222222222222222222222222222");
                    mStrSynonymOfFeature = str_feature_arr[0].trim();
                    System.out.println("****************************************************");
                    System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                    System.out.println("mStrFeatureOfLisText: " + mStrFeatureOfLisText);
                    System.out.println("tach tu dong nghia: " + mStrSynonymOfFeature);
                    System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                    System.out.println("****************************************************");
                } else {
                    mStrSynonymOfFeature = "";
                    System.out.println("*******BBBBBBBBBBBBBBB**************else2222222222222222222222222222222222222222");
                }
            } else {
                mStrSynonymOfFeature = "";
                System.out.println("*********AAAAAAAAAAAAA****************else111111111111111111111111111111111111111");
            }

//            mStrSynonymOfFeature = "";

            requestLinkImage = RequestBody.create(MediaType.parse("text/plain"), mStrLinkUrl);
            System.out.println("HHHHHHHHHHHHHHHH    chay vao else (url tu search) HHHHHHHHHHHHHHHHHHHHHHhh");
            System.out.println("link image chon anh tu wiki: " + mStrLinkUrl);
            System.out.println("tach ra nek: " + mStrSynonymOfFeature);
            System.out.println("HHHHHHHHHHHHHHHH    chay vao else (url tu search) HHHHHHHHHHHHHHHHHHHHHHhh");
        }

        RequestBody requestTitle = RequestBody.create(MediaType.parse("text/plain"), mName);
        RequestBody requestDescription = RequestBody.create(MediaType.parse("text/plain"), mDescription);
        RequestBody requestFeature = RequestBody.create(MediaType.parse("text/plain"), mFeature);
        RequestBody requestQuantity = (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mQuantity));
        RequestBody requestGardenId = (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mIntGardenId));
        RequestBody requestIdDescription = RequestBody.create(MediaType.parse("text/plain"), mStrIdDescription);
        RequestBody requestIsFixed = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mBlIsFixed));
        RequestBody requestNameSearch = RequestBody.create(MediaType.parse("text/plain"), mStrSearchValue);
        RequestBody requestSynonymOfFeature = RequestBody.create(MediaType.parse("text/plain"), mStrSynonymOfFeature);


        System.out.println("------------------------------------------------------------------------");
        System.out.println("mStrUrl: " + mStrUrl);
        System.out.println("requestTitle: " + mName);
        System.out.println("requestDescription: " + mDescription);
        System.out.println("requestFeature: " + mFeature);
        System.out.println("requestQuantity: " + mQuantity);
        System.out.println("requestGardenId: " + mIntGardenId);
        System.out.println("requestIdDescription: " + mStrIdDescription);
        System.out.println("requestIsFixed: " + mBlIsFixed);
        System.out.println("requestNameSearch: " + mStrSearchValue);
        System.out.println("requestSynonymOfFeature: " + mStrSynonymOfFeature);
        System.out.println("requestlinkUrl: " + requestLinkImage);
        System.out.println("link anh: " + mStrLinkUrl);
        System.out.println("requestImage: " + mRequestImage);
        System.out.println("mToken: " + mToken);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD chay api tao rau DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

        mCreateVegetablePresenter.createVegetable(requestTitle, requestDescription, requestFeature, requestQuantity,
                requestGardenId, requestIdDescription, requestIsFixed, requestNameSearch, requestSynonymOfFeature, requestLinkImage,
                mRequestImage, mToken);

        showDialogAddVegetable();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_create_vegetable:
                showDialogTakeOfImage();
                break;
            case R.id.btn_create_vegetable:
                createVegetable();
                break;
            case R.id.lnl_search:
                clickOpenBottomSheet();
                break;
            case R.id.lnl_back:
                finish();
                break;
            case R.id.txt_edit_info_vegetable:
                clickAllowEditVegetable();
                break;
        }
    }
    /*click allow edit vegetable*/
    private void clickAllowEditVegetable() {
        mEdtVegetableName.setEnabled(true);
        mEdtVegetableDescription.setEnabled(true);
        mEdtVegetableFeature.setEnabled(true);
        mBlIsFixed = true;
        mStrIdDescription = mStrIdDescriptionTmp;
        System.out.println("****************    ****************************");
        System.out.println("zxc: " + mStrIdDescriptionTmp);
        System.out.println("clickAllowEditVegetable");
        System.out.println("****************    ****************************");
    }

    //open bottom sheet
    private void clickOpenBottomSheet() {
        mStrSearchValue = mEdtSearchValue.getText().toString().trim();
//        mStrSearchOption = SEARCH_NAME;
        mSearchByNamePresenter.searchByName(mStrSearchValue, mToken);

    }

    /*dialog intro vegetable*/
    private void showDialogIntroVegetable(final VegetableData vegetableData) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_intro_vegetable);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        /*anh xa view*/
        TextView txtName, txtDescription, txtFeature, txtClose, txtAdmit;
        ImageView imgVegetable;
        Button btnClose, btnAdmit;
        txtName = (TextView) dialog.findViewById(R.id.txt_vegetable_name);
        txtDescription = (TextView) dialog.findViewById(R.id.txt_vegetable_description);
        txtFeature = (TextView) dialog.findViewById(R.id.txt_vegetable_feature);
        txtClose = (TextView) dialog.findViewById(R.id.txt_close);
        txtAdmit = (TextView) dialog.findViewById(R.id.txt_admit);
        imgVegetable = (ImageView) dialog.findViewById(R.id.img_create_vegetable);
        /**/
        txtName.setText(vegetableData.getName());
        txtDescription.setText(vegetableData.getDescription());
        txtFeature.setText(vegetableData.getFeature());
        mStrIdDescriptionTmp = vegetableData.getIdDescription();
        System.out.println("777777777777777777777771111111111111111777777777777777777777777777777777777");
        System.out.println("abc: " + mStrIdDescriptionTmp);
        System.out.println("abc: " + mStrIdDescription);
        System.out.println("xyz: " + vegetableData.getIdDescription());
        System.out.println("777777777777777777777771111111111111111777777777777777777777777777777777777");
        /*disable ediText*/
        if (mLnlEditInfoVegetable.getVisibility() == View.GONE) {
            mLnlEditInfoVegetable.setVisibility(View.VISIBLE);
        }
        mEdtVegetableName.setEnabled(false);
        mEdtVegetableDescription.setEnabled(false);
        mEdtVegetableFeature.setEnabled(false);
        mBlIsFixed = false;
        mStrIdDescription = "";

        if (vegetableData.getImageVegetables().size() > 0) {
            int maxSize = vegetableData.getImageVegetables().size() - 1;
            String imageDialog = vegetableData.getImageVegetables().get(maxSize).getUrl().toString().trim();
            if (imageDialog.equals("")) {
                imgVegetable.setImageResource(R.mipmap.addimage64);
            } else {
                Picasso.with(getApplicationContext()).load(imageDialog)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.caybacha)
                        .into(imgVegetable);
            }
        }
        txtAdmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdtVegetableName.setText(vegetableData.getName());
                mEdtVegetableDescription.setText(vegetableData.getDescription());
                mEdtVegetableFeature.setText(vegetableData.getFeature());
                if (vegetableData.getImageVegetables().size() > 0) {
                    int maxSize = vegetableData.getImageVegetables().size() - 1;
                    String imageDialog = vegetableData.getImageVegetables().get(maxSize).getUrl().toString().trim();
                    if (imageDialog.equals("")) {
                        mImgCreateVegetable.setImageResource(R.mipmap.addimage64);
                    } else {
                        Picasso.with(getApplicationContext()).load(imageDialog)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.caybacha)
                                .into(mImgCreateVegetable);
                    }
                    mStrLinkUrl = imageDialog;
                }
                mStrUrl = URL_SEARCH_NAME;
                mSearchByNameBottomSheetFragment.dismiss();
                dialog.dismiss();
            }
        });
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mStrUrl = URL_TELEPHONE;

        try {
            if (requestCode == 2 && resultCode == RESULT_OK && data != null){
                Uri selectImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectImage, filePathColumn, null, null, null);
                assert  cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mMediaPath = cursor.getString(columnIndex);

                System.out.println("----------- **************** ---------------------");
                System.out.println("link tu bo nho");
                System.out.println("media path: " + mMediaPath);
                System.out.println("----------- **************** ---------------------");

                mImgCreateVegetable.setImageBitmap(BitmapFactory.decodeFile(mMediaPath));
                cursor.close();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //cach 2
        try {

            if (requestCode == 0 && resultCode == RESULT_OK &&data != null) {
//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                mImgCreateVegetable.setImageBitmap(bitmap);

                try {
                    Uri selectedImage = data.getData();
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);


                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    File path = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES);
                    File file = new File(path, timeStamp + ".jpg");
                    FileOutputStream fo;
                    try {
                        path.mkdirs();
                        fo = new FileOutputStream(file);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mMediaPath = file.getAbsolutePath();
                    System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                    System.out.println("link came ra");
                    System.out.println("*************");
                    System.out.println("medipath: " + mMediaPath);
                    System.out.println("*************");
                    System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                    mImgCreateVegetable.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /*get data user*/
    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mToken = user.getToken();
    }

    /*get data search vegetable from system*/
    @Override
    public void searchByNameSuccess(List<VegetableData> vegetableData) {
        this.mListVegetable = (ArrayList<VegetableData>) vegetableData;
        System.out.println("searchByNameSuccess searchByNameSuccess");
        System.out.println("list search trong he thong: " + mListVegetable.size());
        mSearchByNameBottomSheetFragment =
                new SearchByNameBottomSheetFragment(mListVegetable, mStrSearchValue, new IClickVegetable() {
                    @Override
                    public void clickVegetable(VegetableData vegetableData) {
                        showDialogIntroVegetable(vegetableData);
                    }
                });
        mSearchByNameBottomSheetFragment.setCancelable(false);
        mSearchByNameBottomSheetFragment.show(getSupportFragmentManager(), mSearchByNameBottomSheetFragment.getTag());
    }

    @Override
    public void searchByNameFail() {
        mListVegetable = new ArrayList<>();
        mSearchByNameBottomSheetFragment =
                new SearchByNameBottomSheetFragment(mListVegetable, mStrSearchValue, new IClickVegetable() {
                    @Override
                    public void clickVegetable(VegetableData vegetableData) {
//                        showDialogIntroVegetable(vegetableData);
                    }
                });
        mSearchByNameBottomSheetFragment.setCancelable(false);
        mSearchByNameBottomSheetFragment.show(getSupportFragmentManager(), mSearchByNameBottomSheetFragment.getTag());
        System.out.println("BBBBBBBBBBBBBBBB searchByNameFail   BBBBBBBBBBBBBBBBBBBBB");
    }

    /*get data search wiki full*/
    @Override
    public void getDataSearchWiki(String vegName, String vegDescription, String vegFeature, String vegLinkUrl) {
        if (mLnlEditInfoVegetable.getVisibility() == View.VISIBLE) {
            mLnlEditInfoVegetable.setVisibility(View.GONE);
        }
        mEdtVegetableName.setEnabled(true);
        mEdtVegetableDescription.setEnabled(true);
        mEdtVegetableFeature.setEnabled(true);
        mBlIsFixed = false;
        mStrIdDescription = "";
        mEdtVegetableName.setText(vegName);
        mEdtVegetableDescription.setText(vegDescription);
        mEdtVegetableFeature.setText(vegFeature);
        mStrFeature = vegFeature;
        mStrFeatureOfLisText = vegFeature;
        if (vegLinkUrl.equals("")) {
            mImgCreateVegetable.setImageResource(R.mipmap.addimage64);
        } else {
            Picasso.with(getApplicationContext()).load("https:" + vegLinkUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(mImgCreateVegetable);
        }
        mStrLinkUrl = "https:" + vegLinkUrl.trim();
        System.out.println("HHHHHHHHHHHHHHHHhh  getDataSearchWiki full  HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHhh");
        System.out.println("name: " + vegName);
        System.out.println("des: " + vegDescription);
        System.out.println("feature: " + vegFeature);
        System.out.println("link url image: " + vegLinkUrl);
        System.out.println("HHHHHHHHHHHHHHHHhh  getDataSearchWiki dialog full HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHhh");
    }

    /*get data search wiki title*/
    @Override
    public void getDataSearchWikiTitleNull(String vegName, String vegLinkUrl, String vegDescription, String vegFeature) {
        if (mLnlEditInfoVegetable.getVisibility() == View.VISIBLE) {
            mLnlEditInfoVegetable.setVisibility(View.GONE);
        }
        mEdtVegetableName.setEnabled(true);
        mEdtVegetableDescription.setEnabled(true);
        mEdtVegetableFeature.setEnabled(true);
        mBlIsFixed = false;
        mStrIdDescription = "";
        mEdtVegetableName.setText(vegName);
        mEdtVegetableDescription.setText(vegDescription);
        mEdtVegetableFeature.setText(vegFeature);
        mStrFeature = vegFeature;
//        mStrFeatureOfLisText = vegFeature;
        if (vegLinkUrl.equals("")) {
            mImgCreateVegetable.setImageResource(R.mipmap.addimage64);
        } else {
            Picasso.with(getApplicationContext()).load("https:" + vegLinkUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(mImgCreateVegetable);
        }
        mStrLinkUrl = "https:" + vegLinkUrl.trim();
        System.out.println("HHHHHHHHHHHHHHHHhh  getDataSearchWiki title full  HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHhh");
        System.out.println("name: " + vegName);
        System.out.println("link url image: " + vegLinkUrl);
        System.out.println("HHHHHHHHHHHHHHHHhh  getDataSearchWiki dialog title full HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHhh");
    }

    /*receive data from the server*/
    @Override
    public void createVegetableSuccess() {
        System.out.println("************************ 5555555555555555 **************************************");
        System.out.println("createVegetableView Success");
        System.out.println("************************ 5555555555555555 **************************************");
        mDialogAddVegetable.dismiss();
        Intent intent = new Intent(CreateVegetableActivity.this, GardenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_VEGETABLE_CREATE, mGardenResult);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void createVegetableFail() {
        mDialogAddVegetable.dismiss();
        showDialogCreateVegetableErr();
//        Toast.makeText(getApplicationContext(), "FFFFFFFFFFFF tao rau khong thanh cong", Toast.LENGTH_SHORT).show();
    }

    /*upload image only*/
    public void uploadImage() {

        mListImagePart = new ArrayList<>();

        File file = new File(mMediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        mRequestImage = MultipartBody.Part.createFormData("newItem", file.getName(), requestBody);
        mListImagePart.add(mRequestImage);

        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        System.out.println(mListImagePart);
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");



    }

    /*check permission*/
    private void checkPermission() {
        Dexter.withContext(CreateVegetableActivity.this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {

                        } else {
//                            showDialogCheckPermissionAgain();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).onSameThread().check();
    }
    /*check permission again*/
    private void showDialogCheckPermissionAgain() {
        final Dialog dialog = new Dialog(CreateVegetableActivity.this);
        dialog.setContentView(R.layout.dialog_delete_garden);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose, btnDelete;
        btnClose = (Button) dialog.findViewById(R.id.btn_delete_no);
        btnDelete = (Button) dialog.findViewById(R.id.btn_delete_yes);
        btnDelete.setText("Tiếp tục");
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_content_delete);
        txtQuantity.setText("Vui lòng cấp quyền cho ứng dụng để tiếp tục sử dụng ");


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(CreateVegetableActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*comment*/
//    @Override
//    public void getDescriptionByWikiSuccess(List<WikiData> wikiData) {
//        System.out.println("AAAAAAAAAAAAgetDescriptionByWikiSuccessAAAAAAAAAAAAAAAAAAAAAAAAA");
//        System.out.println("AAAAAAAAAAAAgetDescriptionByWikiSuccessAAAAAAAAAAAAAAAAAAAAAAAAA");
//        mEdtVegetableDescription.setText(wikiData.get(0).getDescription());
//        mEdtVegetableName.setText(wikiData.get(0).getName());
//        List<String> listTextWikis = wikiData.get(0).getListText();
//        for (int i =0; i < listTextWikis.size(); i++) {
//            System.out.println("111 **** 1111");
//            System.out.println(i);
//            System.out.println(listTextWikis.get(i));
//            System.out.println("222 *** 222");
//        }
//        clickOpenListText(listTextWikis);
//    }

//    @Override
//    @Override
//    public void SearchByDescriptionSuccess(List<VegetableData> vegetableData) {
//        this.mListVegetable = (ArrayList<VegetableData>) vegetableData;
//        SearchByNameBottomSheetFragment searchByNameBottomSheetFragment =
//                new SearchByNameBottomSheetFragment(mListVegetable, new IClickVegetable() {
//                    @Override
//                    public void clickVegetable(VegetableData vegetableData) {
//                        mEdtVegetableName.setText(vegetableData.getName());
//                        mEdtVegetableDescription.setText(vegetableData.getDescription());
//                        mEdtVegetableFeature.setText(vegetableData.getFeature());
//                        mStrUrl = vegetableData.getImageVegetables().get(0).getUrl();
//                        if (vegetableData.getImageVegetables().get(0).getUrl() != null) {
//                            Picasso.with(getApplication()).load(vegetableData.getImageVegetables().get(0).getUrl())
//                                    .placeholder(R.drawable.ic_launcher_background)
//                                    .error(R.drawable.caybacha)
//                                    .into(mImgCreateVegetable);
//                        }
//                    }
//                });
//        searchByNameBottomSheetFragment.show(getSupportFragmentManager(), searchByNameBottomSheetFragment.getTag());
//    }
//    public void SearchByDescriptionFail() {
//
//    }
//
//    @Override
//    public void SearchByKeywordSuccess(List<VegetableData> vegetableSearchKeywords) {
//        this.mListVegetable = (ArrayList<VegetableData>) vegetableSearchKeywords;
//        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
//        System.out.println("size: " + mListVegetable);
//        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
//        SearchByNameBottomSheetFragment searchByNameBottomSheetFragment =
//                new SearchByNameBottomSheetFragment(mListVegetable, new IClickVegetable() {
//                    @Override
//                    public void clickVegetable(VegetableData vegetableData) {
//                        mEdtVegetableName.setText(vegetableData.getName());
//                        mEdtVegetableDescription.setText(vegetableData.getDescription());
//                        mEdtVegetableFeature.setText(vegetableData.getFeature());
//                        mStrUrl = vegetableData.getImageVegetables().get(0).getUrl();
//                        if (vegetableData.getImageVegetables().get(0).getUrl() != null) {
//                            Picasso.with(getApplication()).load(vegetableData.getImageVegetables().get(0).getUrl())
//                                    .placeholder(R.drawable.ic_launcher_background)
//                                    .error(R.drawable.caybacha)
//                                    .into(mImgCreateVegetable);
//                        }
//                    }
//                });
//        searchByNameBottomSheetFragment.show(getSupportFragmentManager(), searchByNameBottomSheetFragment.getTag());
//    }
//
//    @Override
//    public void SearchByKeywordFail() {
//
//    }

}
