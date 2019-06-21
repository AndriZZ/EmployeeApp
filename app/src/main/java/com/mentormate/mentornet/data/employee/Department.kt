package com.mentormate.mentornet.data.employee

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "departments")
data class Department(
    val headOfDepartmentEmployeeId: Int,
    @PrimaryKey val id: Int?,
    val name: String,
    val notes: String
)