package com.lucasamado.goeatapp.ui

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.viewmodels.LoginViewModel
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    lateinit var username: EditText
    lateinit var password: EditText

    @Inject lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (applicationContext as MyApp).appComponent.inject(this)
    }
}
