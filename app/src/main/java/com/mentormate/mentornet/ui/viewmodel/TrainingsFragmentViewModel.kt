package com.mentormate.mentornet.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mentormate.mentornet.data.trainings.TrainingDao
import com.mentormate.mentornet.data.trainings.Training
import javax.inject.Inject

class TrainingsFragmentViewModel
@Inject constructor(
    private val trainingDao: TrainingDao
) : ViewModel() {
    fun getTrainings(): LiveData<List<Training>> = trainingDao.getTrainigsLiveData()
}

