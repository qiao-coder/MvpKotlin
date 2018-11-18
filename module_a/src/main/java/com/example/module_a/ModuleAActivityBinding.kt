package com.example.module_a

import com.example.module_a.main.MainActivity
import com.example.module_a.main.MainModule
import com.tufei.base.di.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author TuFei
 * @date 18-11-17.
 */
@Module
abstract class ModuleAActivityBinding {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity
}