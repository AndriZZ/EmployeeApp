package com.mentormate.mentornet.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.data.authentication.AuthCredentials
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import com.mentormate.mentornet.data.employee.CompleteEmployeeDao
import com.mentormate.mentornet.utilities.AppExecutors
import javax.inject.Inject


class ProfileViewModel @Inject constructor(
    private val authCredsDao: AuthCredentialsDao,
    private val completeEmployeeDao: CompleteEmployeeDao,
    appExecutors: AppExecutors
) : ViewModel() {
    val completeEmployee: MediatorLiveData<CompleteEmployee> = MediatorLiveData()
    lateinit var currentUserAuthCreds: AuthCredentials

    init {
        appExecutors.diskIO.execute {
            appExecutors.mainThread
            currentUserAuthCreds = authCredsDao.getAuthData()
            completeEmployee.addSource(completeEmployeeDao.getCurrentEmployeeLiveData(currentUserAuthCreds.id!!)) {
                it.city
            }

        }
    }

    fun getCurrentEmployee(): CompleteEmployee {
        return completeEmployeeDao.getCurrentEmployee(authCredsDao.getAuthData().id!!)
    }

}
