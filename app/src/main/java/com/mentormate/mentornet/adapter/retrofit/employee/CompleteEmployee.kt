package com.mentormate.mentornet.adapter.retrofit.employee

import androidx.room.ColumnInfo
import java.io.Serializable

/**
 * Created by vasil.mitov@mentormate.com on 13/03/19.
 */

data class CompleteEmployee(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "officeName")
    val city: String,
    @ColumnInfo(name = "positionName")
    val position: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    @ColumnInfo(name = "feedbackFormUrl")
    val feedbackForm: String,
    @ColumnInfo(name = "crystals")
    val crystalAmount: Int,
    @ColumnInfo(name = "clientIds")
    val clientIds: List<Int>,
    @ColumnInfo(name = "startDate")
    val startDate: String,
    @ColumnInfo(name = "courseIds")
    val courseIds: List<Int>


) : Serializable {
    constructor(): this(-1,"","","","","","","",0, emptyList(),"",emptyList())
}