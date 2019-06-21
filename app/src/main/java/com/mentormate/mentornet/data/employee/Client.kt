package com.mentormate.mentornet.data.employee

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class Client(
    @PrimaryKey var id: Int,
    val name: String
)