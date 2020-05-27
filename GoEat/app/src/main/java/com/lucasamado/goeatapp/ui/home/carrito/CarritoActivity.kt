package com.lucasamado.goeatapp.ui.home.carrito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.viewmodels.CarritoViewModel
import javax.inject.Inject

class CarritoActivity : AppCompatActivity() {
    @Inject lateinit var carritoViewModel: CarritoViewModel

    lateinit var btn_pagar: Button
    lateinit var btn_horaRecogida: Button
    lateinit var tvHoraRecogida: TextView
    lateinit var svComentarios: NestedScrollView
    lateinit var comentarios: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        (applicationContext as MyApp).appComponent.inject(this)

        btn_pagar = findViewById(R.id.buttonPagar)
        btn_horaRecogida = findViewById(R.id.buttonHoraRecogida)
        tvHoraRecogida = findViewById(R.id.textView2)
        svComentarios = findViewById(R.id.Scroll)
        comentarios = findViewById(R.id.editTextComentario)

        carritoViewModel.tamanyoCarrito().observe(this, Observer {
            if(it==null || it==0){
                btn_pagar.visibility = View.INVISIBLE
                btn_horaRecogida.visibility = View.INVISIBLE
                tvHoraRecogida.visibility = View.INVISIBLE
                svComentarios.visibility = View.INVISIBLE
                comentarios.visibility = View.INVISIBLE
            }
        })

        btn_horaRecogida.setOnClickListener {
            //TODO hacer fragment con las horas disponibles del bar (quitando las que ya tienen reservas para hoy)
        }

        btn_pagar.setOnClickListener {
            //TODO crear pedido y mandar al main
        }
    }
}
