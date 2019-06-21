package com.mentormate.mentornet.data.kudos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kudos")
data class Kudo(
    val creationDate: String,
    val employeeFromId: Int,
    val employeeFromName: String,
    val employeeToId: Int,
    val employeeToName: String,
    @PrimaryKey
    val id: Int,
    val isVisible: Boolean,
    val message: String,
    val subject: String
)

data class KudoPost(
    val employeeToId: Int,
    val message: String
)