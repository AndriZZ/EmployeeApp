package com.mentormate.mentornet.data.employee

import com.mentormate.mentornet.adapter.retrofit.NetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.employee.GeneralInformationDto
import com.mentormate.mentornet.adapter.retrofit.employee.GeneralInformationService
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by vasil.mitov@mentormate.com on 13/03/19.
 */

class GeneralInformationNetworkBoundRepository @Inject constructor(
    private val generalInformationDao: GeneralInformationDao,
    private val generalInformationService: GeneralInformationService
) : NetworkBoundRepository<GeneralInformation, GeneralInformationDto>() {
    override fun loadFromDb(): GeneralInformation {
        val allAreas = generalInformationDao.getAllAreas()
        val allClients = generalInformationDao.getAllClients()
        val allCountries = generalInformationDao.getAllCountries()
        val allDepartments = generalInformationDao.getAllDepartments()
        val allOffices = generalInformationDao.getAllOffices()
        val allPositions = generalInformationDao.getAllPositions()

        return GeneralInformation(
            allAreas,
            allClients,
            allCountries,
            allDepartments,
            allOffices,
            allPositions
        )
    }

    override fun addToDb(data: GeneralInformation) {
        generalInformationDao.insertAreas(data.areas)
        generalInformationDao.insertClients(data.clients)
        generalInformationDao.insertCountries(data.countries)
        generalInformationDao.insertDepartments(data.departments)
        generalInformationDao.insertOffices(data.offices)
        generalInformationDao.insertPositions(data.positions)
    }

    override fun loadFromNetworkCalls(): List<Call<GeneralInformationDto>> {
        return listOf(generalInformationService.getGeneralInformation())
    }

    override fun adapt(dto: GeneralInformationDto): GeneralInformation {
        return GeneralInformation(
            dto.areas.map { Area(it.id, it.name, it.officeId) },
            dto.clients.map { Client(it.id, it.name) },
            dto.countries.map { Country(it.id, it.isoCode, it.name) },
            dto.departments.map { Department(it.headOfDepartmentEmployeeId, it.id, it.name, it.notes) },
            dto.offices.map { Office(it.countryId, it.id, it.name) },
            dto.positions.map { Position(it.id, it.name) }
        )
    }
}