package com.example.ess

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private lateinit var firstname: EditText
    private lateinit var lastname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirm_password: EditText
    private lateinit var terms_conditions: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Initialize views
        firstname = findViewById(R.id.firstname)
        lastname = findViewById(R.id.lastname)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confirm_password = findViewById(R.id.confirm_password)
        terms_conditions = findViewById(R.id.terms_conditions)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setupActionBar()

        // Find the TextView for login button
        val logBtnNow: TextView = findViewById(R.id.log_btn_now)
        logBtnNow.setOnClickListener {
            // Start LoginActivity when "Login" is clicked
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.register_button).setOnClickListener {
            registerUser()
        }
    }

    private fun setupActionBar() {
        // Initialize the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_register_activity)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.go_back)
            actionBar.title = ""
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(firstname.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(lastname.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            password.text.toString().trim { it <= ' ' } != confirm_password.text.toString().trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }

            !terms_conditions.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_conditions), true)
                false
            }

            else -> {
               // showErrorSnackBar("Registration successful!", false)
                true
            }
        }
    }

    private fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSnackBarError))
        } else {
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSnackBarSuccess))
        }

        snackBar.show()
    }



    private fun registerUser() {

        // Check with validate function if the entries are valid or not
        if (validateRegisterDetails()) {
            showProgressDialog()

            val email: String = email.text.toString().trim { it <= ' ' }
            val password: String = password.text.toString().trim { it <= ' ' }

            // Create an instance and register a user with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                    hideProgressDialog()
                    // If the registration is successfully done
                    if (task.isSuccessful) {
                        // Firebase registered user
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        showErrorSnackBar(
                            "You are registered successfully. Your user id is ${firebaseUser.uid}",
                            false
                        )
                        FirebaseAuth.getInstance().signOut()
                        finish()

                        // You can navigate the user to the login screen or home screen here
                    } else {
                        // If the registering is not successful, then show error message
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                })
        }
    }


    //loading animation
    private lateinit var progressDialog: Dialog

    private fun showProgressDialog() {
        progressDialog = Dialog(this)

        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        progressDialog.show()
    }

    private fun hideProgressDialog() {
        if (::progressDialog.isInitialized && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

}




