package com.example.democ.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.democ.RequestExchangeFragment;
import com.example.democ.fragment.EmptyFragment;
import com.example.democ.fragment.GardenFragment;
import com.example.democ.R;
import com.example.democ.CreatePostFragment;
import com.example.democ.DiaryFragment;
import com.example.democ.MessageFragment;
import com.example.democ.PersonalFragment;
import com.example.democ.SearchFragment;
import com.example.democ.model.AccountSearchByName;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.GardenResult;
import com.example.democ.model.PostData;
import com.example.democ.presenters.AllExchangePresenter;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.presenters.AllSharePresenter;
import com.example.democ.presenters.LoginPresenter;
import com.example.democ.presenters.LogoutPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.AllExchangeView;
import com.example.democ.views.AllGardenView;
import com.example.democ.views.AllShareView;
import com.example.democ.views.LogoutView;
import com.example.democ.views.PersonalView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LogoutView {//} implements AllGardenView, AllShareView, AllExchangeView, PersonalView {

    private BottomNavigationView mBottomNavigationView;

    private User mUser;
    private UserManagement mUserManagement;
    public static String mAccessToken, mAccountId;
    private static List<GardenResult> mListAllGarden;
    private static List<PostData> mListAllPost;
    private static List<ExchangeData> mListAllExchange;
    private AllGardenPresenter mAllGardenPresenter;
    private AllSharePresenter mAllSharePresenter;
    private AllExchangePresenter mAllExchangePresenter;
    private PersonalPresenter mPersonalPresenter;

    private final static String SEARCH_ACCOUNT = "SearchAccount";
    private final static String KEY_FLAG = "KEY_FLAG";
    private static int mIntCheckFlag = 1;

    /*Back 2 tap exit app*/
    private long mBackPressTime;
    private LogoutPresenter mLogoutPresenter;

    //new

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                if(!task.isSuccessful()) {
//                    return;
//                }
//                // Get new Instance ID token
//                String token = task.getResult().getToken();
//                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//                System.out.println(token);
//                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//            }
//        });


        initialView();
        initialData();
    }


    @Override
    public void onBackPressed() {
        if (mBackPressTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            mLogoutPresenter.deleteAccountRoom();
            return;
        } else {
            Toast.makeText(MainActivity.this, "Click BACK lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT).show();
        }
        mBackPressTime = System.currentTimeMillis();
    }

    private void initialView() {
        mIntCheckFlag = 1;
        getDataSearchAccount();
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        Fragment selectedFragment = null;
//
//        if (mIntCheckFlag == 1) {
//            selectedFragment = new DiaryFragment();
//        } else if (mIntCheckFlag == 2) {
//            selectedFragment = new GardenFragment();
//        } else if (mIntCheckFlag == 3) {
//            selectedFragment = new RequestExchangeFragment();
//        } else if (mIntCheckFlag == 4) {
//            selectedFragment = new PersonalFragment();
//        }
        selectedFragment = new DiaryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, selectedFragment).commit();

        mLogoutPresenter = new LogoutPresenter(getApplication(), getApplicationContext(), this);

    }

    private void initialData() {
//        mAllGardenPresenter = new AllGardenPresenter(getApplication(), this, this);
//        mAllSharePresenter = new AllSharePresenter(getApplication(), this, this);
//        mAllExchangePresenter = new AllExchangePresenter(getApplication(), this, this);
//        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
//        mPersonalPresenter.getInfoPersonal();
//        mListAllGarden = new ArrayList<>();
//        mListAllPost = new ArrayList<>();
//        mListAllExchange = new ArrayList<>();
    }

    private void getDataSearchAccount() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mIntCheckFlag = bundle.getInt(KEY_FLAG);

            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println("flag: " + mIntCheckFlag);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragment = null;
                    switch (item.getItemId()) {
//                        case R.id.nav_search:
//                            selectFragment = new SearchFragment();
//                            break;
                        case R.id.nav_diary:
//                            mIntCheckFlag = 1;
                            selectFragment = new DiaryFragment();
                            break;
                        case R.id.nav_garden:
//                            mIntCheckFlag = 2;
                            selectFragment = new GardenFragment();
                            break;
                        case R.id.nav_request_exchange:
//                            mIntCheckFlag = 3;
                            selectFragment = new RequestExchangeFragment();
                            break;
                        case R.id.nav_personal:
//                            mIntCheckFlag = 4;
                            selectFragment = new PersonalFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectFragment).commit();
                    return true;
                }
            };

    @Override
    public void logoutSuccess() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void logoutFail() {

    }

//    @Override
//    public void getAllGardenSuccess(List<GardenResult> listAllGarden) {
//        this.mListAllGarden = listAllGarden;
//        if (mListAllGarden.size() > 0) {
//            initialView();
//
////            get all share - post
//            System.out.println("---------------- *************** -----------------------");
////            mAllSharePresenter.getAllShare(mAccessToken);
//            System.out.println("chay mAllSharePresenter.getAllShare(mAccessToken);");
////            mAllExchangePresenter.getAllExchange(mAccessToken);
//            System.out.println("---------------- *************** -----------------------");
//        } else {
//            initialView();
//        }
//    }
//    @Override
//    public void getAllGardenFail() {
//
//    }
//    @Override
//    public void allShareSuccess(List<PostData> postData) {
//        System.out.println("------------ ************* ------------------");
//        System.out.println("chay toi interface get all share");
//        mListAllPost = postData;
//        if (mListAllPost.size() > 0) {
//            System.out.println("------------- vao if get all share ------------------------");
//            System.out.println(mListAllPost.size());
//            initialView();
//            System.out.println("------------- ket thuc if get all share ------------------------");
//        }
//    }
//    @Override
//    public void allShareFail() {
//        System.out.println("FFFFFFFFFFFFFFFFF   allShareFail main home   FFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
//    }
//    @Override
//    public void allExchangeSuccess(List<ExchangeData> exchangeData) {
//        System.out.println("------------ ************* ------------------");
//        System.out.println("chay toi interface get all exchange");
//        mListAllExchange = exchangeData;
//        if (mListAllExchange.size() > 0) {
//            System.out.println("------------- vao if get all exchange ------------------------");
//            System.out.println(mListAllExchange.size());
//            initialView();
//            System.out.println("------------- ket thuc if get all exchange ------------------------");
//        }
//    }
//    @Override
//    public void allExchangeFail() {
//
//    }
//    @Override
//    public void showInfoPersonal(User user) {
//        mUser = user;
//        mAllExchangePresenter.getAllExchange(user.getToken());
//        mAllSharePresenter.getAllShare(user.getToken());
//        mAllGardenPresenter.getAllGarden(user.getToken());
//        System.out.println("AAAAAAAAAA  get all share main home AAAAAAAAAAAAAAAAAA");
//        System.out.println("token: " + user.getToken());
//        System.out.println("AAAAAAAAAA  get all share main home AAAAAAAAAAAAAAAAAA");
//    }
}
