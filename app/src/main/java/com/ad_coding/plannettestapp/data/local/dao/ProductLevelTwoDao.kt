package com.ad_coding.plannettestapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ad_coding.plannettestapp.data.local.entity.ProductLevelTwoEntity

@Dao
interface ProductLevelTwoDao {
    @Query("SELECT * FROM level_two_products")
    suspend fun getProducts(): List<ProductLevelTwoEntity>

    @Query("SELECT * FROM level_two_products WHERE id = :id")
    suspend fun getProductByIdAndLevel(id: Int): ProductLevelTwoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductLevelTwoEntity>)

    @Query("DELETE FROM level_two_products")
    suspend fun clearProducts()

    @Transaction
    suspend fun replaceProducts(products: List<ProductLevelTwoEntity>) {
        clearProducts()
        insertProducts(products)
    }
}
