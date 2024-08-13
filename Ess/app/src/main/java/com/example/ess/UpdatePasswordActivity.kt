package com.example.ess

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class UpdatePasswordActivity : BaseActivity(), View.OnClickListener {
    private lateinit var newPasswordInput: EditText
    private lateinit var updatePasswordButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_password)

        newPasswordInput = findViewById(R.id.new_password_input)
        updatePasswordButton = findViewById(R.id.update_password_btn)
        auth = FirebaseAuth.getInstance()

        updatePasswordButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.update_password_btn -> {
                    updatePassword()
                }
            }
        }
    }

    private fun updatePassword() {
        val newPassword = newPasswordInput.text.toString().trim()

        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "Please enter a new password", Toast.LENGTH_SHORT).show()
            return
        }

        val actionCode = intent.getStringExtra("actionCode") ?: return

        auth.confirmPasswordReset(actionCode, newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                    finish() // Optionally return to the login screen
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Invalid action code or password", Toast.LENGTH_SHORT).show()
                    } else if (task.exception is FirebaseAuthInvalidUserException) {
                        Toast.makeText(this, "Invalid user", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
