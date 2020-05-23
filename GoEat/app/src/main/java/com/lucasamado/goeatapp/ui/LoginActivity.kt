package com.lucasamado.goeatapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.viewmodels.LoginViewModel
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject lateinit var loginViewModel: LoginViewModel

    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var signUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (applicationContext as MyApp).appComponent.inject(this)

        signUp = findViewById(R.id.textViewSignUp)

        signUp.setOnClickListener {v ->
            val intent = Intent(MyApp.instance, SignupActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}
