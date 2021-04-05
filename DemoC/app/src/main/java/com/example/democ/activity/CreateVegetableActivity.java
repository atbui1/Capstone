package com.example.democ.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.example.democ.model.Garden;
import com.example.democ.model.ImageVegetable;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableDescription;
import com.example.democ.model.VegetableRequest;
import com.example.democ.model.VegetableSearchDescription;
import com.example.democ.presenters.CreateVegetablePresenter;
import com.example.democ.presenters.SearchByDescriptionPresenter;
import com.example.democ.presenters.SearchByKeywordPresenter;
import com.example.democ.presenters.SearchByNamePresenter;
import com.example.democ.presenters.UploadImagePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.CreateVegetableView;
import com.example.democ.views.SearchByDescriptionView;
import com.example.democ.views.SearchByKeywordView;
import com.example.democ.views.SearchByNameView;
import com.example.democ.views.UploadImageView;
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

public class CreateVegetableActivity extends AppCompatActivity implements View.OnClickListener, CreateVegetableView, UploadImageView,
        SearchByNameView, SearchByDescriptionView, SearchByKeywordView {

    private ImageView mImgCreateVegetable;
    private EditText mEdtVegetableName, mEdtVegetableDescription, mEdtVegetableFeature, mEdtVegetableQuantity;
    private Button mBtnCreateVegetable;

    private static final int CAMERA_REQUEST = 0;
    private static int request_code_image = 123;
    private String mediaPath;
    private Uri outputFileUri;

    static int mGardenId;
    static String mGardenName, mGardenAddress, mToken;
    private User mUser;
    private UserManagement mUserManagement;
    private CreateVegetablePresenter mCreateVegetablePresenter;
    //list part image
    private List<MultipartBody.Part> mListImagePart;
    private List<ImageVegetable> mListImageVegetable;
    private UploadImagePresenter mUploadImagePresenter;
    private MultipartBody.Part requestImage = null;

    String imgBase64;

    //new seacrh
    private EditText mEdtSearchValue;
    private TextView mTxtOptionSearch;
    private LinearLayout mLnlOptionSearch, mLnlSearch;
    private String mStrSearchValue = "";
    private String mStrSearchOption = "";
    private final static String SEARCH_NAME = "Tìm kiếm theo tên";
    private final static String SEARCH_DESCRIPTION = "Tìm kiếm theo mô tả";
    private final static String SEARCH_KEYWORD = "Tìm kiếm theo từ khóa";
    private ArrayList<VegetableData> mListVegetable;
    private ArrayList<VegetableDescription> mListVegetableSearchDescription;
    private SearchByNamePresenter mSearchByNamePresenter;
    private SearchByDescriptionPresenter mSearchByDescriptionPresenter;
    private SearchByKeywordPresenter mSearchByKeywordPresenter;
    private String mStrUrl = "";
    private String mStrNewFeature = "", mStrIdDetailName = "", mStrIdDetailDescription = "", mStrIdDetailFeature = "", mStrIdDetailImage = "";

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
        mSearchByDescriptionPresenter = new SearchByDescriptionPresenter(getApplication(), this,this);
        mSearchByKeywordPresenter = new SearchByKeywordPresenter(getApplication(), this, this);
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

//        Matrix matrix = new Matrix();
//        mImgCreateVegetable.setScaleType(ImageView.ScaleType.MATRIX);   //required
//        matrix.postRotate((float) angle, pivotX, pivotY);
//        mImgCreateVegetable.setImageMatrix(matrix);

    }

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

        //new
        if (mStrUrl != null) {
            mStrNewFeature = "";
            requestImage = null;
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println("chay vao if");
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        } else {

            //image
            mListImagePart = new ArrayList<>();
            File file = new File(mediaPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            requestImage = MultipartBody.Part.createFormData("NewImages", file.getName(), requestBody);
            mListImagePart.add(requestImage);
//            idDetail
            mStrNewFeature = "";
            mStrIdDetailName = "";
            mStrIdDetailDescription = "";
            mStrIdDetailFeature = "";
            mStrIdDetailImage = "";
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            System.out.println("chay vao elsse");
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        }

        RequestBody requestTitle = RequestBody.create(MediaType.parse("text/plain"), mName);
        RequestBody requestDescription = RequestBody.create(MediaType.parse("text/plain"), mDescription);
        RequestBody requestFeature = RequestBody.create(MediaType.parse("text/plain"), mFeature);
        RequestBody requestNewFeature = RequestBody.create(MediaType.parse("text/plain"), mStrNewFeature);
        RequestBody requestQuantity = (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mQuantity));
        RequestBody requestGardenId = (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mGardenId));
        RequestBody requestIdDetailName = RequestBody.create(MediaType.parse("text/plain"), mStrIdDetailName);
        RequestBody requestIdDetailDescription = RequestBody.create(MediaType.parse("text/plain"), mStrIdDetailDescription);
        RequestBody requestIdDetailFeature = RequestBody.create(MediaType.parse("text/plain"), mStrIdDetailFeature);
        RequestBody requestIdDetailImage = RequestBody.create(MediaType.parse("text/plain"), mStrIdDetailImage);

        System.out.println("------------------------------------------------------------------------");
        System.out.println("mStrUrl: " +mStrUrl);
        System.out.println("requestTitle: " +requestTitle);
        System.out.println("requestDescription: " + requestDescription);
        System.out.println("requestFeature: " + requestFeature);
        System.out.println("requestNewFeature: " + requestNewFeature);
        System.out.println("requestQuantity: " + requestQuantity);
        System.out.println("requestGardenId: " + requestGardenId);
        System.out.println("requestIdDetailName: " + requestIdDetailName);
        System.out.println("requestIdDetailDescription: " + requestIdDetailDescription);
        System.out.println("requestIdDetailFeature: " + requestIdDetailFeature);
        System.out.println("requestIdDetailImage: " + requestIdDetailImage);
        System.out.println("requestImage: " + requestImage);
        System.out.println("mToken: " + mToken);
        System.out.println("------------------------------------------------------------------------");

