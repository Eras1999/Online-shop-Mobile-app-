package com.example.ess

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }

        val productName = intent.getStringExtra("productName")
        val productPrice = intent.getStringExtra("productPrice")
        val productImageResId = intent.getIntExtra("productImageResId", -1)
        val productDescription = intent.getStringExtra("productDescription")

        val productImageView: ImageView = findViewById(R.id.cartProductImage)
        val productNameTextView: TextView = findViewById(R.id.cartProductName)
        val productPriceTextView: TextView = findViewById(R.id.cartProductPrice)
        val productDescriptionTextView: TextView = findViewById(R.id.cartProductDescription)
        val buyNowButton: Button = findViewById(R.id.buyNowButton)

        productImageView.setImageResource(productImageResId)
        productNameTextView.text = productName
        productPriceTextView.text = productPrice
        productDescriptionTextView.text = productDescription

        buyNowButton.setOnClickListener {
            val intent = Intent(this, BuyActivity::class.java).apply {
                putExtra("productName", productName)
                putExtra("productPrice", productPrice)
            }
            startActivity(intent)
        }
    }
}
