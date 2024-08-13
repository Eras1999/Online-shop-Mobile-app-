package com.example.ess

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class PromotionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_promotions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }

        val promotionsList: LinearLayout = findViewById(R.id.promotionsList)

        // Add promotion messages
        val promotions = listOf(
            "Buy one, get one free on all electronics!",
            "20% off all summer clothing!",
            "Free shipping on orders over $50!",
            "Exclusive deals for members only!",
            "Flash sale: Up to 50% off select items!"
        )

        for (promotion in promotions) {
            val textView = TextView(this).apply {
                text = promotion
                textSize = 18f
                setPadding(0, 16, 0, 16)
            }
            promotionsList.addView(textView)
        }

        setupBottomNavigationView()
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_promotions
    }

    private fun setupBottomNavigationView() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_products -> {
                    val intent = Intent(this, ProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_promotions -> {
                    // Already on PromotionsActivity, do nothing
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
