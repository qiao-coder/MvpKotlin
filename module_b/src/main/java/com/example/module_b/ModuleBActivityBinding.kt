package com.example.module_b

import com.tufei.base.di.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author TuFei
 * @date 18-11-18.
 */
@Module
abstract class ModuleBActivityBinding {

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract fun loginActivity(): LoginActivity
}