package com.tufei.base.util.rx

import io.reactivex.Single


/**
 * @author TuFei
 * @date 18-9-26.
 */
fun <T : Any> T.toSingle(): Single<T> {
    return Single.create<T> {
        it.onSuccess(this)
    }
}