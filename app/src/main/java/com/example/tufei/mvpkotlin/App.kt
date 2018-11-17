package com.example.tufei.mvpkotlin

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import com.tufei.base.base.BaseApp
import com.tufei.base.util.Preferences
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins

/**
 * @author TuFei
 * @date 18-11-17.
 */
class App : BaseApp() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()

}