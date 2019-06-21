package com.mentormate.mentornet.adapter.retrofit.store

import com.mentormate.mentornet.adapter.retrofit.NetworkBoundRepository
import com.mentormate.mentornet.data.store.StoreItem
import com.mentormate.mentornet.data.store.StoreItems
import com.mentormate.mentornet.data.store.StoreItemsDao
import retrofit2.Call
import javax.inject.Inject

class StoreNetworkBoundRepository @Inject constructor(
    private val storeItemsDao: StoreItemsDao,
    private val storeService: StoreService

) : NetworkBoundRepository<List<StoreItem>, StoreItems>() {

    override fun adapt(dto: StoreItems): List<StoreItem> {
        return dto.data.map {
            StoreItem(
                it.id,
                it.description,
                it.imageName,
                it.isActive,
                it.name,
                it.price
            )
        }
    }

    override fun loadFromNetworkCalls(): List<Call<StoreItems>> {
        return listOf(storeService.getStoreItems())
    }

    override fun loadFromDb(): List<StoreItem> {
        return storeItemsDao.getStoreItems()
    }

    override fun addToDb(data: List<StoreItem>) {

        storeItemsDao.insert(data.sortedBy { storeItem -> storeItem.price })
    }
}