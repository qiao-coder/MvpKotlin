package com.example.tufei.mvpkotlin.main

import android.os.Bundle
import com.example.tufei.mvpkotlin.R
import com.tufei.base.base.BaseFragment
import com.tufei.base.di.ActivityScoped
import com.tufei.base.di.FragmentScoped
import javax.inject.Inject

/**
 * @author TuFei
 * @date 18-11-17.
 */
class MainFragment @Inject constructor() : BaseFragment() {

    override fun setupLayoutId(): Int = R.layout.activity_main

    override fun setupPresenter() {
    }

    override fun setupData(savedInstanceState: Bundle?) {
    }
}