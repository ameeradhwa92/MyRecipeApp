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

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferences

    private var txtEmail: TextInputLayout? = null
    private var txtPassword: TextInputLayout? = null

    private var btnLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreference = getSharedPreferences("USER_ACCOUNT", Context.MODE_PRIVATE)

        txtEmail = findViewById<View>(R.id.txtEmail) as TextInputLayout
        txtPassword = findViewById<View>(R.id.txtPassword) as TextInputLayout
        btnLogin = findViewById<View>(R.id.btnLogin) as Button

        btnLogin?.setOnClickListener {
            var isValid = true
            var isLoginCorrect = true

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
                if (txtEmail!!.editText?.text.toString() != sharedPreference.getString("Email", ""))
                    isLoginCorrect = false
                if (txtPassword!!.editText?.text.toString() != sharedPreference.getString(
                        "Password",
                        ""
                    )
                )
                    isLoginCorrect = false

                if (isLoginCorrect) {
                    Snackbar.make(it, "Login correct", Snackbar.LENGTH_LONG).show()

                    val intent = Intent(this@LoginActivity, RecipeListActivity::class.java)
                    startActivity(intent)
                    finish()
                } else
                    Snackbar.make(it, "Your login credential is incorrect", Snackbar.LENGTH_LONG)
                        .show()
            } else
                Snackbar.make(
                    it,
                    "Please fill up all the fields in the correct format",
                    Snackbar.LENGTH_LONG
                ).show()
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