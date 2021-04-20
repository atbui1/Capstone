package com.example.democ.capstone;

import android.graphics.Bitmap;
import android.util.Base64;

import com.example.democ.utils.ConfigApi;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CapstoneService {

    //login
    @POST(ConfigApi.Api.ACCOUNT_LOGIN)
    @Headers("Content-Type:application/json; charset=utf-8")
    Call<ResponseBody> login(@Body RequestBody body);

    //account
    @POST(ConfigApi.Api.ACCOUNT_REGISTER)
    Call<ResponseBody> register(@Body RequestBody body);
    @GET(ConfigApi.Api.ACCOUNT_GET_INFO)
    Call<ResponseBody> getInfoAccount(@Header("Authorization") String token);
    @PUT(ConfigApi.Api.ACCOUNT_UPDATE)
    Call<ResponseBody> updateAccount(@Body RequestBody body, @Header("Authorization") String token);
    /*Friend*/
    @GET(ConfigApi.Api.ACCOUNT_FRIEND)
    Call<ResponseBody> getAllFriend(@Header("Authorization") String token);
    @GET(ConfigApi.Api.ACCOUNT_ADD_FRIEND)
    Call<ResponseBody> getAddFriendRequest(@Header("Authorization") String token);
    @POST(ConfigApi.Api.ACCOUNT_SEND_ADD_FRIEND)
    Call<ResponseBody> sendAddFriend(@Body RequestBody body, @Header("Authorization") String token);
    @PUT(ConfigApi.Api.ACCOUNT_SEND_ADD_FRIEND)
    Call<ResponseBody> replyFriendRequest(@Query("Id") int idRequest, @Query("status") int status, @Header("Authorization") String token);
    @PUT(ConfigApi.Api.ACCOUNT_DELETE_FRIEND)
    Call<ResponseBody> deleteFriend(@Query("Id") int idFriend, @Header("Authorization") String token);

    @GET(ConfigApi.Api.ACCOUNT_SEARCH_NAME)
    Call<ResponseBody> searchAccountByName(@Query("searchValue") String searchValue, @Header("Authorization") String token);

    //garden
    @Headers("Content-Type:application/json; charset=utf-8")
    @POST(ConfigApi.Api.CREATE_GARDEN)
    Call<ResponseBody> createGarden(@Body RequestBody body, @Header("Authorization") String token);

    @GET(ConfigApi.Api.GET_ALL_GARDEN)
    Call<ResponseBody> getAllGarden(@Header("Authorization") String token);
    @PUT(ConfigApi.Api.GARDEN)
    Call<ResponseBody> updateGarden(@Body RequestBody body, @Header("Authorization") String token);
    @DELETE(ConfigApi.Api.GARDEN)
    Call<ResponseBody> deleteGarden(@Query("Id") int gardenId, @Header("Authorization") String token);

//    Vegetable
    @Multipart
    @POST(ConfigApi.Api.CREATE_VEGETABLE)
    Call<ResponseBody> createVegetable(@Part("Title") RequestBody title,
                                       @Part("Description") RequestBody description,
                                       @Part("Featture") RequestBody featture,
                                       @Part("Quantity") RequestBody quantity,
                                       @Part("gardenId") RequestBody gardenId,
                                       @Part("IdDescription") RequestBody IdDescription,
                                       @Part("IsFixed") RequestBody IsFixed,
                                       @Part("NameSearch") RequestBody NameSearch,
                                       @Part("SynonymOfFeature") RequestBody SynonymOfFeature,
                                       @Part("Images") RequestBody Images,
                                       @Part MultipartBody.Part newImages,
                                       @Header("Authorization") String token);


    @GET(ConfigApi.Api.VEGETABLE_ALL_BY_GARDEN_ID)
    Call<ResponseBody> getAllVegetableByGardenId(@Query("GardenId") int gardenId, @Header("Authorization") String token);
    @DELETE(ConfigApi.Api.VEGETABLE)
    Call<ResponseBody> deleteVegetable(@Query("Id") String Id, @Header("Authorization") String token);

    @Multipart
    @PUT(ConfigApi.Api.VEGETABLE)
    Call<ResponseBody> updateVegetable(@Part("Id") RequestBody IdVeg,
                                       @Part("Title") RequestBody title,
                                       @Part("Description") RequestBody description,
                                       @Part("Featture") RequestBody featture,
                                       @Part("Quantity") RequestBody quantity,
                                       @Part("GardenId") RequestBody gardenId,
                                       @Part("Images") RequestBody Images,
                                       @Part MultipartBody.Part newImages,
                                       @Header("Authorization") String token);

    @GET(ConfigApi.Api.VEGETABLE_NEED_ALL)
    Call<ResponseBody> getAllVegetableNeed(@Header("Authorization") String token);
    @GET(ConfigApi.Api.VEGETABLE_CHECK)
    Call<ResponseBody> checkVegetableOfAccount(@Query("Id") String vegetableNeedId, @Query("Name") String vegetableNeedName,
                                               @Header("Authorization") String token);

    //search by name
    @GET(ConfigApi.Api.SEARCH_NAME)
    Call<ResponseBody> searchByName(@Query("searchValue") String searchValue, @Header("Authorization") String token);
    //Search by description
    @GET(ConfigApi.Api.SEARCH_DESCRIPTION)
    Call<ResponseBody> searchByDescription(@Query("searchValue") String searchValue, @Header("Authorization") String token);
    //Search by keyword
    @GET(ConfigApi.Api.SEARCH_KEYWORD)
    Call<ResponseBody> searchByKeyword(@Query("searchValue") String searchValue, @Header("Authorization") String token);
    //search wiki
    @GET(ConfigApi.Api.SEARCH_WIKI_TITLE)
    Call<ResponseBody> searchByWikiTitle(@Query("title") String searchValue, @Header("Authorization") String token);
    @GET(ConfigApi.Api.SEARCH_WIKI_DES)
    Call<ResponseBody> getDescriptionWiki(@Query("title") String searchValue, @Header("Authorization") String token);

    //post - share
    @GET(ConfigApi.Api.SHARE_ALL)
    Call<ResponseBody> getAllShare(@Header("Authorization") String token);
    @GET(ConfigApi.Api.SHARE_ALL_BY_ID)
    Call<ResponseBody> getAllShareById(@Query("Id") String Id, @Header("Authorization") String token);
//    tao bai post
    @POST(ConfigApi.Api.SHARE_CREATE_POST)
    Call<ResponseBody> createPostShare(@Body RequestBody body, @Header("Authorization") String token);
    @DELETE(ConfigApi.Api.SHARE_DELETE)
    Call<ResponseBody> deleteShare(@Query("Id") String shareId, @Header("Authorization") String token);
    @GET(ConfigApi.Api.SHARE_SEARCH_DESCRIPTION)
    Call<ResponseBody> searchShareByDescription(@Query("valueSearch") String valueSearch, @Header("Authorization") String token);
    @GET(ConfigApi.Api.SHARE_SEARCH_NAME)
    Call<ResponseBody> searchShareByName(@Query("valueSearch") String valuSearch, @Header("Authorization") String token);
    @GET(ConfigApi.Api.SHARE_SEARCH_KEYWORD)
    Call<ResponseBody> searchShareByKeyword(@Query("valueSearch") String valueSearch, @Header("Authorization") String token);

    //ExchangeDetail
    @POST(ConfigApi.Api.EXCHANGE)
    Call<ResponseBody> createExchange(@Body RequestBody body, @Header("Authorization") String token);
    @GET(ConfigApi.Api.EXCHANGE_ALL)
    Call<ResponseBody> getAllExchange(@Header("Authorization") String token);
    @PUT(ConfigApi.Api.EXCHANGE_IS_ACCEPT)
    Call<ResponseBody> replyExchangeRequest(@Query("Id") String exchangeId, @Query("Status") int Status, @Header("Authorization") String token);
    @GET(ConfigApi.Api.EXCHANGE_HISTORY)
    Call<ResponseBody> getHistoryExchange(@Header("Authorization") String token);
    @DELETE(ConfigApi.Api.EXCHANGE_DELETE_HISTORY)
    Call<ResponseBody> deleteHistoryExchange(@Query("Id") String exchangeId, @Header("Authorization") String token);

//    upload image
    @Multipart
    @POST(ConfigApi.Api.UPLOAD_IMAGE)
    Call<ResponseBody> uploadImage(@Part List<MultipartBody.Part> newItem, @Header("Authorization") String token);

    //Address
    @GET(ConfigApi.Api.PROVINCE)
    Call<ResponseBody> getAllProvince(@Header("Authorization") String token);
    @GET(ConfigApi.Api.DISTRICT)
    Call<ResponseBody> getDistrictByID(@Query("Id") int id, @Header("Authorization") String token);
    @GET(ConfigApi.Api.WARD)
    Call<ResponseBody> getWardById(@Query("Id") int id, @Header("Authorization") String token);

    //report
    @POST(ConfigApi.Api.REPORT_POST)
    Call<ResponseBody> reportPost(@Body RequestBody body, @Header("Authorization") String token);
    //QRCode
    @GET(ConfigApi.Api.QR_CODE)
    Call<ResponseBody> getQRCode(@Query("ExchangeId") String ExchangeId, @Header("Authorization") String token);
    @POST(ConfigApi.Api.QR_CODE)
    Call<ResponseBody> confirmExchangeFinish(@Query("ExchangeId") String exchangeId, @Header("Authorization") String token);



}
