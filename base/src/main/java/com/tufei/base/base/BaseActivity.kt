package com.tufei.base.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.util.Log
import com.tufei.base.util.ToastTime
import com.tufei.base.util.showToast
import dagger.android.support.DaggerAppCompatActivity

/**
 * @author TuFei
 * @date 18-11-17.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    protected val TAG = javaClass.simpleName

    @Deprecated("为了封装做的妥协，请不要调用")
    var _presenter: BasePresenter<out IBaseView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "$TAG : onCreate()")
        //无法使用这种方式锁定竖屏。如果设定了这行代码，实现锁定竖屏，
        //一旦用户手机不锁定方向，横着打开界面。 界面会直接闪退。
        //请直接在manifest里面设置android:screenOrientation="portrait"
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(setupLayoutId())
        setupPresenter()
        setupData(savedInstanceState)
    }

    @LayoutRes
    abstract fun setupLayoutId(): Int

    abstract fun setupPresenter()

    abstract fun setupData(savedInstanceState: Bundle?)

    /**
     * 绑定view到presenter上。当该activity被销毁，在执行[onDestroy]时，会执行
     * presenter的[BasePresenter.onDetachView]方法。该方法里面，会取消该presenter所有的rx的订阅。这样，就不用在担心异步任务完成后的回调里，执行UI更新操作，会导致空指针等问题。
     *
     * @throws IllegalArgumentException 如果该activity没有实现presenter需要的view接口
     */
    inline fun <reified V : IBaseView, P : BasePresenter<V>, T : P> attach(t: T) {
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
        (this as Context).showToast(tip, ToastTime.SHORT)
    }

    override fun onDestroy() {
        Log.i(TAG, "$TAG : onDestroy()")
        _presenter?.onDetachView()
        //销毁顺序与初始化顺序应该相反，所以，放在最后面
        super.onDestroy()
    }
}