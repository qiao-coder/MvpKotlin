package com.example.module_a.main

import com.example.module_a.DataRepository
import com.tufei.base.base.BasePresenter
import com.tufei.base.di.ActivityScoped
import com.tufei.base.util.awaitUI
import com.tufei.base.util.launchUI
import com.tufei.base.util.rx.handleHttpDSL
import javax.inject.Inject

/**
 * @author TuFei
 * @date 18-11-17.
 */
//注意，@JvmField var是必须的。
@ActivityScoped
class MainPresenter @Inject constructor(@JvmField var dataRepository: DataRepository) :
    BasePresenter<MainView>() {

    fun login() {
        dataRepository.login()
            //handleHttpDSL，封装了线程切换。网络请求预处理。
            //另一个版本是handleNoDataHttpDSL
            .handleHttpDSL {
                //写在这里的代码，会在网络请求一开始时就进行。
                //做了封装。是在主线程执行的。
                view.showLoading()
                onSuccess {
                    view.hideLoading()
                    //doSomething
                }
                onError {
                    view.hideLoading()
                    message?.run {
                        view.showToast(this)
                    }
                }
            }.addToSubscriptions()
    }

    fun loginTest() {
        launchUI {
            view.showLoading()
            kotlin.runCatching {
                dataRepository.loginTest().awaitUI()
                view.hideLoading()
            }.onFailure {
                view.hideLoading()
                it.message?.run {
                    view.showToast(this)
                }
            }
        }.addCoroutines() // addCoroutines方法是基类取消协程的操作
    }

}