package com.mentormate.mentornet.data.employee

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offices")
data class Office(
    val countryId: Int,
    @PrimaryKey val id: Int,
    val officeName: String
)