package com.mentormate.mentornet.adapter.retrofit.employee

import retrofit2.http.GET

interface EmployeeService {

    @GET("employees/listAll")
    fun getEmployees(): retrofit2.Call<EmployeeListDto>
}