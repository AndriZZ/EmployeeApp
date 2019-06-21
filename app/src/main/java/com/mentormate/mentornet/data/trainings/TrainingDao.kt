package com.mentormate.mentornet.data.trainings

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mentormate.mentornet.data.trainings.Training


@Dao
interface TrainingDao {
    @Query("select * from trainings order by id ")
    fun getTrainings(): List<Training>

    @Query("select * from trainings where trainings.id=:id ")
    fun getTraining(id:Int): Training

    @Query("select * from trainings order by id")
    fun getTrainigsLiveData(): LiveData<List<Training>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainings: List<Training>)
}