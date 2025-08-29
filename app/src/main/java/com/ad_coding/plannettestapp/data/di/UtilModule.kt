package com.ad_coding.plannettestapp.data.di

import com.ad_coding.plannettestapp.data.util.SystemClock
import com.ad_coding.plannettestapp.domain.util.Clock
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {

    @Binds
    abstract fun bindClock(
        systemClock: SystemClock
    ): Clock
}