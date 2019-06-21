package com.mentormate.mentornet.data.employee

data class GeneralInformation(
    val areas: List<Area>,
    val clients: List<Client>,
    val countries: List<Country>,
    val departments: List<Department>,
    val offices: List<Office>,
    val positions: List<Position>
)