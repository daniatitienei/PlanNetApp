package com.ad_coding.plannettestapp.data.util

import com.ad_coding.plannettestapp.domain.util.Clock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemClock @Inject constructor() : Clock {
    override fun now(): Long = System.currentTimeMillis()
}