package com.kiran.productapp.repositories;

import androidx.annotation.NonNull;

import com.kiran.productapp.models.Product;
import com.kiran.productapp.retrofit.network_utils.IDataSourceCallback;

import java.util.ArrayList;

public abstract class ProductDataSource {

    public void getData(@NonNull IDataSourceCallback<ArrayList<Product>> callback){}


}
