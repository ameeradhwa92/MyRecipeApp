package com.ameeradhwa92.myrecipeapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var txtFullName: TextInputLayout? = null
    private var txtPhoneNo: TextInputLayout? = null
    private var txtEmail: TextInputLayout? = null
    private var txtPassword: TextInputLayout? = null

    private var btnRegisterAccount: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreference = getSharedPreferences("USER_ACCOUNT", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        txtFullName = findViewById<View>(R.id.txtFullName) as TextInputLayout
        txtPhoneNo = findViewById<View>(R.id.txtPhoneNo) as TextInputLayout
        txtEmail = findViewById<View>(R.id.txtEmail) as TextInputLayout
        txtPassword = findViewById<View>(R.id.txtPassword) as TextInputLayout
        btnRegisterAccount = findViewById<View>(R.id.btnRegisterAccount) as Button

        btnRegisterAccount?.setOnClickListener {
            var isValid = true

            if (txtFullName!!.editText?.text.toString().isEmpty()) {
                isValid = false
                txtFullName!!.error = "Please do not leave this blank"
            }
            if (txtPhoneNo!!.editText?.text.toString().isEmpty()) {
                isValid = false
                txtPhoneNo!!.error = "Please do not leave this blank"
            }
            if (txtEmail!!.editText?.text.toString().isEmpty()) {
                isValid = false
                txtEmail!!.error = "Please do not leave this blank"
            }
            if (!txtEmail!!.editText?.text.toString()
                    .let { android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() }
            ) {
                isValid = false
                txtEmail!!.error = "Email format is incorrect"
            }
            if (txtPassword!!.editText?.text.toString().isEmpty()) {
                isValid = false
                txtPassword!!.error = "Please do not leave this blank"
            }

            if (isValid) {
                editor.putString("FullName", txtFullName!!.editText?.text.toString())
                editor.putString("PhoneNo", txtPhoneNo!!.editText?.text.toString())
                editor.putString("Email", txtEmail!!.editText?.text.toString())
                editor.putString("Password", txtPassword!!.editText?.text.toString())
                editor.apply()

                Snackbar.make(
                    it,
                    "Successfully registered as ${
                        sharedPreference.getString(
                            "FullName",
                            "No Name"
                        )
                    }",
                    Snackbar.LENGTH_LONG
                ).show()

                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else
                Snackbar.make(
                    it,
                    "Please fill up all the fields in the correct format",
                    Snackbar.LENGTH_LONG
                ).show()
        }

        txtFullName!!.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty())
                txtFullName!!.error = "Please do not leave this blank"
            else
                txtFullName!!.error = null
        }

        txtPhoneNo!!.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty())
                txtPhoneNo!!.error = "Please do not leave this blank"
            else
                txtPhoneNo!!.error = null
        }

        txtEmail!!.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty())
                txtEmail!!.error = "Please do not leave this blank"
            else {
                if (text.let { android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() })
                    txtEmail!!.error = null
                else
                    txtEmail!!.error = "Email format is incorrect"
            }
        }

        txtPassword!!.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty())
                txtPassword!!.error = "Please do not leave this blank"
            else
                txtPassword!!.error = null
        }
    }
}