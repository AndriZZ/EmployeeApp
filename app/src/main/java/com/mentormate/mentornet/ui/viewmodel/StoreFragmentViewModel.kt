package com.mentormate.mentornet.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mentormate.mentornet.data.AppDatabase

import com.mentormate.mentornet.data.store.StoreItem
import javax.inject.Inject

class StoreFragmentViewModel
@Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel() {
    fun getStoreItemsLiveData(): LiveData<List<StoreItem>> = appDatabase.storeItemsDao().getStoreItemsLiveData()
}