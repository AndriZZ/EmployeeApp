package com.mentormate.mentornet.adapter.retrofit.authentication


import android.content.Context
import android.os.AsyncTask
import com.mentormate.mentornet.adapter.retrofit.NetworkBoundRepository
import com.mentormate.mentornet.data.authentication.AuthCredentials
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import retrofit2.Call
import javax.inject.Inject


class AuthenticationNetworkBoundRepository
@Inject constructor(
    private val authCredentialsDao: AuthCredentialsDao,
    private val authService: AuthorizationService
) : NetworkBoundRepository<AuthCredentials, AuthCredentialsDto>() {

    override fun adapt(dto: AuthCredentialsDto): AuthCredentials {
        return AuthCredentials(dto.employee.id, dto.newToken, "")
    }

    override fun loadFromNetworkCalls(): List<Call<AuthCredentialsDto>> {

        return listOf(authService.authorize(AuthCredentials.instance.googleToken))
    }

    override fun loadFromDb(): AuthCredentials {
        return authCredentialsDao.getAuthData()
    }

    override fun addToDb(data: AuthCredentials) {
        AuthCredentials.instance.id = data.id
        AuthCredentials.instance.newToken = data.newToken
        authCredentialsDao.insertAuth(AuthCredentials.instance)
    }

}