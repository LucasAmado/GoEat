package com.lucasamado.goeatapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.api.load
import com.google.android.material.textfield.TextInputEditText
import com.lucasamado.goeatapp.MainActivity
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.common.SharedPreferencesManager
import com.lucasamado.goeatapp.models.user.LoginRequest
import com.lucasamado.goeatapp.viewmodels.LoginViewModel
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject lateinit var loginViewModel: LoginViewModel

    lateinit var etEmail: TextInputEditText
    lateinit var etPassword: TextInputEditText
    lateinit var signUp: TextView
    lateinit var button_login: Button
    lateinit var ivLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (applicationContext as MyApp).appComponent.inject(this)

        signUp = findViewById(R.id.textViewSignUp)
        etEmail = findViewById(R.id.textInputUsername)
        etPassword = findViewById(R.id.textInputPassword)
        button_login = findViewById(R.id.buttonLogin)
        ivLogo = findViewById(R.id.imageViewLogo)

        ivLogo.load(
            Uri.parse("file:///android_asset/logo.png")
        )

        signUp.setOnClickListener {v ->
            val intent = Intent(MyApp.instance, SignupActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }

        button_login.setOnClickListener {
            loginViewModel.doLogin(
                LoginRequest(
                    password = etPassword.text.toString(),
                    username = etEmail.text.toString()
                )
            )
            loginViewModel.userLogin.observe(this, Observer {response ->

                when (response) {
                    is Resource.Success -> {
                        var it = response.data!!
                        Toast.makeText(this, "Login correcto", Toast.LENGTH_LONG).show()
                        SharedPreferencesManager().setSomeStringValue(Constantes.TOKEN, it.token)
                        SharedPreferencesManager().setSomeStringValue(Constantes.USER_ROLES, it.user.roles)

                        val main = Intent(this, MainActivity::class.java).apply{
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(main)
                        finish()
                    }

                    is Resource.Loading -> { }

                    is Resource.Error -> {
                        Toast.makeText(this, "Error login: ${response.message}", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}
