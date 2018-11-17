package com.tufei.base.base

/**
 * 下面几个方法，都将会在[BaseActivity]、[BaseFragment]里面实现，
 * presenter里面使用[BasePresenter.view]直接调用这些方法即可
 * @author TuFei
 * @date 18-9-27.
 */
interface IBaseView {
    /**
     * [BaseActivity]虽然没有该方法的实现，但在[ContextExt]里面有实现相应的扩展方法
     */
    fun showToast(tip: String)

    fun showLoading()

    fun showLoading(msg: String)

    fun hideLoading()
}