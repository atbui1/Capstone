package com.example.democ.utils;

public class ConfigApi {
    public static final String BASE_URL = "http://54.179.74.214:8080/";

    public interface Api {

        String REGISTER = "api/Account/Registration";
        String LOGIN = "api/Login/MobileLogin";
        String GET_INFO_ACCOUNT = "api/Account/GetAccountDetailByPhoneNumber";
        String UPDATE_ACCOUNT = "api/Account/Update";
        String ADD_FRIEND = "api/AccountRequest/GetAccountRequest";
        String SEND_ADD_FRIEND = "api/AccountRequest";

        String CREATE_GARDEN = "api/Garden";
        String GET_ALL_GARDEN = "api/Garden/GetByAccountId";
        String GARDEN = "api/Garden";
        String VEGETABLE = "api/Vegetable";
        String CREATE_VEGETABLE = "api/Vegetable";
        String CREATE_VEGETABLE_URL = "api/Vegetable";
        String VEGETABLE_ALL_BY_GARDEN_ID = "api/Vegetable/GetByGardenId";
        String VEGETABLE_NEED_ALL = "api/Vegetable/GetAllVegetable";
        String VEGETABLE_CHECK = "api/Vegetable/CheckVegetableInGarden";
        //Search
        String SEARCH_NAME = "api/Vegetable/SearchByName";
        String SEARCH_DESCRIPTION = "api/Vegetable/SearchByDescription";
        String SEARCH_KEYWORD = "api/Vegetable/SearchByKeyword";
        String SEARCH_WIKI = "LeakInfoFromWiki";

        //post -share
        String CREATE_POST_SHARE = "api/ShareDetail";
        String SHARE_ALL = "api/ShareDetail/GetAll";
        String SHARE_ALL_BY_ID = "api/ShareDetail/GetAllShareById";

        //exchange
        String EXCHANGE = "api/ExchangeDetail";
        String EXCHANGE_ALL = "api/ExchangeDetail/GetExchangeRequest";
        String EXCHANGE_IS_ACCEPT = "api/ExchangeDetail/IsAccept";
        String EXCHANGE_DELETE_REQUEST = "api/ExchangeDetail";

        //upload image
        String UPLOAD_IMAGE = "api/Vegetable/Image";

        //Address
        String PROVINCE = "api/Province";
        String DISTRICT = "api/District";
        String WARD = "api/Ward";
    }
}
