package com.mentormate.mentornet.data.authentication

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AuthCredentialsDao {
    @Query("select * from auth_credentials order by id")
    fun getAuthLiveData(): LiveData<AuthCredentials>

    @Query("select * from auth_credentials order by id")
    fun getAuthData(): AuthCredentials

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuth(authCredentials: AuthCredentials?)

    @Query("delete from auth_credentials")
    fun deleteAuth()



    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCredentials(credentials: AuthCredentials)
}