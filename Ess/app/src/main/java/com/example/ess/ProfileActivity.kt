package com.example.ess

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backArrow: ImageView = findViewById(R.id.backArrow)
        val fullNameTextView: TextView = findViewById(R.id.fullName)
        val emailTextView: TextView = findViewById(R.id.email)
        val cityTextView: TextView = findViewById(R.id.city)
        val logoutButton: Button = findViewById(R.id.logoutButton)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Set the selected item to Profile
        bottomNavigationView.selectedItemId = R.id.navigation_profile

        backArrow.setOnClickListener {
            finish()
        }

        // Fetch user details from Firebase
        val currentUser = auth.currentUser
        currentUser?.let {
            emailTextView.text = it.email

            val userId = it.uid
            database.child("users").child(userId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                    val userData = snapshot.getValue(UserData::class.java)
                    if (userData != null) {
                        fullNameTextView.text = userData.name
                        cityTextView.text = userData.district
                    }
                }

                override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                    // Handle possible errors
                }
            })
        }

        // Handle logout
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Handle navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navigation_products -> {
                    val intent = Intent(this@ProfileActivity, ProductActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navigation_promotions -> {
                    val intent = Intent(this@ProfileActivity, PromotionsActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navigation_profile -> {
                    // This is already on the profile page
                    true
                }
                else -> false
            }
        }
    }

    // Data class to match Firebase Realtime Database structure
    data class UserData(
        var name: String = "",
        var district: String = ""
    )
}
