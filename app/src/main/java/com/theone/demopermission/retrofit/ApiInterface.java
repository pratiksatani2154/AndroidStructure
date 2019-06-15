package com.theone.demopermission.retrofit;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hemant on 08-04-2019,
 * at TheOneTechnologies
 */

public interface ApiInterface {


    @GET("api/v2/eventGate/sites/GetAllCurrency")
    Call<JsonArray> getAllCurrency();


}
