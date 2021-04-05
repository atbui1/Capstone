package com.example.democ.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.democ.RequestExchangeFragment;
import com.example.democ.fragment.EmptyFragment;
import com.example.democ.fragment.GardenFragment;
import com.example.democ.R;
import com.example.democ.CreatePostFragment;
import com.example.democ.DiaryFragment;
import com.example.democ.MessageFragment;
import com.example.democ.PersonalFragment;
import com.example.democ.SearchFragment;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.GardenResult;
import com.example.democ.model.PostData;
import com.example.democ.presenters.AllExchangePresenter;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.presenters.AllSharePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.AllExchangeView;
import com.example.democ.views.AllGardenView;
import com.example.democ.views.AllShareView;
import com.example.democ.views.PersonalView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AllGardenView, AllShareView, AllExchangeView, PersonalView {

    private BottomNavigationView mBottomNavigationView;

    private User mUser;
    private UserManagement mUserManagement;
    public static String mAccessToken;
    private static List<GardenResult> mListAllGarden;
    private static List<PostData> mListAllPost;
    private static List<ExchangeData> mListAllExchange;
    private AllGardenPresenter mAllGardenPresenter;
    private AllSharePresenter mAllSharePresenter;
    private AllExchangePresenter mAllExchangePresenter;
    private PersonalPresenter mPersonalPresenter;

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


//        initialView();
        initialData();
    }

    private void initialView() {
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(navListener);
//        Fragment selectedFragment = new GardenFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,
//                selectedFragment).commit();

//        loadFragment(new GardenFragment());
        Fragment selectedFragment = null;
        System.out.println("initial view ****************");
        System.out.println("mListAllGarden:  " + mListAllGarden.size());
        System.out.println("initial view ****************");
//        if (mListAllGarden.size() > 0) {
//            selectedFragment = new GardenFragment((ArrayList<GardenResult>) mListAllGarden, mAccessToken);
//        } else {
//            selectedFragment = new EmptyFragment();
//        }
        selectedFragment = new DiaryFragment((ArrayList<PostData>) mListAllPost, mAccessToken, mUser);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, selectedFragment).commit();

    }

    private void initialData() {
        mAllGardenPresenter = new AllGardenPresenter(getApplication(), this, this);
        mAllSharePresenter = new AllSharePresenter(getApplication(), this, this);
        mAllExchangePresenter = new AllExchangePresenter(getApplication(), this, this);

        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();

        Intent intentFromLogin = getIntent();
        Bundle bundleFromLogin = intentFromLogin.getExtras();
        if (bundleFromLogin != null) {
            mAccessToken = bundleFromLogin.getString("STRING_TOKEN");
            System.out.println(mAccessToken);
        }
        mAllGardenPresenter.getAllGarden(mAccessToken);

        mListAllGarden = new ArrayList<>();
        mListAllPost = new ArrayList<>();
        mListAllExchange = new ArrayList<>();
        System.out.println("AAAAAAAAAAA");
        System.out.println("list garden: " + mListAllGarden);
        System.out.println("BBBBBBBBBBBBB");


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
                        case R.id.nav_search:
                            selectFragment = new SearchFragment();
                            break;
                        case R.id.nav_request_exchange:
                            selectFragment = new RequestExchangeFragment((ArrayList<ExchangeData>) mListAllExchange, mAccessToken);
                            break;
                        case R.id.nav_diary:
                            selectFragment = new DiaryFragment((ArrayList<PostData>) mListAllPost, mAccessToken, mUser);
                            break;
                        case R.id.nav_garden:
                            selectFragment = new GardenFragment((ArrayList<GardenResult>) mListAllGarden, mAccessToken);
                            break;
                        case R.id.nav_personal:
                            selectFragment = new PersonalFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().add(R.id.frame_container, selectFragment).commit();
                    return true;
                }
            };

    @Override
    public void getAllGardenSuccess(List<GardenResult> listAllGarden) {
        this.mListAllGarden = listAllGarden;
        if (mListAllGarden.size() > 0) {
            initialView();

//            get all share - post
            System.out.println("---------------- *************** -----------------------");
            mAllSharePresenter.getAllShare(mAccessToken);
            System.out.println("chay mAllSharePresenter.getAllShare(mAccessToken);");
//            mAllExchangePresenter.getAllExchange(mAccessToken);
            System.out.println("---------------- *************** -----------------------");
        } else {
            initialView();
        }
    }

    @Override
    public void getAllGardenFail() {

    }

    @Override
    public void allShareSuccess(List<PostData> postData) {
        System.out.println("------------ ************* ------------------");
        System.out.println("chay toi interface get all share");
        mListAllPost = postData;
        if (mListAllPost.size() > 0) {
            System.out.println("------------- vao if get all share ------------------------");
            System.out.println(mListAllPost.size());
            initialView();
            System.out.println("------------- ket thuc if get all share ------------------------");
        }
    }

    @Override
    public void allShareFail() {

    }

    @Override
    public void allExchangeSuccess(List<ExchangeData> exchangeData) {
        System.out.println("------------ ************* ------------------");
        System.out.println("chay toi interface get all exchange");
        mListAllExchange = exchangeData;
        if (mListAllExchange.size() > 0) {
            System.out.println("------------- vao if get all exchange ------------------------");
            System.out.println(mListAllExchange.size());
            initialView();
            System.out.println("------------- ket thuc if get all exchange ------------------------");
        }
    }

    @Override
    public void allExchangeFail() {

    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mAllExchangePresenter.getAllExchange(user.getToken());
        mAllSharePresenter.getAllShare(user.getToken());
    }
}
