package com.ad_coding.plannettestapp.data.di

import android.content.Context
import androidx.room.Room
import com.ad_coding.plannettestapp.data.local.dao.ProductGradeDao
import com.ad_coding.plannettestapp.data.local.dao.ProductLevelOneDao
import com.ad_coding.plannettestapp.data.local.dao.ProductLevelTwoDao
import com.ad_coding.plannettestapp.data.local.database.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ProductDatabase =
        Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "plannet_test_db"
        ).fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideProductLevelOneDao(database: ProductDatabase): ProductLevelOneDao {
        return database.productLevelOneDao()
    }

    @Provides
    fun provideProductLevelTwoDao(database: ProductDatabase): ProductLevelTwoDao {
        return database.productLevelTwoDao()
    }

    @Provides
    fun provideProductGradeDao(database: ProductDatabase): ProductGradeDao {
        return database.productGradeDao()
    }
}