package com.example.ess

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
    }else{
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )


    }
        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        finish()
    },
        2500
    )

       // val typeface: Typeface = Typeface.createFromAsset(assets,"East Rusty Demo.ttf")
      //  val tvAppName: TextView = findViewById(R.id.tv_app_name)
     //   tvAppName.typeface = typeface
}
    }
