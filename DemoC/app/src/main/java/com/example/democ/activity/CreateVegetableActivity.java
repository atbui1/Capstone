package com.example.democ.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.democ.presenters.SearchByDescriptionPresenter;
import com.example.democ.presenters.SearchByKeywordPresenter;
import com.example.democ.presenters.SearchByNamePresenter;
import com.example.democ.presenters.GetDescriptionByWikiPresenter;
import com.example.democ.presenters.SearchByWikiTitlePresenter;
import com.example.democ.presenters.UploadImagePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.CreateVegetableView;
import com.example.democ.views.SearchByNameView;
import com.example.democ.views.SearchByWikiTitleView;
import com.example.democ.views.GetDescriptionByWikiView;
import com.example.democ.views.UploadImageView;
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
        SearchByNameView, GetDescriptionByWikiView, SearchByWikiTitleView {//, SearchByDescriptionView, SearchByKeywordView {

    private ImageView mImgCreateVegetable;
    private EditText mEdtVegetableName, mEdtVegetableDescription, mEdtVegetableFeature, mEdtVegetableQuantity;
    private Button mBtnCreateVegetable;

    //btn_edit_true
    private Button mBtnEditTrue;


    static int mGardenId;
    static String mGardenName, mGardenAddress, mToken;
    private User mUser;
    private UserManagement mUserManagement;
    private CreateVegetablePresenter mCreateVegetablePresenter;
    //list part image
    private List<MultipartBody.Part> mListImagePart;
    private List<ImageVegetable> mListImageVegetable;
    private UploadImagePresenter mUploadImagePresenter;
    private MultipartBody.Part mRequestImage = null;
    private static final int CAMERA_REQUEST = 0;
    private static int request_code_image = 123;
    private String mMediaPath;
    private Uri outputFileUri;

    //new seacrh
    private EditText mEdtSearchValue;
    private TextView mTxtOptionSearch;
    private LinearLayout mLnlOptionSearch, mLnlSearch;
    private String mStrSearchValue = "";
    private String mStrSearchOption = "";
    private final static String SEARCH_NAME = "Tìm kiếm theo tên";
    private final static String SEARCH_DESCRIPTION = "Tìm kiếm theo mô tả";
    private final static String SEARCH_KEYWORD = "Tìm kiếm theo từ khóa";
    private final static String SEARCH_WIKI = "Tìm kiếm từ wikipedia";
    private ArrayList<VegetableData> mListVegetable;
    private ArrayList<VegetableDescription> mListVegetableSearchDescription;
    private ArrayList<WikiData> mListWiki;
    private ArrayList<WikiDataTitle> mListWikiTitle;
    private SearchByNamePresenter mSearchByNamePresenter;
    private SearchByDescriptionPresenter mSearchByDescriptionPresenter;
    private SearchByKeywordPresenter mSearchByKeywordPresenter;
    private GetDescriptionByWikiPresenter mGetDescriptionByWikiPresenter;
    private SearchByWikiTitlePresenter mSearchByWikiTitlePresenter;
    private String mStrUrl = null;
    private String mStrNameSearch = "", mStrSynonymOfFeature = "", mStrIdDescription = "";
    private boolean mBlIsFixed = false;
    private static String URL_SEARCH_NAME = "url_search_name";
    private static String URL_SEARCH_WIKI = "url_search_wiki";
    private static String URL_TELEPHONE = "url_telephone";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vegetable);

        initialView();
        initialData();
    }

    private void initialView() {
//        mListVegetable = new ArrayList<>();

        mImgCreateVegetable = (ImageView) findViewById(R.id.img_create_vegetable);
        mImgCreateVegetable.setOnClickListener(this);
        mEdtVegetableName = (EditText) findViewById(R.id.edt_vegetable_name);
        mEdtVegetableDescription = (EditText) findViewById(R.id.edt_vegetable_description);
        mEdtVegetableFeature = (EditText) findViewById(R.id.edt_vegetable_feature);
        mEdtVegetableQuantity = (EditText) findViewById(R.id.edt_vegetable_quantity);
        mBtnCreateVegetable = (Button) findViewById(R.id.btn_create_vegetable);
        mBtnCreateVegetable.setOnClickListener(this);

        mCreateVegetablePresenter = new CreateVegetablePresenter(getApplication(), this, this);
        mUploadImagePresenter = new UploadImagePresenter(getApplication(), this, this);

        //new
        mStrSearchOption = SEARCH_NAME;
        mEdtSearchValue = (EditText) findViewById(R.id.edt_search_value);
        mStrSearchValue = mEdtSearchValue.getText().toString().trim();
        mTxtOptionSearch = (TextView) findViewById(R.id.txt_option_search);
        mTxtOptionSearch.setText(mStrSearchOption);
        mLnlOptionSearch = (LinearLayout) findViewById(R.id.lnl_option_search);
        mLnlSearch = (LinearLayout) findViewById(R.id.lnl_search);
        mLnlOptionSearch.setOnClickListener(this);
        mLnlSearch.setOnClickListener(this);
        mSearchByNamePresenter = new SearchByNamePresenter(getApplication(), this, this);
        /*
        mSearchByDescriptionPresenter = new SearchByDescriptionPresenter(getApplication(), this,this);
        mSearchByKeywordPresenter = new SearchByKeywordPresenter(getApplication(), this, this);
        */
        mGetDescriptionByWikiPresenter = new GetDescriptionByWikiPresenter(getApplication(), this, this);
        mSearchByWikiTitlePresenter = new SearchByWikiTitlePresenter(getApplication(), this, this);
    }

    private void initialData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mGardenId = bundle.getInt("GARDEN_ID");
            mGardenName = bundle.getString("GARDEN_NAME");
            mGardenAddress = bundle.getString("GARDEN_ADDRESS");
        }
        mUserManagement = new UserManagement(getApplication());
        mUserManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                mUser = user;
                mToken = user.getToken();
                System.out.println("****************************************************");
                System.out.println(mToken);
