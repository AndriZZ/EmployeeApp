package com.mentormate.mentornet.data.leaderboard

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by vasil.mitov@mentormate.com on 18/03/19.
 */

@Dao
interface LeaderBoardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(leaderboards: List<LeaderBoard>)

    @Query("select * from leaderboards order by categoryId asc")
    fun getLeaderBoards(): LiveData<List<LeaderBoard>>

    @Query("select * from leaderboards order by categoryId asc")
    fun getLeaderBoardsNotLive(): List<LeaderBoard>
}