package com.ad_coding.plannettestapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ad_coding.plannettestapp.data.local.entity.ProductGradeEntity

@Dao
interface ProductGradeDao {
    @Query("SELECT * FROM product_grades")
    suspend fun getProductGrades(): List<ProductGradeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductGradeEntity>)

    @Query("DELETE FROM product_grades")
    suspend fun clearProducts()

    @Transaction
    suspend fun replaceProductGradeList(products: List<ProductGradeEntity>) {
        clearProducts()
        insertProducts(products)
    }
}
