package com.example.module_b

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.tufei.base.base.BaseActivity

/**
 * @author TuFei
 * @date 18-11-18.
 */
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/module_b/LoginActivity")
class LoginActivity : BaseActivity() {
    override fun setupLayoutId() = R.layout.b_activity_login

    override fun setupPresenter() {

    }

    override fun setupData(savedInstanceState: Bundle?) {

    }
}