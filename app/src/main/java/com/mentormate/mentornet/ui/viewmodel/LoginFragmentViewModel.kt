package com.mentormate.mentornet.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mentormate.mentornet.data.AppDatabase
import com.mentormate.mentornet.data.authentication.AuthCredentials
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import com.mentormate.mentornet.utilities.AppExecutors
import javax.inject.Inject

class LoginFragmentViewModel
@Inject constructor(
    private val authCredentialsDao: AuthCredentialsDao,
    private val appDatabase: AppDatabase,
    private val appExecutors: AppExecutors
) : ViewModel() {
    fun getAuthData(): LiveData<AuthCredentials> = appDatabase.authCredentialsDao().getAuthLiveData()
    fun insertGoogleToken(authCredentials: AuthCredentials) {
        appExecutors.diskIO.execute {
            authCredentialsDao.insertAuth(authCredentials)
        }
    }
}
