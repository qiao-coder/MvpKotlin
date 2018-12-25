package com.tufei.base.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * @author TuFei
 * @date 18-9-27.
 */

private var toast: Toast? = null

enum class ToastTime {
    LONG, SHORT
}

@SuppressLint("ShowToast")
@JvmName("toast")
fun Context.showToast(tip: String, toastTime: ToastTime) {
    val time = when (toastTime) {
        ToastTime.LONG -> Toast.LENGTH_LONG
        ToastTime.SHORT -> Toast.LENGTH_SHORT
    }
    toast?.apply {
        setText(tip)
    } ?: apply {
        toast = Toast.makeText(applicationContext, tip, time)
    }
    toast?.show()
}

fun Context.getDrawableCompat(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

fun Context.getColorCompat(@ColorRes id: Int) = ContextCompat.getColor(this, id)

/**
 * 简写LayoutInflater.from(this).inflate(resource, null)
 *
 * @param resource layoutId
 * @return View
 */
fun Context.inflater(@LayoutRes resource: Int): View =
    LayoutInflater.from(this).inflate(resource, null)

/**
 * 简写LayoutInflater.from(this).inflate(resource, root, attachToRoot)
 *
 * @param resource layoutId
 * @param root root
 * @param attachToRoot attachToRoot
 * @return View
 */
fun Context.inflater(@LayoutRes resource: Int, root: ViewGroup, attachToRoot: Boolean = false): View =
    LayoutInflater.from(this).inflate(resource, root, attachToRoot)

/**
 * 通过ContextCompat.getColor获取color
 *
 * @param id ColorRes
 * @return int
 */
fun Context.getCompatColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.dpToPx(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f * if (dp >= 0) 1 else -1).toInt()
}