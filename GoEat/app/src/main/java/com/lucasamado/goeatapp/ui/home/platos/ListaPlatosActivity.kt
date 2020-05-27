package com.lucasamado.goeatapp.ui.home.platos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.viewmodels.ListaPlatosViewModel
import javax.inject.Inject

class ListaPlatosActivity : AppCompatActivity() {
    @Inject lateinit var listaPlatosViewModel: ListaPlatosViewModel

    lateinit var btn_carrito: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_platos)

        (applicationContext as MyApp).appComponent.inject(this)

        btn_carrito = findViewById(R.id.buttonCarrito)

        btn_carrito.visibility = View.INVISIBLE

        listaPlatosViewModel.tamanyoCarrito().observe(this, Observer {
            if(it != null && it>0){
                btn_carrito.visibility = View.VISIBLE
            }
        })

        btn_carrito.setOnClickListener {

        }
    }
}
