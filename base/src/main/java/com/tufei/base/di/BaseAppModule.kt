package com.tufei.base.di

import android.app.Application
import android.content.Context
import com.tufei.base.net.NetModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * @author TuFei
 * @date 2018/5/14.
 */
@Module(includes = [NetModule::class])
abstract class BaseAppModule {
    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context
}