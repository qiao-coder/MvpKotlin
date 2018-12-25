package com.tufei.base.util


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable
import java.util.*

/**
 * @author Jowan
 * Created on 2018/11/12.
 */

/**
 * @param extras 支持 Bundle, Boolean, BooleanArray, Byte, ByteArray, Char, CharArray, String, CharSequence, Double, DoubleArray, Float, FloatArray, Int, IntArray, Long, LongArray, Short, ShortArray, Parcelable, Serializable, putParcelableArrayListExtra, putStringArrayListExtra, putCharSequenceArrayListExtra.
 * #sample Intent().putExtraVararg("id" to "id", "bool" to true, "strList" to arrayListOf("1", null), "parcelable" to arrayListOf(ParcelableEntity(), null), "char" to arrayListOf("123", null))
 * @return intent
 */
fun Intent.putExtraVararg(
    vararg extras: Pair<String, Any?>
): Intent {
    if (extras.isEmpty()) return this
    extras.forEach { (key, value) ->
        value ?: let {
            it.putExtra(key, it.toString())
            return@forEach
        }
        when (value) {
            is Bundle -> this.putExtra(key, value)
            is Boolean -> this.putExtra(key, value)
            is BooleanArray -> this.putExtra(key, value)
            is Byte -> this.putExtra(key, value)
            is ByteArray -> this.putExtra(key, value)
            is Char -> this.putExtra(key, value)
            is CharArray -> this.putExtra(key, value)
            is String -> this.putExtra(key, value)
            is CharSequence -> this.putExtra(key, value)
            is Double -> this.putExtra(key, value)
            is DoubleArray -> this.putExtra(key, value)
            is Float -> this.putExtra(key, value)
            is FloatArray -> this.putExtra(key, value)
            is Int -> this.putExtra(key, value)
            is IntArray -> this.putExtra(key, value)
            is Long -> this.putExtra(key, value)
            is LongArray -> this.putExtra(key, value)
            is Short -> this.putExtra(key, value)
            is ShortArray -> this.putExtra(key, value)
            is Array<*> -> {
                @Suppress("UNCHECKED_CAST")
                when {
                    value.isArrayOf<String>() -> {
                        this.putStringArrayListExtra(key, value as ArrayList<String?>)
                    }
                    value.isArrayOf<CharSequence>() -> {
                        this.putCharSequenceArrayListExtra(key, value as ArrayList<CharSequence?>)
                    }
                    value.isArrayOf<Parcelable>() -> {
                        this.putParcelableArrayListExtra(key, value as ArrayList<out Parcelable?>)
                    }
                }
            }
            is Parcelable -> this.putExtra(key, value)
            is Serializable -> this.putExtra(key, value)
            else -> {
                throw IllegalArgumentException("Not support $value type ${value.javaClass}..")
            }
        }
    }
    return this
}


/**
 * 同Fragment的startActivity
 */
fun Fragment.toActivity(packageContext: Context?, cls: Class<*>, vararg extras: Pair<String, Any?>) {
    startActivity(Intent(packageContext, cls).putExtraVararg(*extras))
}

/**
 * 同Fragment的startActivity
 */
fun Fragment.toActivity(intent: Intent) {
    startActivity(intent)
}

/**
 * 同Fragment的startActivityForResult，requestCode
 */
fun Fragment.toActivityForResult(packageContext: Context?, cls: Class<*>, requestCode: Int, vararg extras: Pair<String, Any?>) {
    startActivityForResult(Intent(packageContext, cls).putExtraVararg(*extras), requestCode)
}

/**
 * 同Fragment的startActivityForResult
 */
fun Fragment.toActivityForResult(intent: Intent, requestCode: Int) {
    startActivityForResult(intent, requestCode)
}

/**
 * 同Context的startActivity
 */
fun Context.toActivity(packageContext: Context?, cls: Class<*>, vararg extras: Pair<String, Any?>) {
    startActivity(Intent(packageContext, cls).putExtraVararg(*extras))
}
/**
 * 同Context的startActivity
 */
fun Context.toActivity(intent: Intent) {
    startActivity(intent)
}

/**
 * 同Activity的startActivity
 */
fun Activity.toActivity(packageContext: Context?, cls: Class<*>, vararg extras: Pair<String, Any?>) {
    startActivity(Intent(packageContext, cls).putExtraVararg(*extras))
}

/**
 * 同Activity的startActivityForResult
 */
fun Activity.toActivityForResult(packageContext: Context?, cls: Class<*>, requestCode: Int, vararg extras: Pair<String, Any?>) {
    startActivityForResult(Intent(packageContext, cls).putExtraVararg(*extras), requestCode)
}

/**
 * 同Activity的startActivityForResult
 */
fun Activity.toActivityForResult(intent: Intent, requestCode: Int) {
    startActivityForResult(intent, requestCode)
}