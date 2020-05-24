package com.lucasamado.goeatapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.lucasamado.goeatapp.MainActivity
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.SharedPreferencesManager
import com.lucasamado.goeatapp.models.user.LoginRequest
import com.lucasamado.goeatapp.viewmodels.LoginViewModel
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject lateinit var loginViewModel: LoginViewModel

    lateinit var etUsername: TextInputEditText
    lateinit var etPassword: TextInputEditText
    lateinit var signUp: TextView
    lateinit var button_login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (applicationContext as MyApp).appComponent.inject(this)

        signUp = findViewById(R.id.textViewSignUp)
        etUsername = findViewById(R.id.textInputUsername)
        etPassword = findViewById(R.id.textInputPassword)
        button_login = findViewById(R.id.buttonLogin)

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
                    username = etUsername.text.toString()
                )
            ).observe(this, Observer {
                if(it != null){
                    SharedPreferencesManager().setSomeStringValue(Constantes.TOKEN, it.token)
                    SharedPreferencesManager().setSomeStringValue(Constantes.USER_ROLES, it.user.roles)

                    val intent = Intent(MyApp.instance, MainActivity::class.java).apply{
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                    finish()
                }
            })
        }
    }
}
