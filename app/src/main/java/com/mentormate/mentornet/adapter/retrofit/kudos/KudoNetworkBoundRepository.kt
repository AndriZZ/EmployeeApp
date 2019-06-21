package com.mentormate.mentornet.adapter.retrofit.kudos

import com.mentormate.mentornet.adapter.retrofit.NetworkBoundRepository
import com.mentormate.mentornet.data.kudos.Kudo
import com.mentormate.mentornet.data.kudos.KudoDao
import retrofit2.Call
import javax.inject.Inject

class KudoNetworkBoundRepository @Inject constructor(
    private val kudoDao: KudoDao,
    private val kudoService: KudoService

) : NetworkBoundRepository<List<Kudo>, List<Kudo>>() {
    override fun loadFromNetworkCalls(): List<Call<List<Kudo>>> {
        return listOf(kudoService.getKudos())
    }

    override fun adapt(dao: List<Kudo>): List<Kudo> {

        return dao.map {
            Kudo(
                it.creationDate,
                it.employeeFromId,
                it.employeeFromName,
                it.employeeToId,
                it.employeeToName,
                it.id,
                it.isVisible,
                it.message,
                it.subject

            )
        }
    }

    override fun loadFromDb(): List<Kudo> {
        return kudoDao.getKudos()
    }

    override fun addToDb(data: List<Kudo>) {
        kudoDao.insert(data)
    }
}