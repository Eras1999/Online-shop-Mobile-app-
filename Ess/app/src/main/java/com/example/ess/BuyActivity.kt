package com.example.ess

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BuyActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buy)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance().reference

        val backArrow: ImageView = findViewById(R.id.backArrow)
        val productNameTextView: TextView = findViewById(R.id.productName)
        val productPriceTextView: TextView = findViewById(R.id.productPrice)
        val quantityEditText: EditText = findViewById(R.id.quantityEditText)
        val totalPriceTextView: TextView = findViewById(R.id.totalPrice)
        val userNameEditText: EditText = findViewById(R.id.userNameEditText)
        val addressEditText: EditText = findViewById(R.id.addressEditText)
        val telephoneEditText: EditText = findViewById(R.id.telephoneEditText)
        val orderNowButton: Button = findViewById(R.id.orderNowButton)

        // Retrieve product details from intent
        val productName = intent.getStringExtra("productName")
        val productPriceString = intent.getStringExtra("productPrice") ?: "0.00"
        val productPrice = productPriceString.removePrefix("LKR ").replace(",", "").toDoubleOrNull() ?: 0.0

        productNameTextView.text = productName
        productPriceTextView.text = "LKR $productPrice"

        // Initialize quantity and update total price
        var quantity = quantityEditText.text.toString().toIntOrNull() ?: 1
        val deliveryFee = 500.0
        val updateTotalPrice = {
            val totalPrice = (productPrice * quantity) + deliveryFee
            totalPriceTextView.text = "Total Price: LKR $totalPrice"
        }
        updateTotalPrice()

        quantityEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                quantity = quantityEditText.text.toString().toIntOrNull() ?: 1
                updateTotalPrice()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        orderNowButton.setOnClickListener {
            val name = userNameEditText.text.toString()
            val address = addressEditText.text.toString()
            val telephone = telephoneEditText.text.toString()

            if (name.isNotEmpty() && address.isNotEmpty() && telephone.matches("\\d{10}".toRegex())) {
                // Show confirmation dialog
                showConfirmationDialog(name, address, telephone, productName, productPrice, quantity)
            } else {
                // Show error message
                Toast.makeText(this, "Please enter valid details.", Toast.LENGTH_SHORT).show()
            }
        }

        backArrow.setOnClickListener {
            finish()
        }
    }

    private fun showConfirmationDialog(
        name: String,
        address: String,
        telephone: String,
        productName: String?,
        productPrice: Double,
        quantity: Int
    ) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Confirm Order")
        alertDialogBuilder.setMessage("Are you sure you want to order this product?")

        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            // Save order details to Firebase
            val deliveryFee = 500.0
            val totalPrice = (productPrice * quantity) + deliveryFee

            val orderId = database.child("orders").push().key ?: ""
            val orderDetails = mapOf(
                "orderId" to orderId,
                "productName" to productName,
                "productPrice" to productPrice,
                "quantity" to quantity,
                "totalPrice" to totalPrice,
                "userName" to name,
                "address" to address,
                "telephone" to telephone
            )

            database.child("orders").child(orderId).setValue(orderDetails)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Show success notification and go to InvoiceActivity
                        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, InvoiceActivity::class.java)
                        intent.putExtra("orderId", orderId)
                        intent.putExtra("productName", productName)
                        intent.putExtra("productPrice", productPrice)
                        intent.putExtra("quantity", quantity)
                        intent.putExtra("totalPrice", totalPrice)
                        intent.putExtra("userName", name)
                        intent.putExtra("address", address)
                        intent.putExtra("telephone", telephone)
                        startActivity(intent)
                    } else {
                        // Show error message
                        Toast.makeText(this, "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
