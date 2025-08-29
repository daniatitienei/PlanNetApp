package com.ad_coding.plannettestapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ad_coding.plannettestapp.data.local.dao.ProductGradeDao
import com.ad_coding.plannettestapp.data.local.dao.ProductLevelOneDao
import com.ad_coding.plannettestapp.data.local.dao.ProductLevelTwoDao
import com.ad_coding.plannettestapp.data.local.entity.ProductGradeEntity
import com.ad_coding.plannettestapp.data.local.entity.ProductLevelOneEntity
import com.ad_coding.plannettestapp.data.local.entity.ProductLevelTwoEntity

@Database(
    entities = [ProductLevelOneEntity::class, ProductLevelTwoEntity::class, ProductGradeEntity::class],
    version = 4,
    exportSchema = false
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productLevelOneDao(): ProductLevelOneDao
    abstract fun productLevelTwoDao(): ProductLevelTwoDao
    abstract fun productGradeDao(): ProductGradeDao
}