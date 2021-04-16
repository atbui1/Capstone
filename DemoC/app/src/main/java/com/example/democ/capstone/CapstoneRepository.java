package com.example.democ.capstone;

import android.content.Context;

import com.example.democ.model.Account;
import com.example.democ.model.AccountSearchByName;
import com.example.democ.model.AddFriendRequest;
import com.example.democ.model.DistrictData;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.ExchangeRequest;
import com.example.democ.model.Garden;
import com.example.democ.model.GardenResult;
import com.example.democ.model.ImageVegetable;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchDescription;
import com.example.democ.model.PostSearchKeyword;
import com.example.democ.model.PostSearchName;
import com.example.democ.model.ProvinceData;
import com.example.democ.model.QRCodeData;
import com.example.democ.model.ReportPost;
import com.example.democ.model.ShareDetail;
import com.example.democ.model.ShareRequest;
import com.example.democ.model.UpdateVegetableRequest;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableNeedAll;
import com.example.democ.model.WardData;
import com.example.democ.model.WikiData;
import com.example.democ.model.WikiDataTitle;
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
    void searchAccountByName(Context context, String searchValue, String token, CallBackData<List<AccountSearchByName>> callBackData);

    void createGarden(Context context, Garden garden, String token, CallBackData<GardenResult> callBackData);
    void getAllGarden(Context context, String token, CallBackData<List<GardenResult>> callBackData);
    void updateGarden(Context context, Garden garden, String token, CallBackData<Garden> callBackData);
    void deleteGarden(Context context, int gardenId, String token, CallBackData<String> callBackData);

    void createVegetable(Context context, RequestBody title, RequestBody description, RequestBody featture, RequestBody quantity,
                         RequestBody gardenId, RequestBody IdDescription, RequestBody IsFixed, RequestBody NameSearch,
                         RequestBody SynonymOfFeature,
                         MultipartBody.Part newImages,
                         String token, CallBackData<String> callBackData);
    void getAllVegetableByGardenId(Context context, int gardenId, String token, CallBackData<List<VegetableData>> callBackData);
    void deleteVegetable(Context context, String vegetableId, String token, CallBackData<String> callBackData);
//    void updateVegetable(Context context, String idVeg, String title, String description, String feature, int quantity, int gardenId,
//                         List<MultipartBody.Part> newImages, String token, CallBackData<VegetableData> callBackData);
    void updateVegetable(Context context, UpdateVegetableRequest updateVegetableRequest, String token, CallBackData<VegetableData> callBackData);
    void getAllVegetableNeed(Context context, String token, CallBackData<List<VegetableNeedAll>> callBackData);
    void checkVegetableOfAccount(Context context, String vegetableNeedId, String vegetableNeedName, String token,
                                 CallBackData<List<VegetableData>> callBackData);

//    search
    void searchByName(Context context, String searchValue, String token, CallBackData<List<VegetableData>> callBackData);
    void searchByDescription(Context context, String searchValue, String token, CallBackData<List<VegetableData>> callBackData);
    void searchByKeyword(Context context, String searchValue, String token, CallBackData<List<VegetableData>> callBackData);
    void searchByWikiTitle(Context context, String searchValue, String token, CallBackData<List<WikiDataTitle>> callBackData);
    void getDescriptionWiki(Context context, String searchValue, String token, CallBackData<List<WikiData>> callBackData);

//    share - post
    void getAllShare(Context context, String token, CallBackData<List<PostData>> callBackData);
    void getAllShareById(Context context, String id, String token, CallBackData<List<PostData>> callBackData);
    void createPostShare(Context context, ShareRequest shareRequest, String token, CallBackData<ShareDetail> callBackData);
    void deleteShare(Context context, String shareId, String token, CallBackData<String> callBackData);
    void searchShareByDescription(Context context, String valueSearch, String token, CallBackData<List<PostSearchDescription>> callBackData);
    void searchShareByName(Context context, String valueSearch, String token, CallBackData<List<PostSearchName>> callBackData);
    void searchShareByKeyword(Context context, String valueSearch, String token, CallBackData<List<PostSearchKeyword>> callBackData);

//    exchange
    void isAcceptExchange(Context context, String id, int status, String token, CallBackData<String> callBackData);
    void deleteExchangeRequest(Context context, String exchangeId, String token, CallBackData<String> callBackData);
    void createExchange(Context context, ExchangeRequest exchangeRequest, String token, CallBackData<List<ExchangeData>> callBackData);
    void getAllExchange(Context context, String token, CallBackData<List<ExchangeData>> callBackData);
    void getHistoryExchange(Context context, String token, CallBackData<List<ExchangeData>> callBackData);
//    upload image
    void uploadImage(Context context, List<MultipartBody.Part> newItem, String token, CallBackData<ImageVegetable> callBackData);

    //address
    void getAllProvince(Context context, String token, CallBackData<List<ProvinceData>> callBackData);
    void getDistrictById(Context context, int id, String token, CallBackData<List<DistrictData>> callBackData);
    void getWardById(Context context, int id, String token, CallBackData<List<WardData>> callBackData);

    //Report post
    void reportPost(Context context, ReportPost reportPost, String token, CallBackData<String> callBackData);
    //QRCode
    void getQRCode(Context context, String exchangeId, String token, CallBackData<QRCodeData> callBackData);
}
