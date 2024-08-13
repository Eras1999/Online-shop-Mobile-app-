package com.example.ess

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Your initialization code here
    }

    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(this@BaseActivity, R.color.colorSnackBarError)
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(this@BaseActivity, R.color.colorSnackBarSuccess)
            )
        }

        snackBar.show()
    }

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(this)

        mProgressDialog.setContentView(R.layout.dialog_progress)

        // Ensure the TextView exists and is found correctly
      //  val tvProgressText: TextView = mProgressDialog.findViewById(R.id.tv_progress_text)
      //  tvProgressText.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        if (::mProgressDialog.isInitialized) {
            mProgressDialog.dismiss()
        }
    }
}
