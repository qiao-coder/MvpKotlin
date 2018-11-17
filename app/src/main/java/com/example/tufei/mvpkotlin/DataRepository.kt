package com.example.tufei.mvpkotlin

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author TuFei
 * @date 18-11-17.
 */
@Singleton
class DataRepository @Inject constructor(@JvmField var context: Context,
                                         @JvmField var userService: UserService) {
    fun login() =
            userService.login(
                mutableMapOf(
                    "id" to "123456"
                )
            )
}