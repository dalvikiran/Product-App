package com.kiran.productapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kiran.productapp.R;
import com.kiran.productapp.adapters.ProductAdapter;
import com.kiran.productapp.databinding.ActivityMainBinding;
import com.kiran.productapp.models.Product;
import com.kiran.productapp.repositories.ProductRepository;
import com.kiran.productapp.utils.ViewModelFactory;
import com.kiran.productapp.view_models.ProductViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private ProductViewModel productViewModel;
    private ProductAdapter productAdapter;

    private LinearLayoutManager mLayoutManager;
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        RecyclerView recyclerView = activityMainBinding.productRecyclerView;

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        productRepository = ProductRepository.getInstance(getApplication(), true);
        ViewModelFactory viewModelFactory = new ViewModelFactory(productRepository);
        productViewModel = ViewModelProviders.of(this,viewModelFactory).get(ProductViewModel.class);

        activityMainBinding.setLifecycleOwner(this);

        productAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);

        productViewModel.getProducts();



        productViewModel.productListMutableLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                productAdapter.setProductList(products);
            }
        });
        
    }
}