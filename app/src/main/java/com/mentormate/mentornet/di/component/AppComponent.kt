package com.mentormate.mentornet.di.component

import com.mentormate.mentornet.MentorNetApp
import com.mentormate.mentornet.di.module.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component
    (
    modules = [
        (AndroidSupportInjectionModule::class),
        (AppModule::class),
        (SerializationModule::class),
        (NetworkModule::class),
        (RoomModule::class),
        (AdapterModule::class),
        (ActivityModule::class),
        (FragmentModule::class),
        (ViewModelModule::class),
        (AppModule::class)
    ]
)
interface AppComponent : AndroidInjector<MentorNetApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MentorNetApp>()
}
