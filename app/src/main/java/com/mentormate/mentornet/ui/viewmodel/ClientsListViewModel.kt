package com.mentormate.mentornet.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.mentormate.mentornet.data.employee.Client
import com.mentormate.mentornet.data.employee.EmployeeDao
import javax.inject.Inject

class ClientsListViewModel
@Inject constructor(
    private val employeeDao: EmployeeDao
) : ViewModel() {

    fun getEmployeeClients(id: Int): LiveData<List<Client>> { return employeeDao.getEmployeeClients(id)}

}