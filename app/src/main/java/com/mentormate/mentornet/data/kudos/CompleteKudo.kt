package com.mentormate.mentornet.data.kudos

import com.mentormate.mentornet.data.store.GeneralItemType
import java.io.Serializable

data class CompleteKudo(
    val imageUrl: String,
    val creationDate: String,
    val employeeFromId: Int,
    val employeeFromName: String,
    val employeeToId: Int,
    val employeeToName: String,
    val id: Int,
    val isVisible: Boolean,
    val message: String,
    val subject: String
) : GeneralItemType, Serializable



