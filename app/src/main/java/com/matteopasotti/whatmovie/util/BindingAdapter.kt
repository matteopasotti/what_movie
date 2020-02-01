package com.matteopasotti.whatmovie.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url : String?) {
    val requestOptions = RequestOptions()
    requestOptions.centerCrop()

    if(url != null) {
        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }

}