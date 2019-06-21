package com.mentormate.mentornet.adapter.retrofit.employee

data class GeneralInformationDto(
    val areas: List<AreaDto> = listOf(),
    val clients: List<ClientDto> = listOf(),
    val countries: List<CountryDto> = listOf(),
    val departments: List<DepartmentDto> = listOf(),
    val lastUpdateTimeStamp: Long = 0L,
    val offices: List<OfficeDto> = listOf(),
    val positions: List<PositionDto> = listOf()
)