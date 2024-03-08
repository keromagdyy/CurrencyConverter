package com.kerolosatya.currencyconverter.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kerolosatya.currencyconverter.databinding.ActivitySplashBinding
import com.kerolosatya.currencyconverter.ui.base.BaseActivity
import com.kerolosatya.currencyconverter.ui.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.animationView.setAnimation("lottie_splash_logo.json")
        lifecycleScope.launch {
            delay(3000)

            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

}