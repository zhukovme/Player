package com.zhukovme.player.ui.base

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

/**
 * Created by Michael Zhukov on 07.05.2018.
 * email: zhukovme@gmail.com
 */

//region TextView

@BindingAdapter("android:text")
fun setText(textView: TextView, resId: Int) {
    if (resId == 0) return
    textView.text = textView.resources.getString(resId)
}

//endregion

//region Toolbar

@BindingAdapter("android:title")
fun setTitle(toolbar: Toolbar, resId: Int) {
    if (resId == 0) return
    toolbar.setTitle(resId)
}

@BindingAdapter("android:subtitle")
fun setSubtitle(toolbar: Toolbar, resId: Int) {
    if (resId == 0) return
    toolbar.setSubtitle(resId)
}

//endregion

//region ImageView

//@BindingAdapter("android:src")
//fun setImageUri(view: ImageView, imageUri: String?) {
//    if (imageUri == null) {
//        view.setImageURI(null)
//    } else {
//        view.setImageURI(Uri.parse(imageUri))
//    }
//}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

//endregion
