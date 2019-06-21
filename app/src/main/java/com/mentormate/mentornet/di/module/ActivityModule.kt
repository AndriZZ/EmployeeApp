package com.mentormate.mentornet.di.module

import com.mentormate.mentornet.ui.activity.LoginActivity
import com.mentormate.mentornet.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity
}