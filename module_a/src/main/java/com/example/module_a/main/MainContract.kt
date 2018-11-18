package com.example.module_a.main

import com.tufei.base.base.IBasePresenter
import com.tufei.base.base.IBaseView

/**
 * @author TuFei
 * @date 18-11-17.
 */
class MainContract {
    interface View : IBaseView

    interface Presenter : IBasePresenter<View> {
        fun login()
    }
}