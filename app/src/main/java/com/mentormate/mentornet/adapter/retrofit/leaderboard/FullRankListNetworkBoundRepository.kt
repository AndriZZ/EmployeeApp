package com.mentormate.mentornet.adapter.retrofit.leaderboard

import android.content.Context
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.NetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.rank.RankByCategoryDto
import com.mentormate.mentornet.adapter.retrofit.rank.RankService
import com.mentormate.mentornet.data.employee.CompleteEmployeeDao
import com.mentormate.mentornet.data.leaderboard.LeaderBoard
import com.mentormate.mentornet.data.leaderboard.LeaderBoardDao
import com.mentormate.mentornet.data.leaderboard.LeaderBoardPosition
import com.mentormate.mentornet.data.leaderboard.LeaderBoardPositions
import com.mentormate.mentornet.utilities.CRYSTALS_LEADERBOARD_ID
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by vasil.mitov@mentormate.com on 20/03/19.
 */

class FullRankListNetworkBoundRepository @Inject constructor(
    private val leaderBoardDao: LeaderBoardDao,
    private val rankService: RankService,
    private val completeEmployeeDao: CompleteEmployeeDao,
    private val context: Context
) : NetworkBoundRepository<List<LeaderBoard>, RankByCategoryDto>() {

    private var ids: List<Int> = listOf()

    override fun loadFromDb(): List<LeaderBoard>? {
        return leaderBoardDao.getLeaderBoards().value
    }

    override fun addToDb(data: List<LeaderBoard>) {
        val topTenEmployeesByCrystals = completeEmployeeDao.getTopTenEmployeesByCrystals()

        val leaderBoardPosition = mutableListOf<LeaderBoardPosition>()
        var positionIterator = 1

        topTenEmployeesByCrystals.forEach {
            leaderBoardPosition.add(
                LeaderBoardPosition(
                    positionIterator,
                    it.id
                )
            )
            positionIterator++
        }

        val mutableData = data.toMutableList()
        mutableData.add(
            LeaderBoard(
                CRYSTALS_LEADERBOARD_ID,
                context.getString(R.string.crystals_string),
                LeaderBoardPositions(leaderBoardPosition)
            )
        )

        leaderBoardDao.insert(mutableData)
    }

    override fun loadFromNetworkCalls(): List<Call<RankByCategoryDto>> {
        return ids.map {
            rankService.getRankList(it)
        }
    }

    override fun adapt(dto: RankByCategoryDto): List<LeaderBoard> {
        return listOf(
            LeaderBoard(dto.categoryId,
                dto.categoryName,
                LeaderBoardPositions(
                    dto.employees.map {
                        LeaderBoardPosition(
                            it.position,
                            it.employeeId
                        )
                    }
                )))
    }

    fun setIdsToLoad(ids: List<Int>) {
        this.ids = ids
    }
}