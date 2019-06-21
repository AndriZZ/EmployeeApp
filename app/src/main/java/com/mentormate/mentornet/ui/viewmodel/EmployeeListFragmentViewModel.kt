package com.mentormate.mentornet.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.data.employee.CompleteEmployeeDao
import javax.inject.Inject

class EmployeeListViewModel
@Inject constructor(
    private val completeEmployeeDao: CompleteEmployeeDao
) : ViewModel() {
    fun getCompleteEmployee(): LiveData<List<CompleteEmployee>> = completeEmployeeDao.getCompleteEmployeesLiveData()
}