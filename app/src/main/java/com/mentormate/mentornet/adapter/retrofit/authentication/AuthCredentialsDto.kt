package com.mentormate.mentornet.adapter.retrofit.authentication

import com.mentormate.mentornet.adapter.retrofit.employee.EmployeeDto


data class AuthCredentialsDto(
    val newToken: String,
    val employee: EmployeeDto,
    val roleCodes: List<String>
)

