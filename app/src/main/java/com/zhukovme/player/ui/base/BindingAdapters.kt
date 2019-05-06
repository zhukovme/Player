package com.zhukovme.player.ui.base

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Created by Michael Zhukov on 07.05.2018.
 * email: zhukovme@gmail.com
 */
//region View

@BindingAdapter("android:visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

//endregion

//region TextView

@BindingAdapter("android:text")
fun setText(textView: TextView, resId: Int) {
    if (resId == 0) return
    textView.text = textView.resources.getString(resId)
}

//endregion

//region ImageView

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

//endregion
