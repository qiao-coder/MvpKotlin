package com.example.tufei.mvpkotlin.main

import android.os.Bundle
import android.view.View
import com.example.tufei.mvpkotlin.R
import com.example.tufei.mvpkotlin.UserBean
import com.tufei.base.base.BaseActivity
import com.tufei.base.util.Preferences
import com.tufei.base.util.extraDelegate
import com.tufei.base.util.setOnClickListener
import dagger.Lazy
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, View.OnClickListener {

    @Inject
    lateinit var presenter: MainPresenter
    @Inject
    lateinit var presenter2:MainPresenter
    @Inject
    lateinit var fragment1: MainFragment
    @Inject
    lateinit var fragment2: MainFragment
    //注意，别一不小心用了kotlin的lazy
    @Inject
    lateinit var fragmentLazy: Lazy<MainFragment>

    //从intent中获取参数
    private val fromSystem: Boolean by extraDelegate("fromSystem", false)
    private val status: String by extraDelegate("status", "")

    //调用该字段，就是获取sp值
    //isFirst = true，就是更新sp值
    private var isFirst: Boolean by Preferences("isFirst", false)

    override fun setupLayoutId() = R.layout.activity_main

    override fun setupPresenter() {
        attach(presenter)
        presenter.login()
    }

    override fun setupData(savedInstanceState: Bundle?) {
        if (fragment1 === fragment2 || fragment1 === fragmentLazy.get()) {
            throw RuntimeException("同一个实例")
        }
        //MainPresenter类必须加@ActivityScoped。
        //presenter、presenter2才会实现activity域内的单例
        if(!(presenter===presenter2)){
            throw RuntimeException("不是同一个实例")
        }

        //调到该扩展方法前，必须实现View.OnClickListener接口
        setOnClickListener(btn_hello, btn_next, rv_content)

        val userAdapter = UserAdapter()
        rv_content.adapter = userAdapter
        val newItems = mutableListOf(UserBean("1", "张三"), UserBean("2", "李四"))
        userAdapter.items = newItems
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_hello -> showToast("hello")

            R.id.btn_next -> showToast("next")

            //RecyclerView重写了onTouch事件没有去理会父类的Listener，导致OnClickListener失效
            R.id.rv_content -> showToast("rv_content")
        }
    }

}
