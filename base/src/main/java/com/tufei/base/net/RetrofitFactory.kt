package com.tufei.base.net

import com.tufei.base.constant.Const
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author tufei
 * @date 2018/3/9.
 */
object RetrofitFactory {

    private val defaultInterceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json; charset=UTF-8")
            .build()
        chain.proceed(request)
    }

    fun createRetrofit(interceptor: Interceptor = defaultInterceptor): Retrofit {
        val gsonConverterFactory = GsonConverterFactory.create()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .connectTimeout(Const.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(Const.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}