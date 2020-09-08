package com.matteopasotti.whatmovie.util

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.matteopasotti.whatmovie.R

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url : String?) {

    val context = imageView.context
    val cd = ColorDrawable(context.resources.getColor(R.color.grey, null))

    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .placeholder(cd)
        .into(imageView)

}

@SuppressLint("CheckResult")
@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("circularImageUrl")
fun setCircularImageUrl(imageView: ImageView, url : String) {
    val requestOptions = RequestOptions()
    requestOptions.circleCrop()

    val context = imageView.context
    val cd = ColorDrawable(context.resources.getColor(R.color.grey, null))

    Glide
        .with(imageView.context)
        .load(url)
        .apply(requestOptions)
        .placeholder(cd)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}