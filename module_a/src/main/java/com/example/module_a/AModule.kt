package com.example.module_a

import dagger.Module

/**
 * @author TuFei
 * @date 18-11-19.
 */
@Module(includes = [
    SingleModule::class,
    ModuleAActivityBinding::class
])
class AModule