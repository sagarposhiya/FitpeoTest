package com.example.fitpeoassignment.ui

import android.content.Intent
import android.net.wifi.p2p.WifiP2pDevice.CONNECTED
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.fitpeoassignment.R
import com.example.fitpeoassignment.adapters.DataAdapter
import com.example.fitpeoassignment.api.models.Data
import com.example.fitpeoassignment.databinding.ActivityMainBinding
import com.example.fitpeoassignment.utils.NetworkStatus
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var networkDataActivityViewModel: UserDataActivityViewModel

    @Inject
    lateinit var networkDataAdapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareView()
        callApiAndUpdateUI()
    }

    private fun prepareView() {
        binding.myAdapter = networkDataAdapter
        networkDataAdapter.onItemClick = { data ->
           // networkDataActivityViewModel.data.postValue(data)
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("data", data)
            startActivity(intent)

        }

    }

    private fun callApiAndUpdateUI() {
        binding.networkDataProgressBar.visibility = View.VISIBLE
        networkDataActivityViewModel.networkLiveData.observe(this,
            Observer { baseDataStore ->
                binding.networkDataProgressBar.visibility = View.GONE
                binding.recycleViewNetworkData.visibility = View.VISIBLE

                if (baseDataStore != null) {
                    networkDataAdapter.addItemList(baseDataStore)
                }
                else{
                    networkDataActivityViewModel.statusLiveData.value = NetworkStatus.FAIL
                }
            })

        networkDataActivityViewModel.statusLiveData.observe(this,
            Observer<NetworkStatus> { status ->
                binding.networkDataProgressBar.visibility = View.GONE
                onNetWorkStateChanged(status)
            })
        networkDataActivityViewModel.fetchDataDetail()
    }


    private fun onNetWorkStateChanged(state: NetworkStatus) = when (state) {
        NetworkStatus.INTERNET_CONNECTION -> showSnackBar(getString(R.string.msg_no_internet_network))
        NetworkStatus.SERVER_ERROR -> showSnackBar(getString(R.string.msg_server_error))
        NetworkStatus.FAIL -> showSnackBar(getString(R.string.msg_something_went_wrong))
        NetworkStatus.NO_RECORDS -> showSnackBar(getString(R.string.msg_no_records))
        NetworkStatus.SUCCESS -> showSnackBar(getString(R.string.msg_no_records))

        else -> showSnackBar(getString(R.string.msg_unknown))
    }
    private fun showSnackBar(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
            .show()
    }

}