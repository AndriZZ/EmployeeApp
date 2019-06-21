package com.mentormate.mentornet.adapter.retrofit.employee

data class EmployeeListDto(
    val data: List<EmployeeDto> = listOf(),
    val lastUpdateTimeStamp: Long = 0L,
    val totalNumber: Int = 0
)

data class EmployeeDto(
    val areaId: Int = 0,
    val availableCrystals: Int = 0,
    val badges: List<Any> = listOf(),
    val birthDate: String = "",
    val clientIds: List<Int> = listOf(),
    val courseIds: List<Int> = listOf(),
    val currentCrystalsAmount: Int = 0,
    val departmentId: Int = 0,
    val earnedCrystals: EarnedCrystals = EarnedCrystals(),
    val earnedCrystalsPerCategories: List<Any> = listOf(),
    val email: String = "",
    val employeeMessages: List<Any> = listOf(),
    val firstName: String = "",
    val hobbies: List<Any> = listOf(),
    val id: Int = 0,
    val imageName: String = "",
    val ipPhone: String = "",
    val isActive: Boolean = false,
    val lastName: String = "",
    val officeId: Int = 0,
    val positionIds: List<Int> = listOf(),
    val rank: Int = 0,
    val reviewFormUri: String = "",
    val skype: String = "",
    val spentCrystals: List<Any> = listOf(),
    val startDate: String = "",
    val totalSpentAmount: Int = 0,
    val transfers: List<Any> = listOf()
)

class EarnedCrystals(
)