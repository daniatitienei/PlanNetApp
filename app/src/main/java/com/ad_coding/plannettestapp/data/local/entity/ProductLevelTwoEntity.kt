package com.ad_coding.plannettestapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level_two_products")
data class ProductLevelTwoEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    @ColumnInfo(name = "client_count")
    val clientCount: Int,
    val timestamp: Long
)
