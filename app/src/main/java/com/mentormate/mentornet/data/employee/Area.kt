package com.mentormate.mentornet.data.employee

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "areas")
data class Area(
    @PrimaryKey val id: Int,
    val name: String,
    val officeId: Int
)