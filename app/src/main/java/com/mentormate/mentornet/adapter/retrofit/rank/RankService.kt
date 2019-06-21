package com.mentormate.mentornet.adapter.retrofit.rank

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by vasil.mitov@mentormate.com on 18/03/19.
 */

interface RankService {

    @GET("rank-list")
    fun getRankLists(): retrofit2.Call<RankListDto>

    @GET("rank-list/{id}")
    fun getRankList(@Path("id")  id: Int): retrofit2.Call<RankByCategoryDto>
}