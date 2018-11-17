package com.tufei.base.base

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import com.tufei.base.util.Preferences
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins

/**
 * @author TuFei
 * @date 18-11-17.
 */
abstract class BaseApp : DaggerApplication() {
    protected val TAG = javaClass.simpleName

    override fun onCreate() {
        super.onCreate()
        //有多个进程的时候，会初始化多次
        if (isMainProcess()) {
            init()
        }
    }


    private fun init() {
//        initArouter()
        setRxJavaErrorHandler()
        Preferences.initSharedPreferences(this)

    }

    /**
     * ARouter 相关的配置
     */
    private fun initArouter() {
//        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
//            ARouter.openLog()     // 打印日志
//            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
//        ARouter.init(this)       // 尽可能早，推荐在Application中初始化
    }

    private fun setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            Log.d(TAG, "未实现onError方法，dispose后，导致无法处理错误：${it.message}")
        }
    }

    private fun isMainProcess(): Boolean {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processInfos = am.runningAppProcesses
        val mainProcessName = packageName
        val myPid = android.os.Process.myPid()

        return processInfos.any { it.pid == myPid && mainProcessName == it.processName }
    }
}