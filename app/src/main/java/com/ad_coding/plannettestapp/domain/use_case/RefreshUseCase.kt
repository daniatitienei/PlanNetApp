package com.ad_coding.plannettestapp.domain.use_case

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class RefreshUseCase {

    private val _lastRefresh = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val lastRefresh = _lastRefresh.asSharedFlow()

    suspend fun requestRefresh() {
        _lastRefresh.emit(Unit)
    }
}