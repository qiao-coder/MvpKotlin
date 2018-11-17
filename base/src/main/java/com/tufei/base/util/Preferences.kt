package com.tufei.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.tufei.base.constant.SharedConst
import com.tufei.base.util.Preferences.Companion.initSharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * SharedPreferences operation, you need to invoke [initSharedPreferences] when initialize application
 */
class Preferences<T>(private val key: String, private val default: T) : ReadWriteProperty<Any?, T> {

    companion object {
        private lateinit var sharePreferences: SharedPreferences

        /**
         * 请确保在Application调用一次，对sp进行初始化
         */
        @JvmStatic
        fun initSharedPreferences(context: Context) {
            with(context) {
                sharePreferences = applicationContext.getSharedPreferences(
                    SharedConst.SHARED_NAME,
                    Context.MODE_PRIVATE
                )
            }
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = getData(key, default)

    @Suppress("UNCHECKED_CAST")
    private fun getData(key: String, default: T): T = with(sharePreferences) {
        val value: Any = when (default) {
            is Int -> getInt(key, default)
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> throw IllegalArgumentException("This type of $default can't be acceptable!")
        }
        return value as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putData(key, value)

    @SuppressLint("CommitPrefEdits")
    private fun putData(key: String, value: T) = with(sharePreferences.edit()) {
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> throw IllegalArgumentException("This type of $value can't be saved into SharedPreferences!")
        }.apply()
    }
}