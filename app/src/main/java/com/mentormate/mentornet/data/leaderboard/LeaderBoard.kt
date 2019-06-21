package com.mentormate.mentornet.data.leaderboard

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by vasil.mitov@mentormate.com on 19/03/19.
 */

@Entity(tableName = "leaderboards", indices = [Index(value = ["categoryName"], unique = true)])
data class LeaderBoard(
    @PrimaryKey val categoryId: Int,
    val categoryName: String,
    val leaderBoardPositions: LeaderBoardPositions
)

data class LeaderBoardPositions(val leaderBoardPositions: List<LeaderBoardPosition>)

data class LeaderBoardPosition(
    val position: Int,
    val employeeId: Int
)

class LeaderBoardPositionsConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun toString(positions: LeaderBoardPositions): String {
        return gson.toJson(positions)
    }

    @TypeConverter
    fun toPositions(json: String): LeaderBoardPositions {
        return gson.fromJson(json, object : TypeToken<LeaderBoardPositions>() {}.type)
    }
}

class LeaderBoardPositionConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun toString(positions: List<LeaderBoardPosition>): String {
        return gson.toJson(positions)
    }

    @TypeConverter
    fun toPosition(json: String): List<LeaderBoardPosition> {
        return gson.fromJson(json, object : TypeToken<List<LeaderBoardPosition>>() {}.type)
    }
}