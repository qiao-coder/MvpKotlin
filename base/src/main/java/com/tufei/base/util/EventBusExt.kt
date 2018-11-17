package com.tufei.base.util

import org.greenrobot.eventbus.EventBus

/**
 * 在其他需要使用EventBus的累里面，直接使用该实例：
 * eventBus.register(this)
 * eventBus.unregister(this)
 * eventBus.post(SomeEvent())
 * @author TuFei
 * @date 18-10-17.
 */
val eventBus = EventBus.getDefault()