package com.example.bottomnavyt

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            goToLoginActivity()
        }, 3000L) // Delay 3 detik
    }

    private fun goToLoginActivity() {
        Intent(this, Login::class.java).also {
            startActivity(it)
            finish()
        }
    }
}
