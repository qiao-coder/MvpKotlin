package com.example.tufei.mvpkotlin.main

import android.os.Bundle
import com.example.tufei.mvpkotlin.R
import com.tufei.base.base.BaseFragment

/**
 * @author TuFei
 * @date 18-11-17.
 */
class MainFragment : BaseFragment() {

    override fun setupLayoutId(): Int = R.layout.activity_main

    override fun setupPresenter() {
    }

    override fun setupData(savedInstanceState: Bundle?) {
    }
}