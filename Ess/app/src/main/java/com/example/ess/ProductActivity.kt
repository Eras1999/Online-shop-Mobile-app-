package com.example.ess

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }

        setupBottomNavigationView()

        // Set up click listeners for all Add to Cart buttons
        findViewById<Button>(R.id.addToCartButton1).setOnClickListener {
            navigateToCartActivity("White Cricket Balls", "LKR 3999.00", R.drawable.b1, "High-quality white cricket balls suitable for professional matches and practice sessions. These balls offer excellent seam retention and durability, ensuring consistent performance. Crafted to meet the standards of professional players, they provide superior swing and spin. Ideal for both indoor and outdoor play, their pristine white color makes them easy to spot. Perfect for cricketers of all levels, from amateurs to pros.")
        }

        findViewById<Button>(R.id.addToCartButton2).setOnClickListener {
            navigateToCartActivity("Kokaboora New Bats", "LKR 39999.00", R.drawable.b2, "Latest model of Kokaboora bats, designed for optimal performance and durability. Featuring a lightweight yet strong construction, these bats allow for powerful strokes with ease. The ergonomic handle ensures a comfortable grip, reducing fatigue during long innings. Enhanced with cutting-edge technology, they deliver exceptional control and precision. Trusted by professional players worldwide, Kokaboora bats are a top choice for serious cricketers.")
        }

        findViewById<Button>(R.id.addToCartButton3).setOnClickListener {
            navigateToCartActivity("DSC New Gloves", "LKR 8999.00", R.drawable.g1, "Comfortable and protective DSC gloves, perfect for enhancing your grip and performance. These gloves are crafted with high-quality materials to provide maximum protection against impacts. Their breathable design ensures your hands stay cool and dry during intense matches. The flexible construction allows for unrestricted movement and superior dexterity. Whether batting or fielding, these gloves will help you perform at your best.")
        }

        findViewById<Button>(R.id.addToCartButton4).setOnClickListener {
            navigateToCartActivity("Mazoori Helmets", "LKR 31999.00", R.drawable.h1, "Sturdy and reliable Mazoori helmets, ensuring maximum safety during play. Designed with a strong outer shell and cushioned interior, these helmets provide excellent impact resistance. The adjustable fit system ensures a secure and comfortable fit for all head sizes. Ventilation features keep you cool and focused, even in the heat of the game. With Mazoori helmets, you can play with confidence knowing your head is well protected.")
        }

        findViewById<Button>(R.id.addToCartButton5).setOnClickListener {
            navigateToCartActivity("Kokaboora Shoes", "LKR 20999.00", R.drawable.s2, "Ergonomic Kokaboora shoes designed for comfort and agility on the field. These shoes feature a lightweight design that allows for quick movements and enhanced speed. The cushioned sole provides excellent shock absorption, reducing stress on your feet and joints. With a durable construction, they withstand the rigors of intense play. Ideal for cricketers of all levels, these shoes will help you perform at your peak.")
        }

        findViewById<Button>(R.id.addToCartButton6).setOnClickListener {
            navigateToCartActivity("White Bottoms", "LKR 2999.00", R.drawable.c1, "Classic white cricket bottoms, ideal for both training and official matches. Made from breathable and durable fabric, these bottoms keep you comfortable throughout the game. The flexible waistband ensures a secure fit, allowing for unrestricted movement. Designed to meet professional standards, they offer a sleek and polished look. Whether you're practicing or competing, these cricket bottoms are a must-have.")
        }

        findViewById<Button>(R.id.addToCartButton7).setOnClickListener {
            navigateToCartActivity("Cricket Balls 3 Pack", "LKR 9999.00", R.drawable.bo1, "Pack of three premium cricket balls, offering great value and performance. These balls are crafted to deliver consistent bounce and flight, making them ideal for all types of play. Their durable construction ensures they withstand repeated use without losing their shape. The bright color makes them easy to spot on the field. Perfect for practice sessions and matches, this pack is a great addition to any cricketer's gear.")
        }

        findViewById<Button>(R.id.addToCartButton8).setOnClickListener {
            navigateToCartActivity("CA Helmets", "LKR 24999.00", R.drawable.h2, "Top-of-the-line CA helmets, providing excellent protection and comfort. These helmets are designed with a robust outer shell and soft inner padding for superior impact resistance. The adjustable fit system ensures a secure and personalized fit for every player. Ventilation channels keep your head cool and comfortable during intense matches. Trusted by professional cricketers, CA helmets are the ultimate choice for safety and performance.")
        }

        findViewById<Button>(R.id.addToCartButton9).setOnClickListener {
            navigateToCartActivity("SG Shoes", "LKR 15999.00", R.drawable.s1, "Durable SG shoes, engineered to enhance your performance on the cricket field. These shoes feature a lightweight design for increased speed and agility. The cushioned insole provides comfort and support, reducing fatigue during long games. With a strong and flexible construction, they offer excellent grip on various surfaces. Ideal for cricketers who demand the best, SG shoes help you stay ahead of the competition.")
        }

        findViewById<Button>(R.id.addToCartButton10).setOnClickListener {
            navigateToCartActivity("Gray Nicolls Bats", "LKR 64999.00", R.drawable.b3, "Professional-grade Gray Nicolls bats, known for their superior craftsmanship and balance. These bats are made from high-quality willow, ensuring powerful and precise strokes. The ergonomic handle provides a comfortable and secure grip, enhancing your control. With a sleek design, they offer both style and performance. Trusted by top cricketers, Gray Nicolls bats are the perfect choice for those serious about their game.")
        }
    }

    private fun navigateToCartActivity(productName: String, productPrice: String, productImageResId: Int, productDescription: String) {
        val intent = Intent(this, CartActivity::class.java)
        intent.putExtra("productName", productName)
        intent.putExtra("productPrice", productPrice)
        intent.putExtra("productImageResId", productImageResId)
        intent.putExtra("productDescription", productDescription)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_products
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
                    // Already on ProductActivity, do nothing
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
