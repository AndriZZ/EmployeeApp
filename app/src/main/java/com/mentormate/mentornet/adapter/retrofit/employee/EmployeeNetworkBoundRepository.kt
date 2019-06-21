package com.mentormate.mentornet.adapter.retrofit.employee

import com.mentormate.mentornet.adapter.retrofit.NetworkBoundRepository
import com.mentormate.mentornet.data.employee.Employee
import com.mentormate.mentornet.data.employee.EmployeeDao
import retrofit2.Call
import javax.inject.Inject


class EmployeeNetworkBoundRepository
@Inject constructor(
    private val employeeDao: EmployeeDao,
    private val employeeService: EmployeeService

) : NetworkBoundRepository<List<Employee>, EmployeeListDto>() {
    override fun loadFromNetworkCalls(): List<Call<EmployeeListDto>> {
        return listOf(employeeService.getEmployees())
    }

    override fun adapt(dto: EmployeeListDto): List<Employee> {
        return dto.data.map {
            var positionId = 0
            if (it.positionIds.isNotEmpty()) {
                positionId = it.positionIds[0]
            }

            Employee(
                it.id,
                it.firstName,
                it.lastName,
                it.email,
                it.imageName,
                positionId,
                it.officeId,
                it.availableCrystals,
                it.reviewFormUri,
                it.clientIds,
                it.startDate,
                it.courseIds
            )
        }
    }

    override fun loadFromDb(): List<Employee> {
        return employeeDao.getAllEmpoyees()
    }

    override fun addToDb(data: List<Employee>) {
        employeeDao.insertAll(data)
    }
}