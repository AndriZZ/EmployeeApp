package com.mentormate.mentornet.data.kudos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface KudoDao {

    @Query("select * from kudos order by id desc")
    fun getKudosLiveData(): LiveData<List<Kudo>>

    @Query("select * from kudos order by id desc")
    fun getKudos(): List<Kudo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kudos: List<Kudo>)

}