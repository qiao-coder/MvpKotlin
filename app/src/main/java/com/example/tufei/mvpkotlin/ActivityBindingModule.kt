package com.example.tufei.mvpkotlin

import com.example.tufei.mvpkotlin.main.MainActivity
import com.example.tufei.mvpkotlin.main.MainModule
import com.tufei.base.di.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author TuFei
 * @date 18-11-17.
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity
}