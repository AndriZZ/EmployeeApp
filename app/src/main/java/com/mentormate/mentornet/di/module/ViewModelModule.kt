package com.mentormate.mentornet.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mentormate.mentornet.di.ViewModelFactory
import com.mentormate.mentornet.di.ViewModelKey
import com.mentormate.mentornet.ui.viewmodel.LoginFragmentViewModel
import com.mentormate.mentornet.ui.viewmodel.StoreFragmentViewModel
import com.mentormate.mentornet.ui.viewmodel.BoardsViewModel
import com.mentormate.mentornet.ui.viewmodel.EmployeeListViewModel
import com.mentormate.mentornet.ui.viewmodel.MainActivityViewModel
import com.mentormate.mentornet.ui.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginFragmentViewModel::class)
    abstract fun bindLoginFragmentViewModel(viewModel: LoginFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StoreFragmentViewModel::class)
    abstract fun bindStoreFragmentViewModel(viewModel: StoreFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EmployeeListViewModel::class)
    abstract fun bindEmployeeViewModel(viewModel: EmployeeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindLoginActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BoardsViewModel::class)
    abstract fun bindBoardsViewModel(viewModel: BoardsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KudoListViewModel::class)
    abstract fun bindKudosFragmentViewModel(viewModel: KudoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingsFragmentViewModel::class)
    abstract fun bindTrainingsFragmentViewModel(viewModel: TrainingsFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ClientsListViewModel::class)
    abstract fun bindClientsViewModel(viewModel: ClientsListViewModel): ViewModel



}