//                mSearchByNamePresenter.searchByName(mStrSearchValue, mToken);
                System.out.println("****************************************************");
            }

            @Override
            public void onDataFail() {

            }
        });

        mListImageVegetable = new ArrayList<>();
        mStrSearchValue = mEdtSearchValue.getText().toString().trim();

    }

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

    //take a image
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
            Toast.makeText(getApplicationContext(), "khong covert dc quantity", Toast.LENGTH_SHORT).show();
        }

        if (mQuantity == 0) {
            showDialogQuantityErr();
            return;
        }
        //new
        if (mStrUrl.equals(URL_TELEPHONE)) {
            //image
            mListImagePart = new ArrayList<>();
            File file = new File(mMediaPath);
            String file_path = file.getAbsolutePath();
            String[] file_path_arr = file_path.split("\\.");
            file_path = file_path_arr[0] + System.currentTimeMillis() + "." + file_path_arr[1];
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            System.out.println("cat chuoi thay doi ten file path");
            System.out.println("file_path: " + file_path);
            System.out.println("cat chuoi thay doi ten file path");
            mRequestImage = MultipartBody.Part.createFormData("NewImages", file_path, requestBody);
            mListImagePart.add(mRequestImage);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println("chay vao if");
            System.out.println("media path if: " + mMediaPath);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        } else if(mStrUrl.equals(URL_SEARCH_NAME)) {
            mRequestImage = null;

//            idDetail
            mStrSearchValue = "";
            mStrSynonymOfFeature = "";
            showDialogChoiceImage();
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            System.out.println("chay vao elsse if URL_SEARCH_NAME");
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        } else if(mStrUrl.equals(URL_SEARCH_WIKI)) {
            mRequestImage = null;
            mStrIdDescription = "";
            showDialogChoiceImage();
            System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
            System.out.println("chay vao elsse if URL_SEARCH_WIKI");
            System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
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
        System.out.println("requestImage: " + mRequestImage);
        System.out.println("mToken: " + mToken);
        System.out.println("------------------------------------------------------------------------");

        if (mStrUrl.equals(URL_TELEPHONE)) {
            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            System.out.println("chay api");
            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            mCreateVegetablePresenter.createVegetable(requestTitle, requestDescription, requestFeature, requestQuantity,
                requestGardenId, requestIdDescription, requestIsFixed, requestNameSearch, requestSynonymOfFeature,
                    mRequestImage, mToken);
        } else {
            System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
            System.out.println("khong chay api");
            System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
        }

//        mCreateVegetablePresenter.createVegetable(requestTitle, requestDescription, requestFeature, requestQuantity, requestGardenId, requestImage, mToken);

//        mCreateVegetablePresenter.createVegetable(requestTitle, requestDescription, requestFeature, requestQuantity,
//                requestGardenId, requestIdDescription, requestIsFixed, requestNameSearch, requestSynonymOfFeature,
//                requestImage, mToken);
    }

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_create_vegetable:
                showDialogTakeOfImage();
                break;
            case R.id.btn_create_vegetable:
                createVegetable();
                break;
                //new
            case R.id.lnl_option_search:
                showDialogOptionSearch();
                break;
            case R.id.lnl_search:
                clickOpenBottomSheet();
                break;
        }
    }

    private void showDialogOptionSearch() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_option_search);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        //anh xa
        TextView txtSearchName, txtSearchWiki;//, txtSearchDescription, txtSearchKeyword
        Button btnClose;
        txtSearchName = (TextView) dialog.findViewById(R.id.txt_search_name);
        /*
        txtSearchDescription = (TextView) dialog.findViewById(R.id.txt_search_description);
        txtSearchKeyword = (TextView) dialog.findViewById(R.id.txt_search_keyword);
        */
        txtSearchWiki = (TextView) dialog.findViewById(R.id.txt_search_wiki);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        //onclick
        txtSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrSearchOption = SEARCH_NAME;
                mTxtOptionSearch.setText(mStrSearchOption);
                dialog.dismiss();
            }
        });

        /* comment */
