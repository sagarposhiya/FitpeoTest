package com.example.fitpeoassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.fitpeoassignment.BR
import com.example.fitpeoassignment.R
import com.example.fitpeoassignment.api.models.Data
import com.example.fitpeoassignment.databinding.ActivityDetailsBinding
import com.example.fitpeoassignment.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    var data : Data = Data()

    @Inject
    lateinit var networkDataActivityViewModel: UserDataActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = (intent.getSerializableExtra("data") as? Data)!!
        binding.setVariable(BR.model, data)
        binding.executePendingBindings()
        Log.e("DATA", "PASSED DATA :- " + data.title)
    }
}