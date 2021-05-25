package com.kiran.productapp.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kiran.productapp.models.Product;
import com.kiran.productapp.repositories.ProductRepository;
import com.kiran.productapp.retrofit.network_utils.IDataSourceCallback;

import java.util.ArrayList;

public class ProductViewModel extends ViewModel {

//    private static ProductViewModel productViewModel;
//    private Context context;
    private ProductRepository productRepository;
    public MutableLiveData<ArrayList<Product>> productListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> loading = new MutableLiveData<>(Boolean.FALSE);

    public ProductViewModel(ProductRepository repository) {
        productRepository = repository;
    }
/*

    public ProductViewModel(@NonNull Application application) {
        super(application);
        context = application;

        productRepository = ProductRepository.getInstance(application, true);
    }


    public static synchronized ProductViewModel getInstance(Application application) {
        if (productViewModel == null) {
            productViewModel = new ProductViewModel(application);
            return productViewModel;
        }
        return productViewModel;
    }*/

    public void getProducts(){

        loading.setValue(true);
        productRepository.getData(new IDataSourceCallback<ArrayList<Product>>() {
            @Override
            public void onDataFound(ArrayList<Product> data) {
                productListMutableLiveData.setValue(data);
            }

            @Override
            public void onDataNotFound() {
                errorMessage.setValue( "No products found");
            }

            @Override
            public void onError(String error) {
//                Toast.makeText(context, "Error : " + error, Toast.LENGTH_LONG).show();
                errorMessage.setValue( "Error : " + error);
            }
        });


    }

}
