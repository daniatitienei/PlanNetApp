package com.ad_coding.plannettestapp.data.di

import com.ad_coding.plannettestapp.data.repository.ProductGradeRepositoryImpl
import com.ad_coding.plannettestapp.data.repository.ProductRepositoryImpl
import com.ad_coding.plannettestapp.domain.repository.ProductGradeRepository
import com.ad_coding.plannettestapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    abstract fun bindGradeRepository(
        impl: ProductGradeRepositoryImpl
    ): ProductGradeRepository
}
