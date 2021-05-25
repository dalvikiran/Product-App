package com.kiran.productapp.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kiran.productapp.models.Product;
import com.kiran.productapp.retrofit.NetworkController;
import com.kiran.productapp.retrofit.network_utils.IDataSourceCallback;
import com.kiran.productapp.retrofit.network_utils.NetworkResponseCallback;
import com.kiran.productapp.utils.network.InternetConnectionUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductRemoteDataSource extends ProductDataSource {

    private static ProductRemoteDataSource instance;
    private static NetworkController networkController;

    private static Context mContext;

    public static ProductRemoteDataSource getInstance(Context context) {
        mContext = context;
        networkController = NetworkController.getInstance(context);
        if (instance == null) {
            instance = new ProductRemoteDataSource();
        }
        return instance;
    }

    public ProductRemoteDataSource() {
    }

    @Override
    public void getData(@NonNull IDataSourceCallback<ArrayList<Product>> callback) {
        if (InternetConnectionUtil.getConnectivityStatus(mContext) > 0) {
            networkController.getData(new NetworkResponseCallback() {
                @Override
                public void onSuccess(String jsonData) {
                    if (jsonData != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(jsonData);
                            if (jsonArray.length() > 0) {
                                Gson gson = new Gson();
                                ArrayList<Product> products =
                                        gson.fromJson(jsonArray.toString(),
                                                new TypeToken<ArrayList<Product>>() {
                                                }.getType());

                                callback.onDataFound(products);
                            } else {
                                callback.onDataNotFound();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onError(int errorCode, String errorData) {
                    callback.onError(errorData);
                }
            });
        }else{
            Toast.makeText(mContext, "Please check your internet connection", Toast.LENGTH_LONG).show();

        }
    }

}
