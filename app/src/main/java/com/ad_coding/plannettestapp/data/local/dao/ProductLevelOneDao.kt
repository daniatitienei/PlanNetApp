package com.ad_coding.plannettestapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ad_coding.plannettestapp.data.local.entity.ProductLevelOneEntity

@Dao
interface ProductLevelOneDao {
    @Query("SELECT * FROM level_one_products")
    suspend fun getProducts(): List<ProductLevelOneEntity>

    @Query("SELECT * FROM level_one_products WHERE id = :id")
    suspend fun getProductByIdAndLevel(id: Int): ProductLevelOneEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductLevelOneEntity>)

    @Query("DELETE FROM level_one_products")
    suspend fun clearProducts()

    @Transaction
    suspend fun replaceProducts(products: List<ProductLevelOneEntity>) {
        clearProducts()
        insertProducts(products)
    }
}
