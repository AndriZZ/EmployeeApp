package com.mentormate.mentornet.adapter.retrofit

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mentormate.mentornet.utilities.NETWORK_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by vasil.mitov@mentormate.com on 11/03/19.
 */

abstract class NetworkBoundRepository<Data, DataDto> {

    private val observableData: MutableLiveData<Resource<Data>> = MutableLiveData()

    abstract fun loadFromDb(): Data?

    abstract fun addToDb(data: Data)

    abstract fun loadFromNetworkCalls(): List<Call<DataDto>>

    abstract fun adapt(dto: DataDto): Data

    fun fetchData() {
        val loadingData: Data? = observableData.value?.data
        observableData.value = Resource.loading(loadingData)
        loadFromDbAsync()
        loadDataFromWeb()
    }

    fun getObservable(): MutableLiveData<Resource<Data>> {
        return observableData
    }

    private fun loadDataFromWeb() {

        loadFromNetworkCalls().forEach { call ->
            call.enqueue(LoadCallback(this))

        }

    }

    private fun loadFromDbAsync() {
        AsyncLoadFromDbTask(this).execute()
    }


    private fun addToDBAsync(data: Data) {
        AsyncAddToDbTask(this).execute(data)
    }

    private fun setDataListObservableData(dataList: Data, message: String?) {
        var loadingStatus = Resource.Status.LOADING
        if (observableData.value != null) {
            loadingStatus = observableData.value!!.status
        }
        when (loadingStatus) {
            Resource.Status.LOADING -> observableData.postValue(Resource.loading(dataList))
            Resource.Status.ERROR -> observableData.postValue(Resource.error(message, dataList))
            Resource.Status.SUCCESS -> observableData.postValue(Resource.success(dataList))

        }
    }

    private fun setDataListObservableStatus(status: Resource.Status, message: String?) {
        val loadingData: Data? = observableData.value?.data
        when (status) {
            Resource.Status.ERROR -> {
                observableData.postValue(Resource.error(message, loadingData))
            }
            Resource.Status.LOADING -> {
                observableData.postValue(Resource.loading(loadingData))
            }
            Resource.Status.SUCCESS -> {
                if (loadingData != null) {
                    observableData.postValue(Resource.success(loadingData))
                }
            }
        }
    }

    private class AsyncAddToDbTask<Data, DataDto>(private val networkBoundRepository: NetworkBoundRepository<Data, DataDto>) :
        AsyncTask<Data, Void?, Void?>() {
        override fun doInBackground(vararg params: Data): Void? {
            networkBoundRepository.addToDb(params[0])
            return null
        }

        override fun onPostExecute(result: Void?) {
            networkBoundRepository.loadFromDbAsync()
        }
    }

    private class AsyncLoadFromDbTask<Data, DataDto>(private val networkBoundRepository: NetworkBoundRepository<Data, DataDto>) :
        AsyncTask<Void, Void, Data>() {
        override fun doInBackground(vararg params: Void?): Data? {
            return networkBoundRepository.loadFromDb()
        }

        override fun onPostExecute(result: Data?) {
            if (result != null) {
                networkBoundRepository.setDataListObservableData(result, null)
            }
        }
    }

    private class LoadCallback<Data, DataDto>(
        private val networkBoundRepository: NetworkBoundRepository<Data, DataDto>
    ) : Callback<DataDto> {

        override fun onResponse(call: Call<DataDto>, response: Response<DataDto>) {
            if (response.isSuccessful) {
                if (response.body() != null) {
                    networkBoundRepository.setDataListObservableStatus(Resource.Status.SUCCESS, null)
                    networkBoundRepository.addToDBAsync(networkBoundRepository.adapt(response.body()!!))
                } else {
                    networkBoundRepository.setDataListObservableStatus(Resource.Status.ERROR, "Empty body")
                }

            } else {
                // error case
                networkBoundRepository.setDataListObservableStatus(Resource.Status.ERROR, response.code().toString())
                when (response.code()) {
                    404 -> Log.i(NETWORK_TAG, "not found")
                    401 -> Log.i(NETWORK_TAG, "unauthorized")
                    500 -> Log.i(NETWORK_TAG, "not logged in or server broken")
                    else -> Log.i(NETWORK_TAG, "unknown error")
                }
            }
        }

        override fun onFailure(call: Call<DataDto>, t: Throwable) {
            networkBoundRepository.setDataListObservableStatus(Resource.Status.ERROR, t.message)

        }
    }
}