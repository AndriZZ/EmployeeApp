package com.mentormate.mentornet.data.employee

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by vasil.mitov@mentormate.com on 13/03/19.
 */

@Dao
interface GeneralInformationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAreas(areas: List<Area>)

    @Query("select * from areas")
    fun getAllAreas() : List<Area>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClients(clients: List<Client>)

    @Query("select * from clients")
    fun getAllClients() : List<Client>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<Country>)

    @Query("select * from countries")
    fun getAllCountries() : List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDepartments(departments: List<Department>)

    @Query("select * from departments")
    fun getAllDepartments() : List<Department>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffices(offices: List<Office>)

    @Query("select * from offices")
    fun getAllOffices() : List<Office>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPositions(positions: List<Position>)

    @Query("select * from employee_positions")
    fun getAllPositions() : List<Position>
}