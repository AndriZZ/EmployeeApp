package com.mentormate.mentornet.data.employee

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee

/**
 * Created by vasil.mitov@mentormate.com on 13/03/19.
 */

@Dao
interface CompleteEmployeeDao {

    @Query("select employee.id, employee.firstName, employee.lastName, employee.email,employee.clientIds,employee.startDate,employee.courseIds ,office.officeName, position.positionName, employee.imageUrl, employee.feedbackFormUrl, employee.crystals from employees employee inner join offices office on employee.officeId = office.id inner join employee_positions position on employee.positionId = position.id")
    fun getCompleteEmployeesLiveData(): LiveData<List<CompleteEmployee>>

    @Query("select employee.id, employee.firstName, employee.lastName, employee.email,employee.clientIds,employee.startDate,employee.courseIds ,office.officeName, position.positionName, employee.imageUrl, employee.feedbackFormUrl, employee.crystals from employees employee inner join offices office on employee.officeId = office.id inner join employee_positions position on employee.positionId = position.id")
    fun getCompleteEmployees(): List<CompleteEmployee>

    @Query("select employee.id, employee.firstName, employee.lastName, employee.email,employee.clientIds,employee.startDate,employee.courseIds,office.officeName, position.positionName, employee.imageUrl, employee.feedbackFormUrl, employee.crystals from employees employee inner join offices office on employee.officeId = office.id inner join employee_positions position on employee.positionId = position.id order by crystals desc limit 10")
    fun getTopTenEmployeesByCrystals(): List<CompleteEmployee>

    @Query("select employee.id, employee.firstName, employee.lastName, employee.email,employee.clientIds,employee.startDate,employee.courseIds ,office.officeName, position.positionName, employee.imageUrl, employee.feedbackFormUrl, employee.crystals from employees employee inner join offices office on employee.officeId = office.id inner join employee_positions position on employee.positionId = position.id where employee.id = :id")
    fun getCurrentEmployee(id: Int): CompleteEmployee

    @Query("select employee.id, employee.firstName, employee.lastName, employee.email,employee.clientIds,employee.startDate,employee.courseIds ,office.officeName, position.positionName, employee.imageUrl, employee.feedbackFormUrl, employee.crystals from employees employee inner join offices office on employee.officeId = office.id inner join employee_positions position on employee.positionId = position.id where employee.id = :id")
    fun getCurrentEmployeeLiveData(id: Int): LiveData<CompleteEmployee>
}