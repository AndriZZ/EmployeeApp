package com.mentormate.mentornet.adapter.retrofit.trainings

import com.mentormate.mentornet.adapter.retrofit.NetworkBoundRepository
import com.mentormate.mentornet.data.trainings.Training
import com.mentormate.mentornet.data.trainings.TrainingDao
import com.mentormate.mentornet.data.trainings.Trainings
import retrofit2.Call
import javax.inject.Inject

class TrainingNetworkBoundRepository @Inject constructor(
    private val trainingDao: TrainingDao,
    private val trainingService: TrainingService

) : NetworkBoundRepository<List<Training>, Trainings>() {


    override fun adapt(dao: Trainings): List<Training> {
        return dao.data.map {
            Training(
                it.courseIds,
                it.id,
                it.kingdom,
                it.name,
                it.orderNumber
            )
        }
    }

    override fun loadFromNetworkCalls(): List<Call<Trainings>> {
        return listOf(trainingService.getTrainings())
    }

    override fun loadFromDb(): List<Training> {
        return trainingDao.getTrainings()
    }

    override fun addToDb(data: List<Training>) {
        trainingDao.insert(data)
    }
}