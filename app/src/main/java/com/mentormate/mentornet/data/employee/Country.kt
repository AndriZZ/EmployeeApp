package com.mentormate.mentornet.data.employee

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "countries")
data class Country(
    @PrimaryKey val id: Int,
    val isoCode: String,
    val name: String
)