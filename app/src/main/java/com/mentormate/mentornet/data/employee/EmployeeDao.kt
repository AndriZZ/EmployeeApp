package com.mentormate.mentornet.data.employee

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Query("select * from employees order by id")
    fun getAllEmpoyees(): List<Employee>

    @Query("select * from employees order by id")
    fun getAllEmpoyeesLiveData(): LiveData<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(employees: List<Employee>)

    @Query("select clients.name,clients.id from clients inner join employees on employees.clientIds  LIKE ('%' || clients.id || '%') where employees.id = :id")
    fun getEmployeeClients(id: Int?): LiveData<List<Client>>

}

