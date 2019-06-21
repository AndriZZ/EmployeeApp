package com.mentormate.mentornet.di.module

import android.content.Context
import com.mentormate.mentornet.data.trainings.TrainingDao
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee

import com.mentormate.mentornet.data.*
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import com.mentormate.mentornet.data.employee.CompleteEmployeeDao

import com.mentormate.mentornet.data.employee.EmployeeDao
import com.mentormate.mentornet.data.employee.GeneralInformationDao
import com.mentormate.mentornet.data.kudos.CompleteKudo
import com.mentormate.mentornet.data.kudos.CompleteKudoDao
import com.mentormate.mentornet.data.kudos.Kudo
import com.mentormate.mentornet.data.kudos.KudoDao
import com.mentormate.mentornet.data.leaderboard.LeaderBoardDao
import com.mentormate.mentornet.data.store.StoreItem
import com.mentormate.mentornet.data.store.StoreItemsDao
import com.mentormate.mentornet.data.trainings.АllCourseDao
import com.mentormate.mentornet.data.trainings.Training
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by vasil.mitov@mentormate.com on 11/02/19.
 */

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Named("employees")
    fun provideEmployees(completeEmployeeDa: CompleteEmployeeDao): List<CompleteEmployee> {
        return completeEmployeeDa.getCompleteEmployeesLiveData().value ?: listOf()
    }

    @Provides
    fun provideEmployeeDao(appDatabase: AppDatabase): EmployeeDao {
        return appDatabase.employeeDao()
    }

    @Provides
    fun provideStoreItemsDao(appDatabase: AppDatabase): StoreItemsDao {
        return appDatabase.storeItemsDao()
    }

    @Provides
    fun provideTrainingsDao(appDatabase: AppDatabase): TrainingDao {
        return appDatabase.trainingDao()
    }

    @Provides
    fun provideAuthCredentialsDao(appDatabase: AppDatabase): AuthCredentialsDao {
        return appDatabase.authCredentialsDao()
    }

    @Provides
    fun provideKudoDao(appDatabase: AppDatabase): KudoDao {
        return appDatabase.kudoDao()
    }

    @Provides
    fun provideCompleteKudoDao(appDatabase: AppDatabase): CompleteKudoDao {
        return appDatabase.completeKudoDao()
    }

    @Provides
    @Named("comp_kudos")
    fun providesKudos(completeKudoDao: CompleteKudoDao): List<CompleteKudo> {
        return completeKudoDao.getCompleteKudos().value ?: listOf()
    }


    @Provides
    @Named("store_items")
    fun providesStoreItems(storeItemsDao: StoreItemsDao): List<StoreItem> {
        return storeItemsDao.getStoreItemsLiveData().value ?: listOf()
    }


    @Provides
    @Named("kudos")
    fun providesKudosDao(kudoDao: KudoDao): List<Kudo> {
        return kudoDao.getKudosLiveData().value ?: listOf()
    }
    @Provides
    @Named("trainings")
    fun providesTrainings(trainingDao: TrainingDao): List<Training> {
        return trainingDao.getTrainigsLiveData().value ?: listOf()
    }


    @Provides
    fun provideGeneralInformationDao(appDatabase: AppDatabase): GeneralInformationDao {
        return appDatabase.generalInformationDao()
    }

    @Provides
    fun provideCompleteEmployeeDao(appDatabase: AppDatabase): CompleteEmployeeDao {
        return appDatabase.getCompleteEmployeeDao()
    }

    @Provides
    fun provideLeaderBoardDao(appDatabase: AppDatabase): LeaderBoardDao {
        return appDatabase.getLeaderBoardsDao()
    }

    @Provides
    fun provideCourseDao(appDatabase: AppDatabase): АllCourseDao {
        return appDatabase.courseDao()
    }



}