//        txtSearchDescription.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mStrSearchOption = SEARCH_DESCRIPTION;
//                mTxtOptionSearch.setText(mStrSearchOption);
//                dialog.dismiss();
//            }
//        });
//        txtSearchKeyword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mStrSearchOption = SEARCH_KEYWORD;
//                mTxtOptionSearch.setText(mStrSearchOption);
//                dialog.dismiss();
//            }
//        });

        txtSearchWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrSearchOption = SEARCH_WIKI;
                mTxtOptionSearch.setText(mStrSearchOption);
                dialog.dismiss();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    //open bottom sheet
    private void clickOpenBottomSheet() {
        mStrSearchValue = mEdtSearchValue.getText().toString();
        if (mStrSearchOption == SEARCH_NAME) {
            Toast.makeText(getApplicationContext(), "search name", Toast.LENGTH_SHORT).show();
            mSearchByNamePresenter.searchByName(mStrSearchValue, mToken);
        }
        /*comment*/
//        else if(mStrSearchOption == SEARCH_DESCRIPTION) {
//            Toast.makeText(getApplicationContext(), "search description", Toast.LENGTH_SHORT).show();
//            mSearchByDescriptionPresenter.searchByDescription(mStrSearchValue, mToken);
//        } else if (mStrSearchOption == SEARCH_KEYWORD) {
//            Toast.makeText(getApplicationContext(), "search keyword", Toast.LENGTH_SHORT).show();
//            mSearchByKeywordPresenter.searchByKeyword(mStrSearchValue, mToken);
//        }
        else if (mStrSearchOption == SEARCH_WIKI) {
            Toast.makeText(getApplicationContext(), "search wiki", Toast.LENGTH_SHORT).show();
//            mGetDescriptionByWikiPresenter.searchByWiki(mStrSearchValue, mToken);
            mSearchByWikiTitlePresenter.searchByWikiTitle(mStrSearchValue, mToken);
        }
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
                    File destination = new File(Environment.getExternalStorageDirectory() + "/" +
                            getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mMediaPath = destination.getAbsolutePath();
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
            /*comment*/
//            else {
//                int width = mImgCreateVegetable.getMaxWidth();
//                int height = mImgCreateVegetable.getMaxHeight();
//                BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
//                factoryOptions.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(outputFileUri.getPath(), factoryOptions);
//                int imageWidth = factoryOptions.outWidth;
//                int imageHeight = factoryOptions.outHeight;
//                int scaleFator = Math.min(imageWidth / width, imageHeight / height);
//                factoryOptions.inJustDecodeBounds = false;
//                factoryOptions.inSampleSize = scaleFator;
//                factoryOptions.inPurgeable = true;
//                Bitmap bitmap = BitmapFactory.decodeFile(outputFileUri.getPath(), factoryOptions);
//                Toast.makeText(this, "File has already store", Toast.LENGTH_LONG).show();
//                mImgCreateVegetable.setImageBitmap(bitmap);
//
//                System.out.println("----------- **************** ---------------------");
//                System.out.println("link anh camera");
//                System.out.println("outputFileUri: " + outputFileUri);
//                System.out.println("bitmap: " + bitmap);
//                System.out.println("medipath: " + mediaPath);
//                System.out.println("----------- **************** ---------------------");
//            }
        } catch (Exception ex) {
            Toast.makeText(this, "upload image 22222222222", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void createVegetableSuccess() {
        System.out.println("************************ 5555555555555555 **************************************");
        System.out.println("createVegetableView Success");
        System.out.println("************************ 5555555555555555 **************************************");
        Intent intent = new Intent(CreateVegetableActivity.this, MainActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("GARDEN_NAME", mGardenName);
//        bundle.putString("GARDEN_ADdRESS", mGardenAddress);
////        intent.putExtras(bundle);
//        intent.putExtra("infoGarden", bundle);
        startActivity(intent);
    }

    @Override
    public void createVegetableFail() {
        Toast.makeText(getApplicationContext(), "FFFFFFFFFFFF tao rau khong thanh cong", Toast.LENGTH_SHORT).show();
        System.out.println("FFFFFFFFFFF FFFFFFFFFFF         FFFFFFFFFFFFFF      FFFFFFFFF");
        System.out.println("tao rau fail");
        System.out.println("FFFFFFFFFFF FFFFFFFFFFF         FFFFFFFFFFFFFF      FFFFFFFFF");
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

    @Override
    public void searchByNameSuccess(List<VegetableData> vegetableData) {
        this.mListVegetable = (ArrayList<VegetableData>) vegetableData;
        System.out.println(mListVegetable.size());
        SearchByNameBottomSheetFragment searchByNameBottomSheetFragment =
                new SearchByNameBottomSheetFragment(mListVegetable, new IClickVegetable() {
                    @Override
                    public void clickVegetable(VegetableData vegetableData) {
                        mStrIdDescription = vegetableData.getIdDescription();
                        mEdtVegetableName.setText(vegetableData.getName());
                        mEdtVegetableDescription.setText(vegetableData.getDescription());
                        mEdtVegetableFeature.setText(vegetableData.getFeature());
                        mStrUrl = URL_SEARCH_NAME;
                        int maxSize = vegetableData.getImageVegetables().size() - 1;
                        if (vegetableData.getImageVegetables().size() > 0) {
                            Picasso.with(getApplication()).load(vegetableData.getImageVegetables().get(maxSize).getUrl())
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.caybacha)
                                    .into(mImgCreateVegetable);
                        } else {
                            mImgCreateVegetable.setImageResource(R.mipmap.addimage64);
                        }
                    }
                });
        searchByNameBottomSheetFragment.show(getSupportFragmentManager(), searchByNameBottomSheetFragment.getTag());
    }

    @Override
    public void searchByNameFail() {

    }

    public void clickOpenListText(final List<String> listText) {
        ListTextWikiBottomSheetFragment listTextWikiBottomSheetFragment = new ListTextWikiBottomSheetFragment(listText,
                new IClickListTextWiki() {
                    @Override
                    public void clickListText(String msg, int pos) {
                        mEdtVegetableFeature.setText(listText.get(pos));
                        String featurePosition = listText.get(pos);
                        List<String> subMtp = Arrays.asList(featurePosition.split("\n\r"));
                        for(String w:subMtp){
                            System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                            System.out.println(w);
                            System.out.println("vi tri dau tien khi cat");
                            String a = subMtp.get(0);
                            System.out.println(a);
                            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                        }
                    }
                });
        listTextWikiBottomSheetFragment.show(getSupportFragmentManager(), listTextWikiBottomSheetFragment.getTag());
    }

    @Override
    public void searchByWikiTitleSuccess(List<WikiDataTitle> wikiDataTitles) {
        mListWikiTitle = (ArrayList<WikiDataTitle>) wikiDataTitles;
        SearchByWikiBottomSheetFragment searchByWikiBottomSheetFragment =
                new SearchByWikiBottomSheetFragment(mListWikiTitle, new IClickWikiTitle() {
                    @Override
                    public void clickWikiTitle(WikiDataTitle wikiDataTitle) {
                        System.out.println("NNNNNNNNNNNNNNNNNNNN wiki search titile NNNNNNNNNNNNNNNNNNNNNN");
                        System.out.println("NNNNNNNNNNNNNNNNNNNN wiki search titile NNNNNNNNNNNNNNNNNNNNNN");
                        System.out.println("title: " + wikiDataTitle.getTitle());
                        System.out.println("url: " + wikiDataTitle.getImage());
                        System.out.println("NNNNNNNNNNNNNNNNNNNN wiki search titile NNNNNNNNNNNNNNNNNNNNNN");
                        System.out.println("NNNNNNNNNNNNNNNNNNNN wiki search titile NNNNNNNNNNNNNNNNNNNNNN");
                        if (wikiDataTitle.getImage().equals("")) {
                            mImgCreateVegetable.setImageResource(R.mipmap.addimage64);
                        } else {
                            Picasso.with(getApplicationContext()).load("https:" + wikiDataTitle.getImage())
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.caybacha)
                                    .fit()
                                    .centerInside()
                                    .into(mImgCreateVegetable);
                        }
                        mGetDescriptionByWikiPresenter.getDescriptionByWiki(wikiDataTitle.getTitle(), mToken);
                    }
                });
        searchByWikiBottomSheetFragment.show(getSupportFragmentManager(), searchByWikiBottomSheetFragment.getTag());
    }

    @Override
    public void searchByWikiTitleFail() {

    }

    @Override
    public void getDescriptionByWikiSuccess(List<WikiData> wikiData) {
        System.out.println("AAAAAAAAAAAAgetDescriptionByWikiSuccessAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAgetDescriptionByWikiSuccessAAAAAAAAAAAAAAAAAAAAAAAAA");
        mEdtVegetableDescription.setText(wikiData.get(0).getDescription());
        mEdtVegetableName.setText(wikiData.get(0).getName());
        List<String> listTextWikis = wikiData.get(0).getListText();
        for (int i =0; i < listTextWikis.size(); i++) {
            System.out.println("111 **** 1111");
            System.out.println(i);
            System.out.println(listTextWikis.get(i));
            System.out.println("222 *** 222");
        }
        clickOpenListText(listTextWikis);
    }

    @Override
    public void getDescriptionByWikiFail() {

    }


    /*comment*/
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
