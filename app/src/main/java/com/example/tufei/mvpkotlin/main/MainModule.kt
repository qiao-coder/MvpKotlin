package com.example.tufei.mvpkotlin.main

import com.tufei.base.di.ActivityScoped
import com.tufei.base.di.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * @author TuFei
 * @date 18-11-17.
 */
@Module
abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

//    @ActivityScoped
//    @Binds
//    abstract fun mainPresenter(mainPresenter: MainPresenter): MainContract.Presenter
}