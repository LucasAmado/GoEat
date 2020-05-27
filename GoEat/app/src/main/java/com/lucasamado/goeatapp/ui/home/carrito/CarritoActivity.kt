package com.lucasamado.goeatapp.ui.home.carrito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.viewmodels.CarritoViewModel
import java.text.DecimalFormat
import javax.inject.Inject

class CarritoActivity : AppCompatActivity() {
    @Inject lateinit var carritoViewModel: CarritoViewModel

    lateinit var btn_pagar: Button
    lateinit var btn_horaRecogida: Button
    lateinit var tituloHoraRecogida: TextView
    lateinit var tituloComentarios: TextView
    lateinit var comentarios: EditText
    lateinit var total: TextView
    var df = DecimalFormat("#.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        (applicationContext as MyApp).appComponent.inject(this)

        btn_pagar = findViewById(R.id.buttonPagar)
        btn_horaRecogida = findViewById(R.id.buttonHoraRecogida)
        tituloHoraRecogida = findViewById(R.id.textView2)
        tituloComentarios = findViewById(R.id.textView3)
        comentarios = findViewById(R.id.editTextComentario)
        total = findViewById(R.id.textViewTotal)

        btn_pagar.visibility = View.INVISIBLE
        btn_horaRecogida.visibility = View.INVISIBLE
        tituloHoraRecogida.visibility = View.INVISIBLE
        tituloComentarios.visibility = View.INVISIBLE
        comentarios.visibility = View.INVISIBLE
        total.text="TOTAL: 0€"

        carritoViewModel.totalCarrito().observe(this, Observer {
            if(it!=null && it>0.0){
                btn_pagar.visibility = View.VISIBLE
                btn_horaRecogida.visibility = View.VISIBLE
                tituloHoraRecogida.visibility = View.VISIBLE
                tituloComentarios.visibility = View.VISIBLE
                comentarios.visibility = View.VISIBLE
                total.text="TOTAL: ${df.format(it)}€"
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
