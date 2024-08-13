package com.example.ess

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : BaseActivity(), View.OnClickListener {
    private lateinit var emailInput: EditText
    private lateinit var resetPasswordButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        emailInput = findViewById(R.id.email_input)
        resetPasswordButton = findViewById(R.id.reset_password_btn)
        auth = FirebaseAuth.getInstance()

        resetPasswordButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.reset_password_btn -> {
                    sendPasswordResetEmail()
                }
            }
        }
    }

    private fun sendPasswordResetEmail() {
        val email = emailInput.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    finish() // Return to login or a relevant screen
                } else {
                    Toast.makeText(this, "Failed to send reset email", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
