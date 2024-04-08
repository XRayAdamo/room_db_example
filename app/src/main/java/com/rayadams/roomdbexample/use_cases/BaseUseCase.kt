package com.rayadams.roomdbexample.use_cases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class BaseUseCase {
    internal val coroutineScope = CoroutineScope(Dispatchers.IO)
}