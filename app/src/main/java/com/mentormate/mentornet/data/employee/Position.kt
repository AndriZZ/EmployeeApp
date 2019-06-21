package com.mentormate.mentornet.data.employee

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_positions")
data class Position(
    @PrimaryKey val id: Int,
    val positionName: String
)