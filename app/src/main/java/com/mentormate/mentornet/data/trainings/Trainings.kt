package com.mentormate.mentornet.data.trainings

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Trainings(
    val data: List<Training> = listOf()
)

@Entity(tableName = "trainings")
data class Training(
    val courseIds: List<Int> = listOf(),
    @PrimaryKey
    val id: Int,
    val kingdom: Kingdom,
    val name: String,
    var orderNumber: Int

)

data class Kingdom(
    val id: Int,
    val name: String
)


class KingdomConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun toString(positions: Kingdom): String {
        return gson.toJson(positions)
    }

    @TypeConverter
    fun toPositions(json: String): Kingdom {
        return gson.fromJson(json, object : TypeToken<Kingdom>() {}.type)
    }
}

class CourseIdsConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun toString(positions: List<Int>): String {
        return gson.toJson(positions)
    }

    @TypeConverter
    fun toPosition(json: String): List<Int> {
        return gson.fromJson(json, object : TypeToken<List<Int>>() {}.type)
    }
}




