package com.mentormate.mentornet.adapter.retrofit.store

import com.mentormate.mentornet.data.store.StoreItems
import retrofit2.http.GET

interface StoreService {

    @GET("store-items")
    fun getStoreItems(): retrofit2.Call<StoreItems>
}