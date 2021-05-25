package com.kiran.productapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.kiran.productapp.R;
import com.kiran.productapp.databinding.ProductItemLayoutBinding;
import com.kiran.productapp.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    ArrayList<Product> productArrayList = new ArrayList<>();

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setProductList(ArrayList<Product> products){
        this.productArrayList = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ProductItemLayoutBinding productItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.product_item_layout,parent,false);
        return new ProductViewHolder(productItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = productArrayList.get(position);
        if (product != null){
            holder.productItemLayoutBinding.setProduct(product);

        }
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ProductItemLayoutBinding productItemLayoutBinding;

        public ProductViewHolder(@NonNull ProductItemLayoutBinding productItemLayoutBinding) {
            super(productItemLayoutBinding.getRoot());
            this.productItemLayoutBinding = productItemLayoutBinding;
        }
    }



}
