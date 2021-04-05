package com.example.democ.utils;

public class ConfigApi {
    public static final String BASE_URL = "http://54.179.74.214:8080/api/";

    public interface Api {

        String REGISTER = "Account/Registration";
        String LOGIN = "Login/MobileLogin";
        String GET_INFO_ACCOUNT = "Account/GetAccountDetailByPhoneNumber";
        String UPDATE_ACCOUNT = "Account/Update";
        String ADD_FRIEND = "AccountRequest/GetAccountRequest";
        String SEND_ADD_FRIEND = "AccountRequest";

        String CREATE_GARDEN = "Garden";
        String GET_ALL_GARDEN = "Garden/GetByAccountId";
        String GARDEN = "Garden";
        String VEGETABLE = "Vegetable";
        String CREATE_VEGETABLE = "Vegetable";
        String CREATE_VEGETABLE_URL = "Vegetable";
        String VEGETABLE_ALL_BY_GARDEN_ID = "Vegetable/GetByGardenId";
        String VEGETABLE_NEED_ALL = "Vegetable/GetAllVegetable";
        String VEGETABLE_CHECK = "Vegetable/CheckVegetableInGarden";
        //Search
        String SEARCH_NAME = "Vegetable/SearchByName";
        String SEARCH_DESCRIPTION = "Vegetable/SearchByDescription";
        String SEARCH_KEYWORD = "Vegetable/SearchByKeyword";

        //post -share
        String CREATE_POST_SHARE = "ShareDetail";
        String SHARE_ALL = "ShareDetail/GetAll";
        String SHARE_ALL_BY_ID = "ShareDetail/GetAllShareById";

        //exchange
        String EXCHANGE = "ExchangeDetail";
        String EXCHANGE_ALL = "ExchangeDetail/GetExchangeRequest";
        String EXCHANGE_IS_ACCEPT = "ExchangeDetail/IsAccept";
        String EXCHANGE_DELETE_REQUEST = "ExchangeDetail";

        //upload image
        String UPLOAD_IMAGE = "Vegetable/Image";

        //Address
        String PROVINCE = "Province";
        String DISTRICT = "District";
        String WARD = "Ward";
    }
}
