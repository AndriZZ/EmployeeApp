package com.mentormate.mentornet.adapter.network


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.mentormate.mentornet.R
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import com.mentormate.mentornet.ui.activity.LoginActivity
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

/**
 * Created by vasil.mitov@mentormate.com on 08/03/19.
 */

class TokenInterceptor @Inject constructor(
    private val authDao: AuthCredentialsDao,
    private val googleSignInClient: GoogleSignInClient,
    private val context: Context
) : Interceptor,Authenticator {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val accessToken = authDao.getAuthData()?.newToken

        val request = originRequest.newBuilder()
        if (accessToken != null && accessToken.isNotEmpty()) {
            request.addHeader("Authorization", "Bearer $accessToken")
        }
        request.addHeader("Accept", "application/json")
        request.addHeader("Content-Type", "application/json")

        return chain.proceed(request.build())
    }

    @Throws(IOException::class)
    override fun authenticate (route: Route?, response: Response?): Request? {
        var requestAvailable: Request? = null
        try {
            requestAvailable = response?.request()?.newBuilder()
                ?.addHeader("AUTH_TOKEN", "UUID.randomUUID().toString()")
                ?.build()
            googleSignInClient.signOut()
            authDao.deleteAuth()
            val intent = Intent(context, LoginActivity::class.java)
            val bundle = Bundle()
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ContextCompat.startActivity(context, intent, bundle)
            return requestAvailable
        } catch (ex: Exception) { }
        return requestAvailable
    }

}