package com.example.democ.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.example.democ.fragment.ListTextWikiBottomSheetFragment;
import com.example.democ.fragment.SearchByNameBottomSheetFragment;
import com.example.democ.fragment.SearchByWikiBottomSheetFragment;
import com.example.democ.iclick.IClickListTextWiki;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.iclick.IClickWikiTitle;
import com.example.democ.model.ImageVegetable;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableDescription;
import com.example.democ.model.WikiData;
import com.example.democ.model.WikiDataTitle;
import com.example.democ.presenters.CreateVegetablePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.SearchByDescriptionPresenter;
import com.example.democ.presenters.SearchByKeywordPresenter;
import com.example.democ.presenters.SearchByNamePresenter;
import com.example.democ.presenters.GetDescriptionByWikiPresenter;
import com.example.democ.presenters.SearchByWikiTitlePresenter;
import com.example.democ.presenters.UploadImagePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.CreateVegetableView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.SearchByNameView;
import com.example.democ.views.SearchByWikiTitleView;
import com.example.democ.views.GetDescriptionByWikiView;
import com.example.democ.views.UploadImageView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateVegetableActivity extends AppCompatActivity implements View.OnClickListener, CreateVegetableView, UploadImageView,
        SearchByNameView, PersonalView,
        SearchByNameBottomSheetFragment.IGetDataSearchWikiListener,
        SearchByNameBottomSheetFragment.IGetDataSearchWikiTitleListener {

    private ImageView mImgCreateVegetable;
    private EditText mEdtVegetableName, mEdtVegetableDescription, mEdtVegetableFeature, mEdtVegetableQuantity;
    private Button mBtnCreateVegetable;

    static int mGardenId;
    static String mGardenName, mGardenAddress, mToken;
    private User mUser;
    private PersonalPresenter mPersonalPresenter;
    private CreateVegetablePresenter mCreateVegetablePresenter;
    //list part image
    private List<MultipartBody.Part> mListImagePart;
    private List<ImageVegetable> mListImageVegetable;
    private UploadImagePresenter mUploadImagePresenter;
    private MultipartBody.Part mRequestImage = null;
    private static final int CAMERA_REQUEST = 0;
    private static int request_code_image = 123;

    //new seacrh
    private EditText mEdtSearchValue;
    private LinearLayout mLnlSearch, mLnlBack;
    private String mStrSearchValue = "";
//    private final static String SEARCH_NAME = "Tìm kiếm theo tên";
    private final static String KEY_VEGETABLE_CREATE = "KEY_VEGETABLE_CREATE";
    private ArrayList<VegetableData> mListVegetable;
    private SearchByNamePresenter mSearchByNamePresenter;
    private String mStrNameSearch = "", mStrSynonymOfFeature = "", mStrIdDescription = "";
    private boolean mBlIsFixed = false;
    private static String URL_SEARCH_NAME = "url_search_name";
    private static String URL_TELEPHONE = "url_telephone";
    private String mMediaPath, mStrLinkUrl = "", mStrUrl = "";
    private SearchByNameBottomSheetFragment mSearchByNameBottomSheetFragment;


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

        mCreateVegetablePresenter = new CreateVegetablePresenter(getApplication(), this, this);
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mUploadImagePresenter = new UploadImagePresenter(getApplication(), this, this);

        mEdtSearchValue = (EditText) findViewById(R.id.edt_search_value);
        mStrSearchValue = mEdtSearchValue.getText().toString().trim();

        mLnlSearch = (LinearLayout) findViewById(R.id.lnl_search);
        mLnlSearch.setOnClickListener(this);
        mSearchByNamePresenter = new SearchByNamePresenter(getApplication(), this, this);

    }

    private void initialData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mGardenId = bundle.getInt("GARDEN_ID");
            mGardenName = bundle.getString("GARDEN_NAME");
            mGardenAddress = bundle.getString("GARDEN_ADDRESS");
        }

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
    private  void showDialogInputInfoyErr() {
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

    private  void showDialogChoiceImage() {
        final Dialog dialog = new Dialog(CreateVegetableActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        TextView txtDetail;
        Button btnOk;
        txtDetail = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnOk = (Button) dialog.findViewById(R.id.btn_close);
        txtDetail.setText("Vui lòng chọn ảnh từ điện thoại");
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
            showDialogInputInfoyErr();
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
            System.out.println("link anh: " + mStrLinkUrl);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

            requestLinkImage = RequestBody.create(MediaType.parse("text/plain"), mStrLinkUrl);


        } else {
            mRequestImage = null;
            mStrIdDescription = "";
            mStrSearchValue = "";
            mStrSynonymOfFeature = "";
            requestLinkImage = RequestBody.create(MediaType.parse("text/plain"), mStrLinkUrl);
            System.out.println("HHHHHHHHHHHHHHHH    chay vao else (url tu search) HHHHHHHHHHHHHHHHHHHHHHhh");
            System.out.println("link image: " + mStrLinkUrl);
            System.out.println("HHHHHHHHHHHHHHHH    chay vao else (url tu search) HHHHHHHHHHHHHHHHHHHHHHhh");
        }

        RequestBody requestTitle = RequestBody.create(MediaType.parse("text/plain"), mName);
        RequestBody requestDescription = RequestBody.create(MediaType.parse("text/plain"), mDescription);
        RequestBody requestFeature = RequestBody.create(MediaType.parse("text/plain"), mFeature);
        RequestBody requestQuantity = (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mQuantity));
        RequestBody requestGardenId = (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mGardenId));
        RequestBody requestIdDescription = RequestBody.create(MediaType.parse("text/plain"), mStrIdDescription);
        RequestBody requestIsFixed = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mBlIsFixed));
        RequestBody requestNameSearch = RequestBody.create(MediaType.parse("text/plain"), mStrNameSearch);
        RequestBody requestSynonymOfFeature = RequestBody.create(MediaType.parse("text/plain"), mStrSynonymOfFeature);


        System.out.println("------------------------------------------------------------------------");
        System.out.println("mStrUrl: " + mStrUrl);
        System.out.println("requestTitle: " + requestTitle);
        System.out.println("requestDescription: " + requestDescription);
        System.out.println("requestFeature: " + requestFeature);
        System.out.println("requestQuantity: " + requestQuantity);
        System.out.println("requestGardenId: " + requestGardenId);
        System.out.println("requestIdDescription: " + requestIdDescription);
        System.out.println("requestIsFixed: " + requestIsFixed);
        System.out.println("requestNameSearch: " + requestNameSearch);
        System.out.println("requestSynonymOfFeature: " + requestSynonymOfFeature);
        System.out.println("requestlinkUrl: " + requestLinkImage);
        System.out.println("link anh: " + mStrLinkUrl);
        System.out.println("requestImage: " + mRequestImage);
        System.out.println("mToken: " + mToken);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD chay api tao rau DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

        mCreateVegetablePresenter.createVegetable(requestTitle, requestDescription, requestFeature, requestQuantity,
                requestGardenId, requestIdDescription, requestIsFixed, requestNameSearch, requestSynonymOfFeature, requestLinkImage,
                mRequestImage, mToken);


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
        }
    }

    //open bottom sheet
    private void clickOpenBottomSheet() {
        mStrSearchValue = mEdtSearchValue.getText().toString();
//        mStrSearchOption = SEARCH_NAME;
        mSearchByNamePresenter.searchByName(mStrSearchValue, mToken);
//        if (mStrSearchOption == SEARCH_NAME) {
//            Toast.makeText(getApplicationContext(), "search name", Toast.LENGTH_SHORT).show();
//            mSearchByNamePresenter.searchByName(mStrSearchValue, mToken);
//        } else if (mStrSearchOption == SEARCH_WIKI) {
//            Toast.makeText(getApplicationContext(), "search wiki", Toast.LENGTH_SHORT).show();
//            mSearchByWikiTitlePresenter.searchByWikiTitle(mStrSearchValue, mToken);
//        }
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
        txtDescription.setText(vegetableData.getFeature());
        txtFeature.setText(vegetableData.getFeature());
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
            Toast.makeText(this, "upload image fail", Toast.LENGTH_SHORT).show();
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
//                    File destination = new File(Environment.getExternalStorageDirectory() + "/" +
//                            getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                    File path = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES);
                    File file = new File(path, timeStamp + ".jpg");
                    FileOutputStream fo;
                    try {
//                        destination.mkdirs();
//                        destination.createNewFile();
//                        fo = new FileOutputStream(destination);
                        path.mkdirs();
                        fo = new FileOutputStream(file);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    mMediaPath = destination.getAbsolutePath();
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
            Toast.makeText(this, "upload image 22222222222", Toast.LENGTH_SHORT).show();
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
        System.out.println(mListVegetable.size());
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

    }

    /*get data search wiki full*/
    @Override
    public void getDataSearchWiki(String vegName, String vegDescription, String vegFeature, String vegLinkUrl) {
        mEdtVegetableName.setText(vegName);
        mEdtVegetableDescription.setText(vegDescription);
        mEdtVegetableFeature.setText(vegFeature);
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
    public void getDataSearchWikiTitle(String vegName, String vegLinkUrl) {
        mEdtVegetableName.setText(vegName);
        mEdtVegetableDescription.setText("");
        mEdtVegetableFeature.setText("");
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
        Intent intent = new Intent(CreateVegetableActivity.this, GardenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("GARDEN_ID", mGardenId);
        bundle.putString("GARDEN_NAME", mGardenName);
        bundle.putString("GARDEN_ADDRESS", mGardenAddress);
        intent.putExtra(KEY_VEGETABLE_CREATE, bundle);
        startActivity(intent);
    }

    @Override
    public void createVegetableFail() {
        Toast.makeText(getApplicationContext(), "FFFFFFFFFFFF tao rau khong thanh cong", Toast.LENGTH_SHORT).show();
        System.out.println("FFFFFFFFFFF FFFFFFFFFFF         FFFFFFFFFFFFFF      FFFFFFFFF");
        System.out.println("tao rau fail");
        System.out.println("FFFFFFFFFFF FFFFFFFFFFF         FFFFFFFFFFFFFF      FFFFFFFFF");
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

        mUploadImagePresenter.uploadImage(mListImagePart, mUser.getToken());


    }
    @Override
    public void uploadImageSuccess(ImageVegetable imageVegetables) {
        System.out.println("--------------- ****************-----------------");
        System.out.println("upload image view success");
        System.out.println("chay create vegetable");
        System.out.println(imageVegetables.getLocalUrl());
        System.out.println(imageVegetables.getUrl());
        System.out.println(imageVegetables.getThumbnail());

//        createVegetable();

        System.out.println("-----------------********* ket thuc upload image view success ********* -----------------------");
    }

    @Override
    public void uploadImageFail() {

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
