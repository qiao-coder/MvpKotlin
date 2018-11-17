package com.example.tufei.mvpkotlin

import com.tufei.base.net.HttpResult
import io.reactivex.Single
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author TuFei
 * @date 18-11-17.
 */
interface UserService {
    @FormUrlEncoded
    @POST("xxx/xxx")
    fun login(@FieldMap mutableMapOf: MutableMap<String, String>): Single<HttpResult<UserBean>>
}