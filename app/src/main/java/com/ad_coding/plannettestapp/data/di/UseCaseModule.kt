package com.ad_coding.plannettestapp.data.di

import com.ad_coding.plannettestapp.domain.repository.ProductGradeRepository
import com.ad_coding.plannettestapp.domain.repository.ProductRepository
import com.ad_coding.plannettestapp.domain.use_case.GetGradeListUseCase
import com.ad_coding.plannettestapp.domain.use_case.GetProductLevelOneByIdUseCase
import com.ad_coding.plannettestapp.domain.use_case.GetProductLevelTwoByIdUseCase
import com.ad_coding.plannettestapp.domain.use_case.GetProductsLevelOneUseCase
import com.ad_coding.plannettestapp.domain.use_case.GetProductsLevelTwoUseCase
import com.ad_coding.plannettestapp.domain.use_case.RefreshUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetProductsLevelOneUseCase(
        repository: ProductRepository
    ): GetProductsLevelOneUseCase = GetProductsLevelOneUseCase(repository)

    @Provides
    fun provideGetProductsLevelTwoUseCase(
        repository: ProductRepository
    ): GetProductsLevelTwoUseCase = GetProductsLevelTwoUseCase(repository)

    @Provides
    fun provideGetProductsLevelOneByIdUseCase(
        repository: ProductRepository
    ): GetProductLevelOneByIdUseCase = GetProductLevelOneByIdUseCase(repository)

    @Provides
    fun provideGetProductsLevelTwoByIdUseCase(
        repository: ProductRepository
    ): GetProductLevelTwoByIdUseCase = GetProductLevelTwoByIdUseCase(repository)

    @Provides
    fun provideGetGradeListUseCase(
        repository: ProductGradeRepository
    ): GetGradeListUseCase = GetGradeListUseCase(repository)

    @Singleton
    @Provides
    fun provideRefreshUseCase(): RefreshUseCase = RefreshUseCase()
}