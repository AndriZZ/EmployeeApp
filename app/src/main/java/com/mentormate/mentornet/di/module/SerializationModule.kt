package com.mentormate.mentornet.di.module

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mentormate.mentornet.ui.fragment.*
import com.mentormate.mentornet.utilities.OAUTH_CLIENT_ID
import dagger.Module
import dagger.Provides

/**
 * Created by vasil.mitov@mentormate.com on 06/03/19.
 */

@Module
class SerializationModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .serializeNulls()
            .create()
    }

    @Provides
    fun provideGso(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(OAUTH_CLIENT_ID)
            .requestServerAuthCode(OAUTH_CLIENT_ID)
            .requestProfile()
            .requestEmail()
            .build()
    }


    @Provides
    fun provideprofileClientsFragment(): ProfileClientsFragment {
        return ProfileClientsFragment()
    }
    @Provides
    fun provideprofileAboutFragment(): ProfileAboutFragment {
        return ProfileAboutFragment()
    }
    @Provides
    fun provideprofileTrainingsFragment(): ProfileTrainingsFragment {
        return ProfileTrainingsFragment()
    }
    @Provides
    fun provideprofileKudosFragment(): ProfileKudosFragment {
        return ProfileKudosFragment()
    }
    @Provides
    fun provideprofileCrystalsFragment(): ProfileCrystalsFragment {
        return ProfileCrystalsFragment()
    }

    @Provides
    fun provideprofileKudosItemDialog(): KudosItemDialog {
        return KudosItemDialog()
    }

}