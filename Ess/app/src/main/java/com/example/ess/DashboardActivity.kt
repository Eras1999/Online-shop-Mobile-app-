package com.example.ess

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DashboardActivity : BaseActivity(), View.OnClickListener {
    private lateinit var spinnerDistricts: Spinner
    private lateinit var btnSave: Button
    private lateinit var editTextName: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var realtimeDb: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()
        realtimeDb = FirebaseDatabase.getInstance()

        spinnerDistricts = findViewById(R.id.spinner_districts)
        btnSave = findViewById(R.id.btn_save)
        editTextName = findViewById(R.id.edit_text_name)

        // Populate the spinner with districts of Sri Lanka
        val districts = resources.getStringArray(R.array.sri_lanka_districts)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDistricts.adapter = adapter

        btnSave.setOnClickListener(this)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_save -> {
                    saveDistrict()
                }
            }
        }
    }

    private fun saveDistrict() {
        val selectedDistrict = spinnerDistricts.selectedItem.toString()
        val userName = editTextName.text.toString().trim()
        val user = auth.currentUser

        if (user != null) {
            val userEmail = user.email
            val userData = hashMapOf(
                "email" to userEmail,
                "name" to userName,
                "district" to selectedDistrict
            )

            realtimeDb.reference.child("users").child(user.uid)
                .setValue(userData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Thank you", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@DashboardActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save to Realtime Database", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
