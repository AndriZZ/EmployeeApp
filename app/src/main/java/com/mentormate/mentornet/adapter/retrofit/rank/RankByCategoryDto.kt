package com.mentormate.mentornet.adapter.retrofit.rank

/**
 * Created by vasil.mitov@mentormate.com on 19/03/19.
 */

data class RankByCategoryDto(
    val categoryId: Int = 0,
    val categoryName: String = "",
    val employees: List<RankedEmployeeDto> = listOf()
)