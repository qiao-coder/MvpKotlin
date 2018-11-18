package com.tufei.base.util

import androidx.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * @author TuFei
 * @date 18-10-15.
 */

/**
 * 加载圆形图片
 */
fun ImageView.setCircleSrc(url: String) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().circleCrop())
        .into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.setCircleSrc(@DrawableRes drawableRes: Int) {
    Glide.with(context)
        .load(drawableRes)
        .apply(RequestOptions().circleCrop())
        .into(this)
}
