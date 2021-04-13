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
import com.example.democ.model.ProvinceData;
import com.example.democ.model.ReportPost;
import com.example.democ.model.ShareDetail;
import com.example.democ.model.ShareRequest;
import com.example.democ.model.UpdateVegetableRequest;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableNeedAll;
import com.example.democ.model.VegetableSearchDescription;
import com.example.democ.model.VegetableSearchKeyword;
import com.example.democ.model.WardData;
import com.example.democ.model.WikiData;
import com.example.democ.room.entities.User;
import com.example.democ.utils.CallBackData;
import com.example.democ.utils.ClientApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CapstoneRepositoryImp implements CapstoneRepository {

    private static List<GardenResult> mListGarden;
    private static List<VegetableData> mListVegetable;
    private static List<VegetableSearchDescription> mListSearchDescription;
    private static List<VegetableSearchKeyword> mListSearchKeyword;
    private static List<PostData> mListPost;
    private static List<ExchangeData> mListExchange;
    private static List<VegetableNeedAll> mListVegetableNeed;
    private static List<AddFriendRequest> mListAddFriendRequest;
    private static List<ProvinceData> mListProvince;
    private static List<DistrictData> mListDistrict;
    private static List<WardData> mListWard;
    private static List<WikiData> mListWiki;
    private static List<AccountSearchByName> mListAccount;

    @Override
    public void login(Context context, String userName, String password, String deviceToken, final CallBackData<User> callBackData) {
        ClientApi clientApi = new ClientApi();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneNumber", userName);
            jsonObject.put("password", password);
            jsonObject.put("deviceToken", deviceToken);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ResponseBody> serviceCall = clientApi.capstoneService().login(body);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        User responseResult = new Gson().fromJson(result, User.class);
                        if (responseResult == null) {
                            callBackData.onFail(response.message());
                        } else {
                            callBackData.onSuccess(responseResult);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void register(final Context context, Account account, final CallBackData<Account> callBackData) {
        ClientApi clientApi = new ClientApi();
        final Gson gson = new Gson();
        String json = gson.toJson(account);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> serviceCall = clientApi.capstoneService().register(requestBody);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        Account accountResult = gson.fromJson(result, Account.class);
                        if (accountResult != null) {
                            callBackData.onSuccess(accountResult);
                        } else {
                            callBackData.onFail("");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void getInfoAccount(Context context, String token, final CallBackData<Account> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().getInfoAccount("Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONObject jsonObject = new JSONObject(result);
                        Account account = new Gson().fromJson(jsonObject.toString(), Account.class);
                        if (account == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(account);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });

    }

    @Override
    public void updateAccount(Context context, Account account, String token, final CallBackData<Account> callBackData) {
        ClientApi clientApi = new ClientApi();
        final Gson gson = new Gson();
        String json = gson.toJson(account);
        final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> serviceCall = clientApi.capstoneService().updateAccount(requestBody, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        Account accountResponse = gson.fromJson(result, Account.class);
                        if (accountResponse == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(accountResponse);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void getAddFriendRequest(Context context, String token, final CallBackData<List<AddFriendRequest>> callBackData) {
        mListAddFriendRequest = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().getAddFriendRequest("Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            AddFriendRequest addFriendRequest = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), AddFriendRequest.class);
                            mListAddFriendRequest.add(addFriendRequest);
                        }
                        if (mListAddFriendRequest == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListAddFriendRequest);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void sendAddFriend(Context context, final AddFriendRequest addFriendRequest, String token, final CallBackData<AddFriendRequest> callBackData) {
        ClientApi clientApi = new ClientApi();
        final Gson gson = new Gson();
        final String json = gson.toJson(addFriendRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> serviceCall = clientApi.capstoneService().sendAddFriend(requestBody, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONObject jsonObject = new JSONObject(result);
                        AddFriendRequest addFriendResponse = gson.fromJson(jsonObject.toString(), AddFriendRequest.class);
                        if (addFriendRequest == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(addFriendResponse); }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void searchAccountByName(Context context, String searchValue, String token, final CallBackData<List<AccountSearchByName>> callBackData) {
        mListAccount = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().searchAccountByName(searchValue, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            AccountSearchByName data = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), AccountSearchByName.class);
                            mListAccount.add(data);
                        }
                        if (mListAccount == null) {
                            callBackData.onFail("");
                            System.out.println("FFFFFFFFFFFFFFFFFFF  search name account fail FFFFFFFFFFFFFFFFF");
                        } else {
                            callBackData.onSuccess(mListAccount);
                            System.out.println("SSSSSSSSSSSSSSSSSS  search name account success SSSSSSSSSSSSSSSSSSSSSSS");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                    System.out.println("FFFFFFFFFFFFFFFF ngoai if search name account FFFFFFFFFFFFFFFFFFFFFFFFFF");
                    System.out.println("code: " + response.code());
                    System.out.println("FFFFFFFFFFFFFFFF ngoai if search name account FFFFFFFFFFFFFFFFFFFFFFFFFF");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("FFFFFFFFFFFFFFFFFFFFFFF  search name account onFailure    FFFFFFFFFFFFFFFFFFFFFFFF");
                callBackData.onFail("");
            }
        });
    }


    @Override
    public void createGarden(final Context context, Garden garden, String token, final CallBackData<GardenResult> callBackData) {
        ClientApi clientApi = new ClientApi();
        final Gson gson = new Gson();
        String json = gson.toJson(garden);
        final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> serviceCall = clientApi.capstoneService().createGarden(requestBody, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONObject jsonObject = new JSONObject(result);
                        GardenResult gardenResult = gson.fromJson(jsonObject.toString(), GardenResult.class);
                        if (gardenResult != null) {
                            callBackData.onSuccess(gardenResult);
                        } else {
                            callBackData.onFail("tao vuon rau fail dong 130");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void getAllGarden(final Context context, String token, final CallBackData<List<GardenResult>> callBackData) {
        mListGarden = new ArrayList<>();
        try{
            ClientApi clientApi = new ClientApi();
            Call<ResponseBody> serviceCall = clientApi.capstoneService().getAllGarden("Bearer " + token);
            serviceCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200 && response.body() != null) {
                        try {
                            String result = response.body().string();
                            JSONArray jsonArray = new JSONArray(result);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                GardenResult gardenResult = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), GardenResult.class);
                                mListGarden.add(gardenResult);
                            }

                            if (mListGarden == null) {
                                callBackData.onFail("");
                            } else {
                                callBackData.onSuccess(mListGarden);
                            }
//                            String result = response.body().string();
//                            JSONArray jsonArray = new JSONArray(result);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                System.out.println(jsonObject);
//                                System.out.println("Id " + jsonObject.getString("id"));
//                                System.out.println("name " + jsonObject.getString("name"));
//                                System.out.println("address " + jsonObject.getString("address"));
//                                System.out.println("accountId " + jsonObject.getString("accountId"));
//                                System.out.println("------------- app account----------------");
//                                System.out.println("appAccount " + jsonObject.getString("appAccount"));
//                                System.out.println("************* app account **********************");
//                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        callBackData.onFail("");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callBackData.onFail("");
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateGarden(Context context, final Garden garden, String token, final CallBackData<Garden> callBackData) {
        ClientApi clientApi = new ClientApi();
        final Gson gson = new Gson();
        final String json = gson.toJson(garden);
        final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> serviceCall = clientApi.capstoneService().updateGarden(requestBody, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == 200 && response.body() != null) {
                        try {
                            String result = response.body().string();
                            JSONObject jsonObject = new JSONObject(result);
                            Garden gardenUpdate = gson.fromJson(jsonObject.toString(), Garden.class);
                            if (gardenUpdate != null) {
                                callBackData.onSuccess(gardenUpdate);
                            } else {
                                callBackData.onFail("");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        callBackData.onFail("");
                    }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void deleteGarden(Context context, int gardenId, String token, final CallBackData<String> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().deleteGarden(gardenId, "Bearer " + token);

        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    callBackData.onSuccess("Delete garden success");
                } else {
                    callBackData.onFail("Delete garden fail");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void createVegetable(final Context context, final RequestBody title, final RequestBody description,
                                final RequestBody featture, final RequestBody quantity, final RequestBody gardenId,
                                final RequestBody IdDescription, final RequestBody IsFixed, final RequestBody NameSearch,
                                final RequestBody SynonymOfFeature,
                                final MultipartBody.Part newImages,
                                final String token, final CallBackData<String> callBackData) {
        ClientApi clientApi = new ClientApi();
        final Gson gson = new Gson();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().createVegetable( title, description, featture, quantity, gardenId,
                IdDescription, IsFixed, NameSearch, SynonymOfFeature, newImages,"Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
//                        String result = response.body().string();
//                        JSONObject jsonObject = new JSONObject(result);
//                        VegetableData vegetableData = gson.fromJson(jsonObject.toString(), VegetableData.class);
//                        if (vegetableData != null) {
//                            callBackData.onSuccess(vegetableData);
//                        } else {
//                            callBackData.onFail("");
//                        }
                        callBackData.onSuccess("");
                        System.out.println("SSSSSSSSSSSSSSSs tao rau thanh cong SSSSSSSSSSSSSSSSSSSSSSSS");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                    System.out.println("FFFFFFFFFFFFFFFFF tao rau fail       FFFFFFFFFFFFFFFFFFFF");
                    System.out.println("code: " + response.code());
                    System.out.println("FFFFFFFFFFFFFFFFF tao rau fail       FFFFFFFFFFFFFFFFFFFF");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
                System.out.println("FFFFFFFFFFFFFFF tao rau loi server     FFFFFFFFFFFFFF");
                System.out.println("FFFFFFFFFFFFFFF tao rau loi server     FFFFFFFFFFFFFF");
            }
        });
    }


    @Override
    public void getAllVegetableByGardenId(Context context, int gardenId, String token, final CallBackData<List<VegetableData>> callBackData) {
        mListVegetable = new ArrayList<>();
        try {
            ClientApi clientApi = new ClientApi();
            Call<ResponseBody> serviceCall = clientApi.capstoneService().getAllVegetableByGardenId(gardenId, "Bearer " + token);
            serviceCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200 && response.body() != null) {
                        try {
                            String result = response.body().string();
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                VegetableData vegetableData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), VegetableData.class);
                                mListVegetable.add(vegetableData);
                            }
                            if (mListVegetable == null) {
                                callBackData.onFail("");
                            } else {
                                callBackData.onSuccess(mListVegetable);
                                System.out.println("getAllVegetableByGardenId capstoneRepisi size: " + mListVegetable.size());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        callBackData.onFail("");
                        System.out.println("getAllVegetableByGardenId capstoneRepisi else code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callBackData.onFail("");
                    System.out.println("getAllVegetableByGardenId capstoneRepisi onFailure:");
                    System.out.println("getAllVegetableByGardenId capstoneRepisi onFailure: " + t);
                    System.out.println("getAllVegetableByGardenId capstoneRepisi onFailure:");
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteVegetable(Context context, int noVeg, int gardenId, String token, final CallBackData<String> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().deleteVegetable(noVeg, gardenId, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    callBackData.onSuccess("");
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void updateVegetable(Context context, UpdateVegetableRequest updateVegetableRequest, String token, final CallBackData<VegetableData> callBackData) {
        ClientApi clientApi = new ClientApi();
        Gson gson = new Gson();
        String json = gson.toJson(updateVegetableRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> serviceCall = clientApi.capstoneService().updateVegetable(requestBody, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
//                        callBackData.onSuccess("");
                        System.out.println("SSSSSSSSSSSSSSSSS update SSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                    System.out.println("FFFFFFFFFFF ngoai if FFFFFFFFFFFFFFFFFFFFFFFFFF");
                    System.out.println("response code: " + response.code());
                    System.out.println("FFFFFFFFFFF ngoai if FFFFFFFFFFFFFFFFFFFFFFFFFF");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
                System.out.println("FFFFFFFFFFFFFFFF update vegetable  loi server FFFFFFFFFFFFFFFFFFFFFFFFF");
            }
        });
    }

//    @Override
//    public void updateVegetable(Context context, String idVeg, String title, String description, String feature,
//                                int quantity, int gardenId, List<MultipartBody.Part> newImages, String token,
//                                final CallBackData<VegetableData> callBackData) {
//        ClientApi clientApi = new ClientApi();
//        final Gson gson = new Gson();
//        Call<ResponseBody> serviceCall = clientApi.capstoneService().updateVegetable(idVeg, title, description, feature, quantity,
//                gardenId, newImages, "Bearer " + token);
//        serviceCall.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.code() == 200 && response.body() != null) {
//                    System.out.println("SSSSSSSSSSSSSSS     chay vo if SSSSSSSSSSSSSSSSSSSs");
//                    System.out.println("response code: " + response.code());
//                    System.out.println("SSSSSSSSSSSSSSS     chay vo if SSSSSSSSSSSSSSSSSSSs");
//                    try {
//                        String result = response.body().string();
//                        JSONObject jsonObject = new JSONObject(result);
//                        VegetableData vegetableResponse = gson.fromJson(jsonObject.toString(), VegetableData.class);
//                        if (vegetableResponse == null) {
//                            callBackData.onFail("");
//                            System.out.println("FFFFFFFFFFFFFFF   update vegetable fail FFFFFFFFFFFFFFFFF");
//                        } else {
//                            System.out.println("SSSSSSSSSSSSSSS         update vegetable success SSSSSSSSSSSSSSSSSSSSSSSSSS");
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                } else {
//                    callBackData.onFail("");
//                    System.out.println("FFFFFFFFFFFFF       update vegetable faill ngoai if FFFFFFFFFFFFF");
//                    System.out.println("response code: " + response.code());
//                    System.out.println("FFFFFFFFFFFFF       update vegetable faill ngoai if FFFFFFFFFFFFF");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                callBackData.onFail("");
//                System.out.println("FFFFFFFFFFFFFFF     onFailure update vegetable loi server     FFFFFFFFFFFFFFFFF");
//            }
//        });
//    }


    /* update vegetable */


    @Override
    public void getAllVegetableNeed(Context context, String token, final CallBackData<List<VegetableNeedAll>> callBackData) {
        mListVegetableNeed = new ArrayList<>();
        try {
            ClientApi clientApi = new ClientApi();
            Call<ResponseBody> serviceCall = clientApi.capstoneService().getAllVegetableNeed("Bearer " + token);
            serviceCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200 && response.body() != null) {
                        try {
                            String result = response.body().string();
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i <jsonArray.length(); i++) {
                                VegetableNeedAll vegetableNeedAll = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), VegetableNeedAll.class);
                                mListVegetableNeed.add(vegetableNeedAll);
                            }
                            if (mListVegetableNeed == null) {
                                callBackData.onFail("");
                            } else {
                                callBackData.onSuccess(mListVegetableNeed);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        callBackData.onFail("");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callBackData.onFail("");
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void checkVegetableOfAccount(Context context, String vegetableNeedId, final String vegetableNeedName, String token, final CallBackData<List<VegetableData>> callBackData) {
        mListVegetable = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().checkVegetableOfAccount(vegetableNeedId, vegetableNeedName,
                "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            VegetableData vegetableData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), VegetableData.class);
                            mListVegetable.add(vegetableData);
                        }
                        if (mListVegetable == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListVegetable);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }


    @Override
    public void searchByName(Context context, String searchValue, String token, final CallBackData<List<VegetableData>> callBackData) {
        mListVegetable = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().searchByName(searchValue, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            VegetableData vegetableData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), VegetableData.class);
                            mListVegetable.add(vegetableData);
                        }
                        if (mListVegetable == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListVegetable);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void searchByDescription(Context context, String searchValue, String token, final CallBackData<List<VegetableData>> callBackData) {
        mListVegetable =  new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().searchByDescription(searchValue, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            VegetableData vegetableData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(),
                                    VegetableData.class);
                            mListVegetable.add(vegetableData);
                        }
                        if (mListVegetable == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListVegetable);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void searchByKeyword(Context context, String searchValue, String token, final CallBackData<List<VegetableData>> callBackData) {
        mListVegetable =  new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().searchByKeyword(searchValue, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            VegetableData vegetableData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(),
                                    VegetableData.class);
                            mListVegetable.add(vegetableData);
                        }
                        if (mListVegetable == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListVegetable);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void searchByWiki(Context context, String searchValue, String token, final CallBackData<List<WikiData>> callBackData) {
        mListWiki = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().searchByWiki(searchValue, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            WikiData wikiData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), WikiData.class);
                            mListWiki.add(wikiData);
                        }
                        if (mListWiki == null) {
                            callBackData.onFail("");
                            System.out.println("FFFFFFFFFFFFFFFF wiki search FFFFFFFFFFFFFFFFFFFFF");
                        } else {
                            callBackData.onSuccess(mListWiki);
                            System.out.println("SSSSSSSSSSSSs wiki search SSSSSSSSSSSSSSSSSS");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }


    //        share - post
    @Override
    public void getAllShare(Context context, String token, final CallBackData<List<PostData>> callBackData) {
        mListPost = new ArrayList<>();
        try {
            ClientApi clientApi = new ClientApi();
            Call<ResponseBody> serviceCall = clientApi.capstoneService().getAllShare("Bearer " + token);
            serviceCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200 && response.body() != null) {
                        try {
                            String result = response.body().string();
                            JSONArray jsonArray = new JSONArray(result);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                PostData postData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), PostData.class);
                                mListPost.add(postData);
                            }

                            if (mListPost == null) {
                                callBackData.onFail("");
                            } else {
                                callBackData.onSuccess(mListPost);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        callBackData.onFail("");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callBackData.onFail("");
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void getAllShareById(Context context, String token, final CallBackData<List<PostData>> callBackData) {
        mListPost = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().getAllShareById("Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            PostData postData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), PostData.class);
                            mListPost.add(postData);
                        }
                        if (mListPost ==  null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListPost);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    //        create post share
    @Override
    public void createPostShare(Context context, ShareRequest shareRequest, String token, final CallBackData<ShareDetail> callBackData) {
        ClientApi clientApi = new ClientApi();
        final Gson gson = new Gson();
        String json = gson.toJson(shareRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> serviceCall = clientApi.capstoneService().createPostShare(requestBody, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONObject jsonObject = new JSONObject(result);
                        ShareDetail shareDetail = gson.fromJson(jsonObject.toString(), ShareDetail.class);
                        if (shareDetail != null) {
                            callBackData.onSuccess(shareDetail);
                            System.out.println("SSSSSSSSSSSSSSS create post success SSSSSSSSSSSSSSSSSSSSSSSS");
                        } else {
                            callBackData.onFail("");
                            System.out.println("FFFFFFFFFFFFFFFFF cretae post fail FFFFFFFFFFFFFFFFFFFFFFFF");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                    System.out.println("FFFFFFFFFFFFFFFFF cretae post fail ngoai if FFFFFFFFFFFFFFFFFFFFFFFF");
                    System.out.println("response code: " + response.code());
                    System.out.println("FFFFFFFFFFFFFFFFF cretae post fail ngoai if FFFFFFFFFFFFFFFFFFFFFFFF");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
                System.out.println("FFFFFFFFFFFFFFFFF cretae post fail onFailure FFFFFFFFFFFFFFFFFFFFFFFF");
            }
        });
    }

    @Override
    public void deleteShare(Context context, String shareId, String token, final CallBackData<String> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().deleteShare(shareId, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    callBackData.onSuccess("");
                    System.out.println("SSSSSSSSSSSSSS xoa bai share thanh cong SSSSSSSSSSSSSSSSSSSSS");
                } else {
                    callBackData.onFail("");
                    System.out.println("FFFFFFFFFFFFF xoa bai dang khg thanh cong FFFFFFFFFFFFFFFFFFFFFF");
                    System.out.println("code: " + response.code());
                    System.out.println("FFFFFFFFFFFFF xoa bai dang khg thanh cong FFFFFFFFFFFFFFFFFFFFFF");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("FFFFFFFFF xoa bai dang onFailure FFFFFFFFFFFFFFF");
                callBackData.onFail("");
            }
        });
    }

    //isAcceptExchange
    @Override
    public void isAcceptExchange(Context context, int status, ArrayList<String> exchangeId, String token, final CallBackData<String> callBackData) {
        ClientApi clientApi = new ClientApi();
        Gson gson = new Gson();
        String json = gson.toJson(exchangeId);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> serviceCall = clientApi.capstoneService().isAcceptExchange(status, requestBody, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.headers() != null) {
                    try {
                        callBackData.onSuccess("");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void deleteExchangeRequest(Context context, String exchangeId, String token, final CallBackData<String> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().deleteExchangeRequest(exchangeId, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.headers() != null) {
                    try {
                        callBackData.onSuccess("");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void createExchange(Context context, ExchangeRequest exchangeRequest, String token, final CallBackData<List<ExchangeData>> callBackData) {
        mListExchange = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        final Gson gson = new Gson();
        String json = gson.toJson(exchangeRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> serviceCall = clientApi.capstoneService().createExchange(requestBody, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ExchangeData exchangeData = gson.fromJson(jsonArray.getJSONObject(i).toString(), ExchangeData.class);
                            mListExchange.add(exchangeData);
                        }

                        if (mListExchange == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListExchange);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void getAllExchange(Context context, String token, final CallBackData<List<ExchangeData>> callBackData) {
        mListExchange = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().getAllExchange("Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ExchangeData exchangeData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), ExchangeData.class);
                            mListExchange.add(exchangeData);
                        }
                        if (mListExchange == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListExchange);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }


    //        upload image
    @Override
    public void uploadImage(Context context, List<MultipartBody.Part> newItem, String token, final CallBackData<ImageVegetable> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().uploadImage(newItem, "Bearer " + token);
        System.out.println("11111111111111111111111111111111111111111111111");
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONObject jsonObject = new JSONObject(result);
                        ImageVegetable imageVegetable = new Gson().fromJson(jsonObject.toString(), ImageVegetable.class);
                        if (imageVegetable == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(imageVegetable);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void getAllProvince(Context context, String token, final CallBackData<List<ProvinceData>> callBackData) {
        mListProvince = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().getAllProvince("Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ProvinceData provinceData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), ProvinceData.class);
                            mListProvince.add(provinceData);
                        }
                        if (mListProvince == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListProvince);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("");
            }
        });
    }

    @Override
    public void getDistrictById(Context context, int id, String token, final CallBackData<List<DistrictData>> callBackData) {
        mListDistrict = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().getDistrictByID(id, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            DistrictData districtData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), DistrictData.class);
                            mListDistrict.add(districtData);
                        }
                        if (mListDistrict == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListDistrict);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void getWardById(Context context, int id, String token, final CallBackData<List<WardData>> callBackData) {
        mListWard = new ArrayList<>();
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.capstoneService().getWardById(id, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            WardData wardData = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), WardData.class);
                            mListWard.add(wardData);
                        }
                        if (mListWard == null) {
                            callBackData.onFail("");
                        } else {
                            callBackData.onSuccess(mListWard);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /* report post*/
    @Override
    public void reportPost(Context context, ReportPost reportPost, String token, final CallBackData<String> callBackData) {
        ClientApi clientApi = new ClientApi();
        Gson gson = new Gson();
        String json = gson.toJson(reportPost);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> serviceCall = clientApi.capstoneService().reportPost(requestBody, "Bearer " + token);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        callBackData.onSuccess("");
                        System.out.println("SSSSSSSSSSSSSS report success SSSSSSSSSSSSSSSSSSSSS");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    callBackData.onFail("");
                    System.out.println("FFFFFFFFFFFFFFFFFFF     report fail ngoai if FFFFFFFFFFFFFFFFFFFFFF");
                    System.out.println("response code: " + response.code());
                    System.out.println("FFFFFFFFFFFFFFFFFFF     report fail ngoai if FFFFFFFFFFFFFFFFFFFFFF");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("FFFFFFFFFFFFF onFailure report loi server FFFFFFFFFFFFFFFFFFFFFF");
            }
        });
    }

}
