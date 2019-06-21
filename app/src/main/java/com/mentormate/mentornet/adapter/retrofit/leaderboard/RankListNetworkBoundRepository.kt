package com.mentormate.mentornet.adapter.retrofit.leaderboard

import android.content.Context
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.NetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.rank.RankListDto
import com.mentormate.mentornet.adapter.retrofit.rank.RankService
import com.mentormate.mentornet.data.leaderboard.LeaderBoard
import com.mentormate.mentornet.data.leaderboard.LeaderBoardDao
import com.mentormate.mentornet.data.leaderboard.LeaderBoardPosition
import com.mentormate.mentornet.data.leaderboard.LeaderBoardPositions
import com.mentormate.mentornet.utilities.CRYSTALS_LEADERBOARD_ID
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by vasil.mitov@mentormate.com on 18/03/19.
 */

class RankListNetworkBoundRepository
@Inject constructor(
    private val rankService: RankService,
    private val leaderBoardDao: LeaderBoardDao,
    private val context: Context
) : NetworkBoundRepository<List<LeaderBoard>, RankListDto>() {
    override fun loadFromDb(): List<LeaderBoard> {
        return leaderBoardDao.getLeaderBoards().value ?: listOf()
    }

    override fun addToDb(data: List<LeaderBoard>) {
        leaderBoardDao.insert(data)
    }

    override fun loadFromNetworkCalls(): List<Call<RankListDto>> {

        return listOf(rankService.getRankLists())


    }

    override fun adapt(dto: RankListDto): List<LeaderBoard> {
        val leaderBoards = dto.rankByCategories.map { category ->
            LeaderBoard(
                category.categoryId,
                category.categoryName,
                LeaderBoardPositions(category.employees.map { employee ->
                    LeaderBoardPosition(
                        employee.position,
                        employee.employeeId
                    )
                })
            )
        }
        val mutableLeaderBoards = leaderBoards.toMutableList()

        //Backend doesn't provide the crystals category, but it provides rankAll which represents it
        mutableLeaderBoards.add(
            LeaderBoard(
                CRYSTALS_LEADERBOARD_ID,
                context.getString(R.string.crystals_string),
                LeaderBoardPositions(dto.rankAll.map {
                    LeaderBoardPosition(
                        it.position,
                        it.employeeId
                    )
                })
            )
        )

        return mutableLeaderBoards
    }
}