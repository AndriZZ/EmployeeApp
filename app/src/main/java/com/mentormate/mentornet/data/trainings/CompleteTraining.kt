package com.mentormate.mentornet.data.trainings

data class CompleteTraining(
    val courseIds: List<Int> = listOf(),
    val id: Int=0,
    val kingdom: Kingdom,
    val name: String,
    val orderNumber: Int,
    var percentage:Float=0F

)