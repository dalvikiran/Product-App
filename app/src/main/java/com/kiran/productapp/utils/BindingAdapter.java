package com.kiran.productapp.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
public class BindingAdapter {

    @androidx.databinding.BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url){
        if (url != null && !url.isEmpty()){
            Glide.with(view.getContext())
                    .load(url)
                    .into(view);

        }
    }
}
