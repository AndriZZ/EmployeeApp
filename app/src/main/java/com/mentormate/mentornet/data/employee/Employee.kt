package com.mentormate.mentornet.data.employee

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity(tableName = "employees", indices = [Index(value = ["email"], unique = true)])
data class Employee(
    @PrimaryKey val id: Int?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val imageUrl: String,
    val positionId: Int,
    val officeId: Int,
    val crystals: Int,
    val feedbackFormUrl: String,
    val clientIds: List<Int>,
    val startDate: String,
    val courseIds:List<Int>
) : Serializable

