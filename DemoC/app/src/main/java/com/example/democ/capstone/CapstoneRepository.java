package com.example.democ.capstone;

import android.content.Context;

import com.example.democ.model.Account;
import com.example.democ.model.AddFriendRequest;
import com.example.democ.model.DistrictData;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.ExchangeRequest;
import com.example.democ.model.Garden;
import com.example.democ.model.GardenResult;
import com.example.democ.model.ImageVegetable;
import com.example.democ.model.PostData;
import com.example.democ.model.ProvinceData;
import com.example.democ.model.ShareData;
import com.example.democ.model.ShareRequest;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableNeedAll;
import com.example.democ.model.VegetableRequest;
import com.example.democ.model.VegetableSearchDescription;
import com.example.democ.model.VegetableSearchKeyword;
import com.example.democ.model.WardData;
import com.example.democ.room.entities.User;
import com.example.democ.utils.CallBackData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface CapstoneRepository {

    void login(Context context, String userName, String password, String deviceToken, CallBackData<User> callBackData);
    void register(Context context, Account account, CallBackData<Account> callBackData);
    void getInfoAccount(Context context, String token, CallBackData<Account> callBackData);
    void updateAccount(Context context, Account account, String token, CallBackData<Account> callBackData);
    void getAddFriendRequest(Context context, String token, CallBackData<List<AddFriendRequest>> callBackData);
    void sendAddFriend(Context context, AddFriendRequest addFriendRequest, String token, CallBackData<AddFriendRequest> callBackData);

    void createGarden(Context context, Garden garden, String token, CallBackData<GardenResult> callBackData);
    void getAllGarden(Context context, String token, CallBackData<List<GardenResult>> callBackData);
    void updateGarden(Context context, Garden garden, String token, CallBackData<Garden> callBackData);
    void deleteGarden(Context context, int gardenId, String token, CallBackData<String> callBackData);

    void createVegetable(Context context, RequestBody title, RequestBody description, RequestBody featture, RequestBody newFeatture,
                         RequestBody quantity, RequestBody gardenId, RequestBody idDetailName, RequestBody idDetailDescription,
                         RequestBody idDetailFeature, RequestBody idDetailImage,
                         MultipartBody.Part newImages,
                         String token, CallBackData<VegetableData> callBackData);
    void getAllVegetableByGardenId(Context context, int gardenId, String token, CallBackData<List<VegetableData>> callBackData);
    void deleteVegetable(Context context, int noVeg, int gardenId, String token, CallBackData<String> callBackData);
    void getAllVegetableNeed(Context context, String token, CallBackData<List<VegetableNeedAll>> callBackData);
    void checkVegetableOfAccount(Context context, String vegetableNeedId, String vegetableNeedName, String token,
                                 CallBackData<List<VegetableData>> callBackData);

//    search
    void searchByName(Context context, String searchValue, String token, CallBackData<List<VegetableData>> callBackData);
    void searchByDescription(Context context, String searchValue, String token, CallBackData<List<VegetableData>> callBackData);
    void searchByKeyword(Context context, String searchValue, String token, CallBackData<List<VegetableData>> callBackData);

//    share - post
    void getAllShare(Context context, String token, CallBackData<List<PostData>> callBackData);
    void getAllShareById(Context context, String token, CallBackData<List<PostData>> callBackData);
    void createPostShare(Context context, ShareRequest shareRequest, String token, CallBackData<ShareData> callBackData);
    void isAcceptExchange(Context context, int status, ArrayList<String> exchangeId, String token, CallBackData<String> callBackData);
    void deleteExchangeRequest(Context context, String exchangeId, String token, CallBackData<String> callBackData);

//    exchange
    void createExchange(Context context, ExchangeRequest exchangeRequest, String token, CallBackData<List<ExchangeData>> callBackData);
    void getAllExchange(Context context, String token, CallBackData<List<ExchangeData>> callBackData);
//    upload image
    void uploadImage(Context context, List<MultipartBody.Part> newItem, String token, CallBackData<ImageVegetable> callBackData);

    //address
    void getAllProvince(Context context, String token, CallBackData<List<ProvinceData>> callBackData);
    void getDistrictById(Context context, int id, String token, CallBackData<List<DistrictData>> callBackData);
    void getWardById(Context context, int id, String token, CallBackData<List<WardData>> callBackData);
}
