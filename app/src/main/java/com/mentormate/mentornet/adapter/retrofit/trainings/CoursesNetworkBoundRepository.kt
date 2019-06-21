package com.mentormate.mentornet.adapter.retrofit.trainings

import com.mentormate.mentornet.adapter.retrofit.NetworkBoundRepository
import com.mentormate.mentornet.data.trainings.АllCourseDao
import com.mentormate.mentornet.data.trainings.EmployeeTrainingDto
import com.mentormate.mentornet.data.trainings.EmployeeTrainingsFull
import retrofit2.Call
import javax.inject.Inject


class CoursesNetworkBoundRepository
@Inject constructor(
    private val аllCourseDao: АllCourseDao,
    private val trainingService: TrainingService

) : NetworkBoundRepository<List<EmployeeTrainingsFull>, EmployeeTrainingDto>() {
    override fun loadFromNetworkCalls(): List<Call<EmployeeTrainingDto>> {
        return listOf(trainingService.getEmployeeCourses())
    }

    override fun adapt(dto: EmployeeTrainingDto): List<EmployeeTrainingsFull> {
        return dto.data.map {

            EmployeeTrainingsFull(
                it.id,
                it.name,
                it.orderNumber
            )
        }
    }

    override fun loadFromDb(): List<EmployeeTrainingsFull>? {
        return аllCourseDao.getAllCoursesLiveData().value
    }

    override fun addToDb(data: List<EmployeeTrainingsFull>) {
        аllCourseDao.insertAll(data)
    }
}