package com.mentormate.mentornet.data.trainings

import androidx.room.Entity
import androidx.room.PrimaryKey

data class EmployeeTrainingDto(
    val data: List<EmployeeTrainingsFullDto>
)

data class EmployeeTrainingsFullDto(
    val courseCategory: CourseCategory,
    val id: Int,
    val name: String,
    val orderNumber: Int
)
@Entity(tableName = "all_courses")
data class EmployeeTrainingsFull(
    @PrimaryKey
    val id: Int,
    val name: String,
    val orderNumber: Int
)
data class CourseCategory(
    val courseIds: List<Int>,
    val id: Int,
    val kingdom: Kingdom,
    val name: String,
    val orderNumber: Int
)

data class OrderedTrainingCategory(
    val id: Int,
    val orderNumber: Int,
    val name: String,
    val kingdom: Kingdom,
    val category_name: String

)





