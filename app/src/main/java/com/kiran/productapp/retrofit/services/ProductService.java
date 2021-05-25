package com.kiran.productapp.retrofit.services;

import com.kiran.productapp.retrofit.network_utils.NetworkURLs;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET(NetworkURLs.GET_DATA)
    Call<ResponseBody> getData();

}
