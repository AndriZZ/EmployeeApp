package com.mentormate.mentornet.di.module

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mentormate.mentornet.MentorNetApp
import com.mentormate.mentornet.utilities.AppExecutors
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(app: MentorNetApp): Context = app.applicationContext

    @Provides
    fun provideGoogleSignInClient(gso: GoogleSignInOptions, context: Context): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }

    @Provides
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }

    @Provides
    fun provideIsOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        return isConnected
    }


}