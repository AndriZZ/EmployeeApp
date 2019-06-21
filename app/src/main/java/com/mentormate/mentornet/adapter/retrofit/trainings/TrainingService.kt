package com.mentormate.mentornet.adapter.retrofit.trainings

import com.mentormate.mentornet.data.trainings.EmployeeTrainingDto
import com.mentormate.mentornet.data.trainings.Trainings
import retrofit2.http.GET


interface TrainingService {
    @GET("course-categories")
    fun getTrainings(): retrofit2.Call<Trainings>

    @GET("courses")
    fun getEmployeeCourses(): retrofit2.Call<EmployeeTrainingDto>
}
