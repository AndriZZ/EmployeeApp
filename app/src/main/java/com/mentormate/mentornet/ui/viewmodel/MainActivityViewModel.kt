package com.mentormate.mentornet.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mentormate.mentornet.adapter.retrofit.employee.EmployeeNetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.kudos.KudoNetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.leaderboard.RankListNetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.store.StoreNetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.trainings.CoursesNetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.trainings.TrainingNetworkBoundRepository
import com.mentormate.mentornet.data.employee.GeneralInformationNetworkBoundRepository
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
    private val employeeNetworkBoundRepository: EmployeeNetworkBoundRepository,
    private val storeNetworkBoundRepository: StoreNetworkBoundRepository,
    private val generalInformationNetworkBoundRepository: GeneralInformationNetworkBoundRepository,
    private val rankListRepository: RankListNetworkBoundRepository,
    private val kudosRepository: KudoNetworkBoundRepository,
    private val trainingNetworkBoundRepository: TrainingNetworkBoundRepository,
    private val coursesNetworkBoundRepository: CoursesNetworkBoundRepository,
    private val context: Context

) : ViewModel() {

   // private var isConnected: MutableLiveData<Boolean> = MutableLiveData()
    fun preloadData() {
        rankListRepository.fetchData()
        storeNetworkBoundRepository.fetchData()
        employeeNetworkBoundRepository.fetchData()
        generalInformationNetworkBoundRepository.fetchData()
        kudosRepository.fetchData()
        trainingNetworkBoundRepository.fetchData()
        coursesNetworkBoundRepository.fetchData()

    }
    fun getConnectionStatus():Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true

    }


}