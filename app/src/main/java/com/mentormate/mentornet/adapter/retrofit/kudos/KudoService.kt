package com.mentormate.mentornet.adapter.retrofit.kudos

import com.mentormate.mentornet.data.kudos.Kudo
import com.mentormate.mentornet.data.kudos.KudoPost
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface KudoService {
    @GET("employees/message")
    fun getKudos(): retrofit2.Call<List<Kudo>>

    @POST("employees/message")
    fun postKudo(@Body postKudo: KudoPost): retrofit2.Call<Void>
}