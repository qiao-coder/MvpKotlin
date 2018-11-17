package com.tufei.base.util.rx

import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * @author TuFei
 * @date 18-9-26.
 */
object Functions {
    val EMPTY_ACTION = EmptyAction()
    val EMPTY_CONSUMER = EmptyConsumer()
    val ON_ERROR_MISSING = OnErrorMissingConsumer()
    @Suppress("UNCHECKED_CAST")
    fun <T> emptyConsumer(): Consumer<T> = EMPTY_CONSUMER as Consumer<T>
}

class EmptyConsumer : Consumer<Any> {
    override fun accept(t: Any) {
        //doNothing
    }


    override fun toString(): String {
        return "EmptyConsumer"
    }
}

class EmptyAction : Action {
    override fun run() {
        //doNothing
    }

    override fun toString(): String {
        return "EmptyAction"
    }

}

class OnErrorMissingConsumer : Consumer<Throwable> {
    override fun accept(t: Throwable) {
        //doNothing
    }

    override fun toString(): String {
        return "OnErrorMissingConsumer"
    }
}