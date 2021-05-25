package com.kiran.productapp.repositories;

import android.app.Application;

import androidx.annotation.NonNull;

import com.kiran.productapp.models.Product;
import com.kiran.productapp.retrofit.network_utils.IDataSourceCallback;

import java.util.ArrayList;

public class ProductRepository extends ProductDataSource {

    private volatile static ProductRepository INSTANCE = null;
    private final ProductDataSource productRemoteDataSource;

    public ProductRepository(ProductDataSource productRemoteDataSource) {
        this.productRemoteDataSource = productRemoteDataSource;
    }

    public static ProductRepository getInstance(@NonNull Application mApplication, boolean initRemoteRepository) {
        if (INSTANCE == null) {
            synchronized (ProductRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProductRepository(initRemoteRepository ? ProductRemoteDataSource.getInstance(mApplication) : null);

                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getData(@NonNull IDataSourceCallback<ArrayList<Product>> callback) {
        productRemoteDataSource.getData(callback);
    }
}
