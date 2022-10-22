package com.nexlatech.fagito

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.android.material.snackbar.Snackbar
import com.nexlatech.fagito.databinding.ActivityScanBinding

private const val CAMERA_REQUEST_CODE = 101


class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private lateinit var codeScanner: CodeScanner
    private var qrCodeText = "Scan barcode."



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupPermissions()
        codeScanner()

        Log.d("println", qrCodeText)


    }

    private fun codeScanner(){
        codeScanner = CodeScanner(this,binding.scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    binding.tvScanCodeText.text = it.text
                    qrCodeText = it.text
                    Log.d("println", it.text)

                    callingApi(qrCodeText)
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("main", "Camera initialization error: ${it.message}")
                }
            }

            binding.scannerView.setOnClickListener {
                codeScanner.startPreview()
            }



        }
    }
    fun callingApi(upcCode: String){
        Log.d("println", "Barcode: $upcCode")
    }

    private fun setupPermissions(){
        val permission = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }


    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_DENIED){
                    //make a snackbar to show the user that they need to grant the camera permission
                    Snackbar.make(binding.root, "you need to give camera permission to use this app.",
                        Snackbar.LENGTH_LONG).setAction("OK") {
                        makeRequest()
                    }.show()
                } else {
                    Snackbar.make(binding.root, "Camera permission Granted.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}