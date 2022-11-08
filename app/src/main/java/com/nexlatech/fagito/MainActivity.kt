package com.nexlatech.fagito

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nexlatech.fagito.databinding.ActivityMainBinding
import com.nexlatech.fagito.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


//        val fagitoService = RetrofitHelper.getInstance().create(FagitoService::class.java)
//        val repository = FagitoRepository(fagitoService)
//
//        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
//
//        mainViewModel.profileDetailsView.observe(this) {
//            Log.d("println", it.apiVersion.toString())
//        }

        val activity = if(getJsonTokenSharedPreferences() == "NoToken") AuthActivity::class.java else HomeActivity::class.java


        val intent = Intent(this, activity)

        binding.ivLogoBig.animate().setDuration(1500).alpha(1f)
        binding.ivLogoSmall.animate().setDuration(1500).alpha(1f).withEndAction{
            startActivity(intent)

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }
    //Todo :Remove the getJsonTokenSharedPrefrences from each actiity and move it to the mainViewModel
    private fun getJsonTokenSharedPreferences():String? {
        //getting Json token from shared preferences.

        val sharedPreference = this.getSharedPreferences("jsonTokenFile", Context.MODE_PRIVATE)
        val jsonToken = sharedPreference?.getString("jsonTokenKey","NoToken");

        return jsonToken;
    }
}