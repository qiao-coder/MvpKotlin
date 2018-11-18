package com.example.module_a.main

import android.os.Bundle
import com.example.module_a.R
import com.tufei.base.base.BaseFragment
import javax.inject.Inject

/**
 * @author TuFei
 * @date 18-11-17.
 */
class MainFragment @Inject constructor() : BaseFragment() {

    override fun setupLayoutId(): Int = R.layout.a_activity_main

    override fun setupPresenter() {
    }

    override fun setupData(savedInstanceState: Bundle?) {
    }
}