package com.example.tufei.mvpkotlin

import android.app.Application
import com.example.module_a.AppModule
import com.example.module_a.ModuleAActivityBinding
import com.example.module_b.ModuleBActivityBinding
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
        AppModule::class,
        BaseAppModule::class,
        ModuleAActivityBinding::class,
        ModuleBActivityBinding::class,
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