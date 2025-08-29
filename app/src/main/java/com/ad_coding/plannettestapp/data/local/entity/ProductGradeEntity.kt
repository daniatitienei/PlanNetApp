package com.ad_coding.plannettestapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product_grades")
data class ProductGradeEntity(
    @ColumnInfo(name = "product_id")
    @PrimaryKey val productId: Int,
    val name: String,
    val grade: String,
    val timestamp: Long
)
