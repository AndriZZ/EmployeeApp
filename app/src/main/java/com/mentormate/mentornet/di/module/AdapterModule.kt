package com.mentormate.mentornet.di.module

import android.content.Context
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.data.kudos.CompleteKudo
import com.mentormate.mentornet.data.store.StoreItem
import com.mentormate.mentornet.data.trainings.Training
import com.mentormate.mentornet.ui.adapter.*
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by vasil.mitov@mentormate.com on 20/02/19.
 */

@Module
class AdapterModule {

    @Provides
    @Singleton
    fun provideEmployeeListAdapter(@Named("employees") employees: List<CompleteEmployee>, context: Context): PeopleAdapter {
        return PeopleAdapter(employees, context)
    }

    @Provides
    @Singleton
    fun provideBoardsLeadersAdapter(context: Context): BoardsLeadersAdapter {
        return BoardsLeadersAdapter(context)
    }

    @Provides
    fun provideStoreItemsListAdapter(@Named("store_items") storeItems: List<StoreItem>, context: Context): StoreItemsAdapter {
        return StoreItemsAdapter(storeItems, context)
    }

    @Provides
    fun provideClientsAdapter(): ClientsAdapter {
        return ClientsAdapter()
    }



}