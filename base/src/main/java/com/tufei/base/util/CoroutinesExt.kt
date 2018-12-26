package com.tufei.base.util

import com.tufei.base.net.HttpResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @author Jowan
 * Created on 2018/12/24.
 */

class SuccessAndError<T : Any> {
    private var _onSuccess: T.() -> Unit = {}
    private var _onError: (Throwable) -> Unit = {}
    var context: CoroutineContext = Dispatchers.Default

    fun onSuccess(onSuccess: T.() -> Unit) {
        _onSuccess = onSuccess
    }

    fun onError(onError: (Throwable) -> Unit) {
        _onError = onError
    }

    fun excSuccess(t: T) = _onSuccess.invoke(t)

    fun excError(t: Throwable) = _onError.invoke(t)
}

fun Job.cancelByActive() {
    if (isActive) cancel()
}

fun launchUI(block: suspend () -> Unit): Job {
    return GlobalScope.launch(Dispatchers.Main) { block() }
}

fun launchIO(block: suspend () -> Unit): Job {
    return GlobalScope.launch(Dispatchers.IO) { block() }
}

fun launch(block: suspend () -> Unit): Job {
    return GlobalScope.launch { block() }
}

/**
 ** 外面需要使用launch或者async，例子：
 **
 **        launchUI {
 **            view.showLoading()
 **            kotlin.runCatching {
 **                view.showSleepMusics(dataRepository.getSleepMusicsTest().awaitUI())
 **                view.hideLoading()
 **            }.onFailure {
 **                view.hideLoading()
 **                it.message?.run {
 **                    view.showToast(this)
 **                    Log.e(TAG, this)
 **                }
 **            }
 **        }.addCoroutines() // addCoroutines方法是基类取消协程的操作
 */
@Throws(Exception::class)
suspend fun <T : Any> Deferred<HttpResult<T>>?.awaitUI(): T {
    val result = this@awaitUI?.await() ?: throw Exception("获取到的数据为空")
    result.let {
        if (it.code != "0" || it.data == null) {
            val msg: String = when {
                it.data == null -> "获取到的数据为空"
                it.codeMsg.isEmpty() -> "网络请求错误，错误信息为空"
                else -> it.codeMsg
            }
            throw Exception(msg)
        }
    }
    return result.data!!
}

/**
 ** 例子：
 **
 **    dataRepository.getSleepMusicsTest().handleHttp {
 **        onSuccess {
 **
 **        }
 **        onError {
 **
 **        }
 **    }.addCoroutines() // addCoroutines方法是基类取消协程的操作
 */
inline fun <T : Any> Deferred<HttpResult<T>>?.handleHttp(
    init: SuccessAndError<HttpResult<T>>.() -> Unit
): Deferred<Any> {
    val successAndError = SuccessAndError<HttpResult<T>>()
    successAndError.init()
    return excResultSub(successAndError, this)
}

/**
 ** 例子：
 **
 **    dataRepository.getSleepMusicsTest().handleNotDataHttp {
 **        onSuccess {
 **
 **        }
 **        onError {
 **
 **        }
 **    }.addCoroutines() // addCoroutines方法是基类取消协程的操作
 */
inline fun <T : Any> Deferred<HttpResult<T>>?.handleNotDataHttp(
    init: SuccessAndError<T>.() -> Unit
): Deferred<Any> {
    val successAndError = SuccessAndError<T>()
    successAndError.init()
    return excNotDataResultSub(successAndError, this)
}

fun <T : Any> excResultSub(
    successAndError: SuccessAndError<HttpResult<T>>,
    _deferred: Deferred<HttpResult<T>>?
): Deferred<Any> {
    return GlobalScope.async(successAndError.context) {
        _deferred?.await().runCatching {
            this?.let {
                if (it.code == "0" && it.data != null) {
                    successAndError.excSuccess(it)
                } else {
                    val msg: String = when {
                        it.data == null -> "获取到的数据为空"
                        it.codeMsg.isEmpty() -> "网络请求错误，错误信息为空"
                        else -> it.codeMsg
                    }
                    throw Exception(msg)
                }
            } ?: throw Exception("获取到的数据为空")
        }.onFailure {
            // 异常会走这里
            successAndError.excError(it)
        }
    }
}

fun <T : Any> excNotDataResultSub(
    successAndError: SuccessAndError<T>,
    _deferred: Deferred<HttpResult<T>>?
): Deferred<Any> {
    return GlobalScope.async(successAndError.context) {
        _deferred?.await().runCatching {
            this?.let {
                if (it.code == "0" && it.data != null) {
                    successAndError.excSuccess(it.data)
                } else {
                    val msg: String = when {
                        it.data == null -> "获取到的数据为空"
                        it.codeMsg.isEmpty() -> "网络请求错误，错误信息为空"
                        else -> it.codeMsg
                    }
                    throw Exception(msg)
                }
            } ?: throw Exception("获取到的数据为空")
        }.onFailure {
            // 异常会走这里
            successAndError.excError(it)
        }
    }
}
