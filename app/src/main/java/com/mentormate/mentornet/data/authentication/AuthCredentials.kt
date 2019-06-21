package com.mentormate.mentornet.data.authentication

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "auth_credentials",indices = [Index(value = ["googleToken"], unique = true)])
data class AuthCredentials(
    @PrimaryKey
    var id: Int?,
    var newToken: String?,
    var googleToken:String?
){
    companion object AuthCreds {
        val instance = AuthCredentials(0, "", "")
    }

}
