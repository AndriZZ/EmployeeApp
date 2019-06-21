package com.mentormate.mentornet.data.kudos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface CompleteKudoDao {
    @Query("select employees.imageUrl,kudos.* from employees, kudos where employees.id =kudos.employeeFromId order by kudos.id desc")
    fun getCompleteKudos(): LiveData<List<CompleteKudo>>

    @Query("select employees.imageUrl,kudos.* from employees, kudos where employees.id =kudos.employeeFromId AND kudos.employeeToId=:id")
    fun getKudosForId(id: Int?): LiveData<List<CompleteKudo>>

    @Query("select employees.imageUrl,kudos.* from employees, kudos where employees.id =kudos.employeeFromId AND kudos.employeeFromId=:id")
    fun getKudosFromId(id: Int?): LiveData<List<CompleteKudo>>

}