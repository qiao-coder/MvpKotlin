package com.example.module_a

import com.tufei.base.base.BaseApp
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * @author TuFei
 * @date 18-11-17.
 */
class App : BaseApp() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()

}