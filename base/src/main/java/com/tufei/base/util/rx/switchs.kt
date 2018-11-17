package com.tufei.base.util.rx

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 线程切换
 * @author tufei
 * @date 2018/2/26.
 */
fun <T> Single<T>.ioToMain(): Single<T> {
    return compose {
        it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}

fun Completable.ioToMain(): Completable {
    return compose {
        it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}