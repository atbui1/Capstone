package com.example.democ.utils;

public class ConfigApi {
    public static final String BASE_URL = "http://54.179.74.214:8080/";

    public interface Api {

        String ACCOUNT_LOGIN = "api/Login/MobileLogin";
        String ACCOUNT_REGISTER = "api/Account/Registration";
        String ACCOUNT_GET_INFO = "api/Account/GetAccountDetailByPhoneNumber";
        String ACCOUNT_UPDATE = "api/Account/Update";
        String ACCOUNT_ADD_FRIEND = "api/AccountRequest/GetAccountRequest";
        String ACCOUNT_SEND_ADD_FRIEND = "api/AccountRequest";
        String ACCOUNT_SEARCH_NAME = "api/Account/SearchAccount";

        String CREATE_GARDEN = "api/Garden";
        String GET_ALL_GARDEN = "api/Garden/GetByAccountId";
        String GARDEN = "api/Garden";
        String VEGETABLE = "api/Vegetable";
        String CREATE_VEGETABLE = "api/Vegetable";
        String VEGETABLE_ALL_BY_GARDEN_ID = "api/Vegetable/GetByGardenId";
        String VEGETABLE_NEED_ALL = "api/Vegetable/GetAllVegetable";
        String VEGETABLE_CHECK = "api/Vegetable/CheckVegetableInGarden";
        //Search
        String SEARCH_NAME = "api/Vegetable/SearchByName";
        String SEARCH_DESCRIPTION = "api/Vegetable/SearchByDescription";
        String SEARCH_KEYWORD = "api/Vegetable/SearchByKeyword";
        String SEARCH_WIKI = "LeakInfoFromWiki";

        //post -share
        String SHARE_CREATE_POST = "api/ShareDetail";
        String SHARE_ALL = "api/ShareDetail/GetAll";
        String SHARE_ALL_BY_ID = "api/ShareDetail/GetAllShareById";
        String SHARE_DELETE = "api/ShareDetail";

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

        //report
        String REPORT_POST = "api/Report";
    }
}
