package com.example.module_b

import dagger.Module

/**
 * @author TuFei
 * @date 18-11-19.
 */
@Module(includes = [
    SingleModule::class,
    ModuleBActivityBinding::class
])
class BModule