package com.example.ess

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class InvoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        val backArrow: ImageView = findViewById(R.id.backArrow)
        val invoiceTitle: TextView = findViewById(R.id.invoiceTitle)
        val orderIdLabel: TextView = findViewById(R.id.orderIdLabel)
        val orderIdValue: TextView = findViewById(R.id.orderIdValue)
        val userNameLabel: TextView = findViewById(R.id.userNameLabel)
        val userNameValue: TextView = findViewById(R.id.userNameValue)
        val addressLabel: TextView = findViewById(R.id.addressLabel)
        val addressValue: TextView = findViewById(R.id.addressValue)
        val productNameLabel: TextView = findViewById(R.id.productNameLabel)
        val productNameValue: TextView = findViewById(R.id.productNameValue)
        val quantityLabel: TextView = findViewById(R.id.quantityLabel)
        val quantityValue: TextView = findViewById(R.id.quantityValue)
        val totalLabel: TextView = findViewById(R.id.totalLabel)
        val totalValue: TextView = findViewById(R.id.totalValue)
        val specialNotice: TextView = findViewById(R.id.specialNotice)
        val thankYou: TextView = findViewById(R.id.thankYou)

        // Retrieve order details from intent
        val orderId = intent.getStringExtra("orderId")
        val userName = intent.getStringExtra("userName")
        val address = intent.getStringExtra("address")
        val productName = intent.getStringExtra("productName")
        val quantity = intent.getIntExtra("quantity", 0)
        val totalPrice = intent.getDoubleExtra("totalPrice", 0.0)

        // Set the retrieved data to the TextViews
        orderIdValue.text = orderId
        userNameValue.text = userName
        addressValue.text = address
        productNameValue.text = productName
        quantityValue.text = quantity.toString()
        totalValue.text = "LKR $totalPrice"

        backArrow.setOnClickListener {
            finish()
        }
    }
}

