package com.tufei.base.di

import javax.inject.Scope

/**
 * 在Dagger里，一个无范围的component不能依赖一个有范围的component。当[AppComponent]是一个有范围的component
 * 时，我们要创建一个定制的scope，给所有的activity components使用。
 * 另外要注意，一个被标注一个规定的scope的component不能拥有一个被标注了相同scope的sub component(子组件)，
 * 不然编译时会报错。
 *
 * @author tufei
 * @date 2018/2/21.
 */
@MustBeDocumented
@Scope
@Retention
annotation class ActivityScoped