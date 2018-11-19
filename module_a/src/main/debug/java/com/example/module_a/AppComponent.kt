package com.example.module_a

import android.app.Application
import com.tufei.base.di.BaseAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * @author tufei
 * @date 2018/2/21.
 */
@Singleton
@Component(
    modules = [
        BaseAppModule::class,
        AModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}