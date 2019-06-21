package com.mentormate.mentornet.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.data.employee.CompleteEmployeeDao
import com.mentormate.mentornet.data.leaderboard.CompleteLeaderBoard
import com.mentormate.mentornet.data.leaderboard.LeaderBoard
import com.mentormate.mentornet.data.leaderboard.LeaderBoardDao
import com.mentormate.mentornet.utilities.AppExecutors
import javax.inject.Inject


class BoardsViewModel @Inject constructor(
    private val leaderBoardDao: LeaderBoardDao,
    private val completeEmployeeDao: CompleteEmployeeDao

) : ViewModel() {
    val completedLeaderBoards: MediatorLiveData<List<CompleteLeaderBoard>> = MediatorLiveData()
    val completedEmployees: MediatorLiveData<List<CompleteEmployee>> = MediatorLiveData()

    var completeEmployees: List<CompleteEmployee> = listOf()
    var leaderBoards: List<LeaderBoard> = listOf()
    val appExecutors: AppExecutors = AppExecutors()


    var hasEmployees: Boolean = false
    var hasLeaderCategories: Boolean = false

    init {
        completedLeaderBoards.addSource(completedEmployees) {}

        completedLeaderBoards.addSource(leaderBoardDao.getLeaderBoards()) {
            if (!it.isNullOrEmpty()) {
                hasEmployees = true
                provideAllLeaders(hasEmployees, hasLeaderCategories)

            }
        }
        completedEmployees.addSource(completeEmployeeDao.getCompleteEmployeesLiveData()) {
            if (!it.isNullOrEmpty()) {
                hasLeaderCategories = true
                provideAllLeaders(hasEmployees, hasLeaderCategories)
            }
        }
    }


    fun provideAllLeaders(hasEmployees: Boolean, hasLeaderCategories: Boolean) {
        if (hasEmployees == true && hasLeaderCategories == true) {
            appExecutors.diskIO.execute {
                completeEmployees = completeEmployeeDao.getCompleteEmployees()
                leaderBoards = leaderBoardDao.getLeaderBoardsNotLive()


                var finalLeaderBoards = leaderBoards.map {
                    CompleteLeaderBoard(
                        it.categoryId,
                        it.categoryName,
                        it.leaderBoardPositions.leaderBoardPositions.map { position ->
                            completeEmployees.find { employee ->
                                (employee.id == position.employeeId)

                            }
                                ?: CompleteEmployee()
                        })
                }
                completedLeaderBoards.postValue(finalLeaderBoards)

            }
        }
    }
}



