package com.rsa.contacts.view.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.rsa.contacts.R

@BindingAdapter("loadImage")
fun ImageView.loadImage(photo: Uri?) {
    Glide
        .with(this.context)
        .load(photo)
        .placeholder(R.drawable.profile_placeholder)
        .into(this)
}