package com.example.fitpeoassignment.ui

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitpeoassignment.App
import com.example.fitpeoassignment.api.models.Data
import com.example.fitpeoassignment.utils.NetworkStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import com.example.fitpeoassignment.utils.NetworkModule as NetworkModule1


class UserDataActivityViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var networkModule: NetworkModule1

    val networkLiveData = MutableLiveData<ArrayList<Data>>()
    val statusLiveData = MutableLiveData<NetworkStatus>()
    val data = MutableLiveData<Data>()

     var data2 : Data = Data()

    fun fetchDataDetail() {

        val networkDataAPI =
            networkModule.provideUserDataApi(networkModule.provideRetrofitInterface())

        val networkDataAPICall =
            networkDataAPI.getData()

        networkDataAPICall.enqueue(object : Callback<ArrayList<Data>> {

            override fun onResponse(
                call: Call<ArrayList<Data>>,
                response: Response<ArrayList<Data>>
            ) {
                if(response!!.body()!=null)
                    networkLiveData.value = response?.body()
                else{
                    statusLiveData.value = NetworkStatus.FAIL
                }
            }

            override fun onFailure(call: Call<ArrayList<Data>>?, t: Throwable?) {
                statusLiveData.value = NetworkStatus.FAIL
            }
        })

        if(!networkModule.isConnected) {
            // off line
            statusLiveData.value = NetworkStatus.INTERNET_CONNECTION
        }

    }

}