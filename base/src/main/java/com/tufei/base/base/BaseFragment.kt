package com.tufei.base.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tufei.base.util.ToastTime
import com.tufei.base.util.showToast
import dagger.android.support.DaggerFragment

/**
 * @author TuFei
 * @date 18-11-17.
 */
abstract class BaseFragment : DaggerFragment() {
    protected val TAG = javaClass.simpleName

    @Deprecated("为了封装做的妥协，请不要调用")
    var _presenter: IBasePresenter<out IBaseView>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(setupLayoutId(), container, false)
    }

    @LayoutRes
    abstract fun setupLayoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupPresenter()
        setupData(savedInstanceState)
    }

    abstract fun setupPresenter()

    abstract fun setupData(savedInstanceState: Bundle?)

    fun showLoading() {
        //实现请求接口等异步操作时的加载弹窗
    }

    fun showLoading(msg: String) {
        //实现请求接口等异步操作时的加载弹窗
    }


    fun hideLoading() {
        //请求接口等异步操作结束，隐藏加载弹窗
    }


    fun showToast(tip: String) {
        context?.showToast(tip,ToastTime.SHORT)
    }

    /**
     * 绑定view到presenter上。当该fragment被销毁，在执行[onDestroy]时，会执行
     * presenter的[IBasePresenter.onDetachView]方法。该方法里面，会取消该presenter所有的rx
     * 的订阅。这样，就不用在担心异步任务完成后的回调里，执行UI更新操作，会导致空指针等问题。
     *
     * @throws IllegalArgumentException 如果该fragment没有实现presenter需要的view接口
     */
    inline fun <reified V : IBaseView, P : IBasePresenter<V>, T : P> attach(t: T) {
        when {
            this is V -> {
                _presenter = t
                t.onAttachView(this)
            }
            else -> throw  IllegalArgumentException(
                "${this.javaClass.simpleName} hadn't implement the interface " +
                        "${V::class.java.simpleName}.So it can't attach to ${t.javaClass.simpleName}"
            )
        }
    }

    override fun onDestroyView() {
        _presenter?.onDetachView()
        //销毁顺序与初始化顺序应该相反，所以，放在最后面
        super.onDestroyView()
    }
}