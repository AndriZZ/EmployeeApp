package com.mentormate.mentornet.data.store

import androidx.room.Entity
import androidx.room.PrimaryKey

data class StoreItems(
    val data: List<StoreItem> = listOf(),
    val totalNumber: Int=0
)
interface GeneralItemType

data class TextStoreItem(val textValue:String): GeneralItemType

@Entity(tableName = "store_items")
data class StoreItem(
    @PrimaryKey
    val id: Int,
    val description: String,
    val imageName: String,
    val isActive: Boolean,
    val name: String,
    val price: Int
): GeneralItemType

