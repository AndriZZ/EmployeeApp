package com.mentormate.mentornet.data.store

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoreItemsDao {
    @Query("select * from store_items order by price")
    fun getStoreItems(): List<StoreItem>

    @Query("select * from store_items order by price")
    fun getStoreItemsLiveData(): LiveData<List<StoreItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(storeItem: StoreItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(storeItems: List<StoreItem>)
}