package com.tufei.base.base

import com.tufei.base.util.cancelByActive
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Job

/**
 * @author TuFei
 * @date 18-11-17.
 */
open class BasePresenter<View : IBaseView> : IBasePresenter<View> {
    protected val TAG = javaClass.simpleName
    private val compositeDisposable = CompositeDisposable()
    private val jobs = mutableListOf<Job>()
    //在Presenter里面，直接使用这个变量，调用view层的方法
    protected lateinit var view: View

    override fun onAttachView(view: View) {
        this.view = view
    }

    /**
     * 如果使用rx进行异步操作后，要执行一些更新UI的操作，必须调用该方法。
     * 避免异步任务回来后，activity/fragment却已经被销毁，导致空指针等异常。
     */
    protected fun Disposable.addToSubscriptions() {
        compositeDisposable.add(this)
    }

    protected fun Job.addCoroutines() {
        jobs.add(this)
    }

    override fun onDetachView() {
        compositeDisposable.clear()
        jobs.forEach { it.cancelByActive() }
        jobs.clear()
    }
}