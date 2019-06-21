package com.mentormate.mentornet.adapter.retrofit.rank

/**
 * Created by vasil.mitov@mentormate.com on 18/03/19.
 */

data class RankedEmployeeDto(
    val crystals: Int = 0,
    val employeeId: Int = 0,
    val name: String = "",
    val position: Int = 0
)