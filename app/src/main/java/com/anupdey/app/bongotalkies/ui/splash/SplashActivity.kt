package com.anupdey.app.bongotalkies.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.anupdey.app.bongotalkies.databinding.ActivitySplashBinding
import com.anupdey.app.bongotalkies.ui.home.HomeActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var handler = Handler(Looper.getMainLooper())
    private var callback: Runnable? = null
    private var timeOut = 1000L
    private var timeoutFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        timeoutSplashScreen()
    }

    override fun onResume() {
        super.onResume()
        if (timeoutFlag) goToHomeScreen()
    }

    override fun onPause() {
        super.onPause()
        callback?.let {
            handler.removeCallbacks(it)
            timeoutFlag = true
        }
    }

    private fun timeoutSplashScreen() {
        callback = Runnable {
            timeoutFlag = true
            goToHomeScreen()
        }
        handler.postDelayed(callback!!, timeOut)
    }

    private fun goToHomeScreen() {
        Intent(this, HomeActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

}