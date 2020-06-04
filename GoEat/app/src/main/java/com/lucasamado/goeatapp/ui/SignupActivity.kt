package com.lucasamado.goeatapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.user.SignupRequest
import com.lucasamado.goeatapp.viewmodels.SignupViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class SignupActivity : AppCompatActivity() {
    @Inject
    lateinit var signupViewModel: SignupViewModel

    lateinit var tiNickname: TextInputEditText
    lateinit var tiEmail: TextInputEditText
    lateinit var tiNombreCompleto: TextInputEditText
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

        tiNickname = findViewById(R.id.textInputUsername)
        tiEmail = findViewById(R.id.textInputEmail)
        tiNombreCompleto = findViewById(R.id.textInputNombreCompleto)
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

        with(tiNickname) {
            onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (text.toString().isEmpty()) {
                    error = "Debe escribir un nombre"
                }
            }
        }

        with(tiNombreCompleto) {
            onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (text.toString().isEmpty()) {
                    error = "Debe escribir su nombre completo"
                }
            }
        }

        with(tiEmail) {
            onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                matcher = pattern.matcher(getText().toString())
                if (text.toString().isEmpty()) {
                    error = "Debe escribir un email"
                } else if (!matcher.find()) {
                    error = "Email no válido"
                }
            }
        }

        with(tiPassword) {
            setOnFocusChangeListener { v, hasFocus ->
                if (text.toString().isEmpty()) {
                    error = "Escriba una contraseña"
                }
            }
        }

        with(tiPassword2) {
            setOnFocusChangeListener { v, hasFocus ->
                if (text.toString().isEmpty()) {
                    error = "Escriba una contraseña"
                }

                if (text.toString() != tiPassword.text.toString()) {
                    error = "Las contraseñas no coinciden"
                }
            }
        }

        buttonSignup.setOnClickListener {
            if (tiNickname.text!!.isEmpty() || tiEmail.text!!.isEmpty() || tiNombreCompleto.text!!.isEmpty() || tiPassword.text!!.isEmpty() || tiPassword2.text!!.isEmpty()) {
                Toast.makeText(this, "No puede haber ningún campo vacío", Toast.LENGTH_LONG).show()
            }else if(tiPassword.text.toString()!=tiPassword2.text.toString()){
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            } else {
                signupViewModel.createUser(
                    SignupRequest(
                        nickName = tiNickname.text.toString(),
                        email = tiEmail.text.toString(),
                        nombreCompleto = tiNombreCompleto.text.toString(),
                        password = tiPassword.text.toString(),
                        password2 = tiPassword2.text.toString()
                    )
                )
                signupViewModel.usuarioCreado.observe(this, Observer { response ->
                    when (response) {
                        is Resource.Success -> {
                            Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_LONG)
                                .show()
                            val login = Intent(this, LoginActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(login)
                            finish()
                        }

                        is Resource.Loading -> { }

                        is Resource.Error -> {
                            Toast.makeText(this, "Error, ${response.message}", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                })
            }
        }

    }
}
