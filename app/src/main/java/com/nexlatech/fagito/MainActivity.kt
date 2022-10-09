package com.nexlatech.fagito

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.api.RetrofitHelper
import com.nexlatech.fagito.repository.FagitoRepository
import com.nexlatech.fagito.viewmodel.MainViewModel
import com.nexlatech.fagito.viewmodel.MainViewModelFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fagitoService = RetrofitHelper.getInstance().create(FagitoService::class.java)
        val repository = FagitoRepository(fagitoService)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.profileDetailsView.observe(this) {
            Log.d("println", it.apiVersion.toString())
        }

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }
}