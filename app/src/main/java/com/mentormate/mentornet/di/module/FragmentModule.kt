package com.mentormate.mentornet.di.module

import com.mentormate.mentornet.adapter.retrofit.NetworkBoundRepository
import com.mentormate.mentornet.ui.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by vasil.mitov@mentormate.com on 11/02/19.
 */

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindMainFragment(): PeopleFragment

    @ContributesAndroidInjector
    abstract fun bindBoardsFragment(): BoardsFragment

    @ContributesAndroidInjector
    abstract fun bindKudosFragment(): KudosFragment

    @ContributesAndroidInjector
    abstract fun bindProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun bindLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun bindProfileTrainingsFragment(): ProfileTrainingsFragment

    @ContributesAndroidInjector
    abstract fun bindProfileKudosFragment(): ProfileKudosFragment

    @ContributesAndroidInjector
    abstract fun bindProfileClientsFragment(): ProfileClientsFragment

    @ContributesAndroidInjector
    abstract fun bindProfileCrystalsFragment(): ProfileCrystalsFragment

    @ContributesAndroidInjector
    abstract fun bindProfileAboutFragment(): ProfileAboutFragment

    @ContributesAndroidInjector
    abstract fun bindKudosItemDialog(): KudosItemDialog

    @ContributesAndroidInjector
    abstract fun bindTrainingsDialog(): TrainingsDialog


}