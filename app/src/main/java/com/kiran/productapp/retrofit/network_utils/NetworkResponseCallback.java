package com.kiran.productapp.retrofit.network_utils;

public interface NetworkResponseCallback {

    void onSuccess(String jsonData);

    void onError(int errorCode, String errorData);

}
