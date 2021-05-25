package com.kiran.productapp.retrofit.network_utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kiran.productapp.utils.CustomGsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    public static final int TIMEOUT = 60 * 1000;

    public static final int HTTP_SUCCESS = 200;
    public static final int HTTP_404 = 404;
    public static final int HTTP_500 = 500;
    public static final int HTTP_RETROFIT_FAILURE = 0;

    public static Retrofit buildRetrofit(boolean defaultRequest, Context context) {
        final OkHttpClient client = getOkHttpClient(context);

        if (!defaultRequest) {
            return new Retrofit.Builder()
                    .baseUrl(NetworkURLs.SERVER_URL)
                    .client(client.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).readTimeout(TIMEOUT, TimeUnit.SECONDS).writeTimeout(TIMEOUT, TimeUnit.SECONDS).build())
                    .addConverterFactory(GsonConverterFactory.create(CustomGsonBuilder.getInstance().create()))
                    .build();
        } else {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            return new Retrofit.Builder()
                    .baseUrl(NetworkURLs.SERVER_URL)
                    .client(client.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).readTimeout(TIMEOUT, TimeUnit.SECONDS).writeTimeout(TIMEOUT, TimeUnit.SECONDS).build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
    }

    public static OkHttpClient getOkHttpClient(Context context) {
        final OkHttpClient client = new OkHttpClient();


        return client;
    }

    public static String getStringResponseFromRaw(ResponseBody response) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {

            reader = new BufferedReader(new InputStreamReader(response.byteStream()));

            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static JSONObject getJsonResponseFromRaw(Response<ResponseBody> response) {

        try {
            return new JSONObject(getStringResponseFromRaw(response));
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getStringResponseFromRaw(Response<ResponseBody> response) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {

            reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));

            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



}
