package com.example.democ.utils;

import com.example.democ.capstone.CapstoneService;

public class ClientApi extends BaseApi {

    public CapstoneService capstoneService(){
        return this.getService(CapstoneService.class, ConfigApi.BASE_URL);
    }
}
