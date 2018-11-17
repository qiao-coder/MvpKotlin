package com.tufei.base.net

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author tufei
 * @date 2018/3/9.
 */
@Module
class NetModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitFactory.createRetrofit()
    }
}