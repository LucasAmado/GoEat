package com.lucasamado.goeatapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.user.SignupRequest
import com.lucasamado.goeatapp.viewmodels.SignupViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class SignupActivity : AppCompatActivity() {
    @Inject
    lateinit var signupViewModel: SignupViewModel

    lateinit var tiUsername: TextInputEditText
    lateinit var tiEmail: TextInputEditText
    lateinit var tiPassword: TextInputEditText
    lateinit var tiPassword2: TextInputEditText
    lateinit var buttonSignup: Button
    lateinit var tvLogin: TextView
    lateinit var matcher: Matcher
    val pattern = Pattern.compile(
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        (applicationContext as MyApp).appComponent.inject(this)

        tiUsername = findViewById(R.id.textInputUsername)
        tiEmail = findViewById(R.id.textInputEmail)
        tiPassword = findViewById(R.id.textInputPassword)
        tiPassword2 = findViewById(R.id.textInputPassword2)
        buttonSignup = findViewById(R.id.buttonSignUp)
        tvLogin = findViewById(R.id.textViewLogin)

        tvLogin.setOnClickListener {
            val login: Intent = Intent(MyApp.instance, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(login)
            finish()
        }

        with(tiUsername) {
            setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
                if (text.toString().isEmpty()) {
                    setError("Debe escribir un nombre")
                }
            })
        }

        with(tiEmail) {
            setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
                matcher = pattern.matcher(getText().toString())
                if (text.toString().isEmpty()) {
                    setError("Debe escribir un email")
                } else if (!matcher.find()) {
                    setError("Email no válido")
                }
            })
        }

        with(tiPassword) {
            setOnFocusChangeListener { v, hasFocus ->
                if (text.toString().isEmpty()) {
                    setError("Escriba una contraseña")
                }
            }
        }

        with(tiPassword2) {
            setOnFocusChangeListener { v, hasFocus ->
                if (text.toString().isEmpty()) {
                    setError("Escriba una contraseña")
                }

                if (text.toString() != tiPassword.text.toString()) {
                    setError("Las contraseñas no coinciden")
                }
            }
        }

        buttonSignup.setOnClickListener {
            signupViewModel.createUser(
                SignupRequest(
                    username = tiUsername.text.toString(),
                    email = tiEmail.text.toString(),
                    password = tiPassword.text.toString(),
                    password2 = tiPassword2.text.toString()
                )
            ).observe(this, Observer {
                if(it != null){
                    val login: Intent = Intent(MyApp.instance, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(login)
                    finish()
                }
            })
        }

    }
}
