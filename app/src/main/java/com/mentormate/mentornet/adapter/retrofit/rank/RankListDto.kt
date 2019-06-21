package com.mentormate.mentornet.adapter.retrofit.rank

/**
 * Created by vasil.mitov@mentormate.com on 19/03/19.
 */

data class RankListDto(
    val rankAll: List<RankAllDto> = listOf(),
    val rankByCategories: List<RankByCategoryDto> = listOf(),
    val totalNumberRankAll: Int = 0,
    val totalNumberRankByCategories: Int = 0
)