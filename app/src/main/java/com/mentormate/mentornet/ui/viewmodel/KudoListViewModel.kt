package com.mentormate.mentornet.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mentormate.mentornet.data.kudos.CompleteKudo
import com.mentormate.mentornet.data.kudos.CompleteKudoDao
import javax.inject.Inject


class KudoListViewModel
@Inject constructor(
    private val completeKudoDao: CompleteKudoDao
) : ViewModel() {
    fun getCompleteKudos(): LiveData<List<CompleteKudo>> = completeKudoDao.getCompleteKudos()
    fun getCompleteKudoForId(id:Int): LiveData<List<CompleteKudo>> = completeKudoDao.getKudosForId(id)
    fun getCompleteKudoFromId(id:Int): LiveData<List<CompleteKudo>> = completeKudoDao.getKudosFromId(id)
}