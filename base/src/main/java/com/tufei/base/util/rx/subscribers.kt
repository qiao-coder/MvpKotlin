package com.tufei.base.util.rx

import com.tufei.base.net.HttpResult
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * 封装了线程切换功能的subscribe方法。
 * @author tufei
 * @date 2018/2/28.
 */

/**
 * 包含onNext和onError
 */
class NextAndError<T : Any> {
    private var _onError: (Throwable) -> Unit = onErrorStub
    private var _onNext: (T) -> Unit = onNextStub

    fun onError(onError: Throwable.() -> Unit) {
        _onError = onError
    }

    fun onSuccess(onNext: (T) -> Unit) {
        _onNext = onNext
    }

    fun getError() = _onError

    fun getNext() = _onNext
}

private val onNextStub: (Any) -> Unit = {}
private val onErrorStub: (Throwable) -> Unit = {}
private val onCompleteStub: () -> Unit = {}
private fun <T : Any> ((T) -> Unit).asConsumer(): Consumer<T> {
    return if (this === onNextStub) Functions.emptyConsumer() else Consumer(this)
}

private fun ((Throwable) -> Unit).asOnErrorConsumer(): Consumer<Throwable> {
    return if (this === onErrorStub) Functions.ON_ERROR_MISSING else Consumer(this)
}

private fun (() -> Unit).asOnCompleteAction(): Action {
    return if (this === onCompleteStub) Functions.EMPTY_ACTION else Action(this)
}

fun <T : Any> Single<T>.into(
    onError: (Throwable) -> Unit = onErrorStub,
    onNext: (T) -> Unit = onNextStub
): Disposable = ioToMain().subscribe(onNext.asConsumer(), onError.asOnErrorConsumer())


fun Completable.into(
    onError: (Throwable) -> Unit = onErrorStub,
    onComplete: () -> Unit = onCompleteStub
): Disposable = when {
    onError === onErrorStub && onComplete === onCompleteStub -> ioToMain().subscribe()
    onError === onErrorStub -> ioToMain().subscribe(onComplete)
    else -> ioToMain().subscribe(onComplete.asOnCompleteAction(), onError.asOnErrorConsumer())
}

/**
 * compose[handleHttpResult]返回Disposable
 */
fun <T : Any> excResultSub(
    nextAndError: NextAndError<T>,
    observable: Single<HttpResult<T>>
): Disposable {
    return observable
        .ioToMain()
        .compose(handleHttpResult<T>())
        .subscribe(
            nextAndError.getNext().asConsumer(),
            nextAndError.getError().asOnErrorConsumer()
        )
}

/**
 * compose[handleNoDataHttpResult]返回Disposable
 */
fun <T : Any> excNoDataResultSub(
    nextAndError: NextAndError<T>,
    observable: Single<HttpResult<T>>
): Disposable {
    return observable
        .ioToMain()
        .compose(handleNoDataHttpResult<T>())
        .subscribe(
            nextAndError.getNext().asConsumer(),
            nextAndError.getError().asOnErrorConsumer()
        )
}

/**
 * [handleHttp]的DSL版本
 */
inline fun <T : Any> Single<HttpResult<T>>.handleHttpDSL(init: NextAndError<T>.(Single<HttpResult<T>>) -> Unit): Disposable {
    val nextAndError = NextAndError<T>()
    nextAndError.init(this)
    return excResultSub(nextAndError, this)
}

/**
 * [handleNoDataHttp]的DSL版本
 */
inline fun <T : Any> Single<HttpResult<T>>.handleNoDataHttpDSL(init: NextAndError<T>.(Single<HttpResult<T>>) -> Unit): Disposable {
    val nextAndError = NextAndError<T>()
    nextAndError.init(this)
    return excNoDataResultSub(nextAndError, this)
}

private fun <T : Any> Single<HttpResult<T>>.handleHttp(
    onError: (Throwable) -> Unit = onErrorStub,
    onNext: (T) -> Unit = onNextStub
): Disposable = ioToMain()
    .compose(handleHttpResult<T>())
    .subscribe(onNext.asConsumer(), onError.asOnErrorConsumer())


private fun <T : Any> Single<HttpResult<T>>.handleNoDataHttp(
    onError: (Throwable) -> Unit = onErrorStub,
    onComplete: () -> Unit = onCompleteStub
): Disposable = ioToMain()
    .compose(handleNoDataHttpResult<T>())
    .toCompletable()
    .subscribe(onComplete.asOnCompleteAction(), onError.asOnErrorConsumer())

private fun <T : Any> handleHttpResult(): SingleTransformer<HttpResult<T>, T> =
    SingleTransformer { observable ->
        observable.flatMap { httpResult ->
            when {
                httpResult.code == "00000" && httpResult.data != null -> Single.just<T>(httpResult.data)
                else -> {
                    val msg: String = when {
                        httpResult.data == null -> "获取到的数据为空"
                        httpResult.codeMsg.isEmpty() -> "网络请求错误，错误信息为空"
                        else -> httpResult.codeMsg
                    }
                    Single.error<T>(Exception(msg))
                }
            }
        }
    }

private fun <T : Any> handleNoDataHttpResult(): SingleTransformer<HttpResult<T>, T> =
    SingleTransformer { observable ->
        observable.flatMap { httpResult ->
            when {
                httpResult.code == "00000" -> Single.just<T>(httpResult.data)
                else -> {
                    val msg: String = when {
                        httpResult.codeMsg.isEmpty() -> "网络请求错误，错误信息为空"
                        else -> httpResult.codeMsg
                    }
                    Single.error<T>(Exception(msg))
                }
            }
        }
    }