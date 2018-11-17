package com.tufei.base.base

/**
 * @author TuFei
 * @date 18-9-27.
 */
interface IBasePresenter<T : IBaseView> {

    fun onAttachView(view: T)

    fun onDetachView()
}