//        mCreateVegetablePresenter.createVegetable(requestTitle, requestDescription, requestFeature, requestQuantity, requestGardenId, requestImage, mToken);

        mCreateVegetablePresenter.createVegetable(requestTitle, requestDescription, requestFeature, requestNewFeature, requestQuantity,
                requestGardenId, requestIdDetailName, requestIdDetailDescription, requestIdDetailFeature, requestIdDetailImage,
                requestImage, mToken);
    }

    public void uploadImage() {

        mListImagePart = new ArrayList<>();

        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        requestImage = MultipartBody.Part.createFormData("newItem", file.getName(), requestBody);
        mListImagePart.add(requestImage);

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
        TextView txtSearchName, txtSearchDescription, txtSearchKeyword;
        Button btnClose;
        txtSearchName = (TextView) dialog.findViewById(R.id.txt_search_name);
        txtSearchDescription = (TextView) dialog.findViewById(R.id.txt_search_description);
        txtSearchKeyword = (TextView) dialog.findViewById(R.id.txt_search_keyword);
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
        txtSearchDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrSearchOption = SEARCH_DESCRIPTION;
                mTxtOptionSearch.setText(mStrSearchOption);
                dialog.dismiss();
            }
        });
        txtSearchKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrSearchOption = SEARCH_KEYWORD;
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
        } else if(mStrSearchOption == SEARCH_DESCRIPTION) {
            Toast.makeText(getApplicationContext(), "search description", Toast.LENGTH_SHORT).show();
            mSearchByDescriptionPresenter.searchByDescription(mStrSearchValue, mToken);
        } else if (mStrSearchOption == SEARCH_KEYWORD) {
            Toast.makeText(getApplicationContext(), "search keyword", Toast.LENGTH_SHORT).show();
            mSearchByKeywordPresenter.searchByKeyword(mStrSearchValue, mToken);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mStrUrl = null;

        try {
            if (requestCode == 2 && resultCode == RESULT_OK && data != null){
                Uri selectImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectImage, filePathColumn, null, null, null);
                assert  cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                System.out.println("----------- **************** ---------------------");
                System.out.println("link tu bo nho");
                System.out.println("media path: " + mediaPath);
                System.out.println("----------- **************** ---------------------");

                mImgCreateVegetable.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
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

                    mediaPath = destination.getAbsolutePath();
                    System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                    System.out.println("link came ra");
                    System.out.println("*************");
                    System.out.println("medipath: " + mediaPath);
                    System.out.println("*************");
                    System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                    mImgCreateVegetable.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
    public void createVegetableSuccess(VegetableData vegetableData) {
        System.out.println("************************ 5555555555555555 **************************************");
        System.out.println("createVegetableView Success");
        System.out.println("************************ 5555555555555555 **************************************");
        Intent intent = new Intent(CreateVegetableActivity.this, GardenActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("GARDEN_NAME", mGardenName);
//        bundle.putString("GARDEN_ADdRESS", mGardenAddress);
//        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void createVegetableFail() {
        Toast.makeText(getApplicationContext(), "FFFFFFFFFFFF", Toast.LENGTH_SHORT).show();
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
                        mEdtVegetableName.setText(vegetableData.getName());
                        mEdtVegetableDescription.setText(vegetableData.getDescription());
                        mEdtVegetableFeature.setText(vegetableData.getFeature());
                        mStrUrl = vegetableData.getImageVegetables().get(0).getUrl();
                        mStrIdDetailName = vegetableData.getIdDetailName();
                        mStrIdDetailDescription = vegetableData.getIdDetailDescription();
                        mStrIdDetailFeature = vegetableData.getIdDetailFeature();
                        mStrIdDetailImage = vegetableData.getIdDetailImage();
                        if (vegetableData.getImageVegetables().get(0).getUrl() != null) {
                            Picasso.with(getApplication()).load(vegetableData.getImageVegetables().get(0).getUrl())
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.caybacha)
                                    .into(mImgCreateVegetable);
                        }
                    }
                });
        searchByNameBottomSheetFragment.show(getSupportFragmentManager(), searchByNameBottomSheetFragment.getTag());
    }

    @Override
    public void searchByNameFail() {

    }

    @Override
    public void SearchByDescriptionSuccess(List<VegetableData> vegetableData) {
        this.mListVegetable = (ArrayList<VegetableData>) vegetableData;
        SearchByNameBottomSheetFragment searchByNameBottomSheetFragment =
                new SearchByNameBottomSheetFragment(mListVegetable, new IClickVegetable() {
                    @Override
                    public void clickVegetable(VegetableData vegetableData) {
                        mEdtVegetableName.setText(vegetableData.getName());
                        mEdtVegetableDescription.setText(vegetableData.getDescription());
                        mEdtVegetableFeature.setText(vegetableData.getFeature());
                        mStrUrl = vegetableData.getImageVegetables().get(0).getUrl();
                        if (vegetableData.getImageVegetables().get(0).getUrl() != null) {
                            Picasso.with(getApplication()).load(vegetableData.getImageVegetables().get(0).getUrl())
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.caybacha)
                                    .into(mImgCreateVegetable);
                        }
                    }
                });
        searchByNameBottomSheetFragment.show(getSupportFragmentManager(), searchByNameBottomSheetFragment.getTag());
    }

    @Override
    public void SearchByDescriptionFail() {

    }

    @Override
    public void SearchByKeywordSuccess(List<VegetableData> vegetableSearchKeywords) {
        this.mListVegetable = (ArrayList<VegetableData>) vegetableSearchKeywords;
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
        System.out.println("size: " + mListVegetable);
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
        SearchByNameBottomSheetFragment searchByNameBottomSheetFragment =
                new SearchByNameBottomSheetFragment(mListVegetable, new IClickVegetable() {
                    @Override
                    public void clickVegetable(VegetableData vegetableData) {
                        mEdtVegetableName.setText(vegetableData.getName());
                        mEdtVegetableDescription.setText(vegetableData.getDescription());
                        mEdtVegetableFeature.setText(vegetableData.getFeature());
                        mStrUrl = vegetableData.getImageVegetables().get(0).getUrl();
                        if (vegetableData.getImageVegetables().get(0).getUrl() != null) {
                            Picasso.with(getApplication()).load(vegetableData.getImageVegetables().get(0).getUrl())
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.caybacha)
                                    .into(mImgCreateVegetable);
                        }
                    }
                });
        searchByNameBottomSheetFragment.show(getSupportFragmentManager(), searchByNameBottomSheetFragment.getTag());
    }

    @Override
    public void SearchByKeywordFail() {

    }
}
