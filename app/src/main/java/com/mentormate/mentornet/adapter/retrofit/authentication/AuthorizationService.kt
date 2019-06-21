package com.mentormate.mentornet.adapter.retrofit.authentication

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthorizationService {
    @FormUrlEncoded
    @POST("oauth/authorize")
    fun authorize(@Field("authCode") key: String?): retrofit2.Call<AuthCredentialsDto>
}

