package com.example.ess

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the button and set up the click listener
        val viewAllProductsButton: Button = findViewById(R.id.viewAllProductsButton)
        viewAllProductsButton.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
        }

        setupBottomNavigationView()
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_home
    }

    private fun setupBottomNavigationView() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Already on HomeActivity, do nothing
                    true
                }
                R.id.navigation_products -> {
                    val intent = Intent(this, ProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_promotions -> {
                    val intent = Intent(this, PromotionsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
