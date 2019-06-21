package com.mentormate.mentornet.data.leaderboard

import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee

/**
 * Created by vasil.mitov@mentormate.com on 15/03/19.
 */

data class CompleteLeaderBoard(val id: Int, val title: String, val leaders: List<CompleteEmployee>)