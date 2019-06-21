package com.mentormate.mentornet.adapter.retrofit.employee

import retrofit2.http.GET

/**
 * Created by vasil.mitov@mentormate.com on 13/03/19.
 */

interface GeneralInformationService{

    @GET("generalInformation/getInformation")
    fun getGeneralInformation() : retrofit2.Call<GeneralInformationDto>
}