package com.lucasamado.goeatapp.ui.home.carrito

import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.SharedPreferencesManager
import com.lucasamado.goeatapp.models.pedido.CreatePedido
import com.lucasamado.goeatapp.viewmodels.CarritoViewModel
import java.text.DecimalFormat
import java.time.LocalTime
import javax.inject.Inject

class CarritoActivity : AppCompatActivity() {
    @Inject
    lateinit var carritoViewModel: CarritoViewModel

    lateinit var btn_pagar: Button
    lateinit var btn_horaRecogida: Button
    lateinit var tituloHoraRecogida: TextView
    lateinit var tituloComentarios: TextView
    lateinit var comentarios: EditText
    lateinit var total: TextView
    var df = DecimalFormat("#.00")
    lateinit var horasList: List<String>
    lateinit var adapterHoras: ArrayAdapter<String>
    var horaSelect: String?=null

    @RequiresApi(Build.VERSION_CODES.O)
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
        total.text = "TOTAL: 0€"

        carritoViewModel.totalCarrito().observe(this, Observer {
            if (it != null && it > 0.0) {
                btn_pagar.visibility = View.VISIBLE
                btn_horaRecogida.visibility = View.VISIBLE
                tituloHoraRecogida.visibility = View.VISIBLE
                tituloComentarios.visibility = View.VISIBLE
                comentarios.visibility = View.VISIBLE
                total.text = "TOTAL: ${df.format(it)}€"

                verHorasDisponibles()
            }
        })

        btn_horaRecogida.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val title = TextView(this)
            title.text = "Elija una hora"
            title.setPadding(20, 30, 20, 30)
            title.textSize = 20F
            title.setTextColor(resources.getColor(R.color.colorAccent))
            title.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
            builder.setCustomTitle(title)

            val lvHoras = ListView(this)
            lvHoras.adapter = adapterHoras
            lvHoras.setOnItemClickListener { parent, view, position, id ->
                horaSelect = horasList.get(position)
            }

            val dialog = builder
                .setView(lvHoras)
                .setPositiveButton("CONFIRMAR") { dialog, which ->
                }
                .setNegativeButton("CANCELAR"){dialog, which ->
                    dialog.dismiss()
                }
                .create()

            dialog.setOnShowListener(DialogInterface.OnShowListener {
                val theButton: Button =
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                theButton.setOnClickListener(View.OnClickListener {
                    if (horaSelect?.isNotEmpty()!!) {
                        tituloHoraRecogida.text = "Hora de recogida: $horaSelect"
                        dialog.dismiss()
                    }
                })
            })
            
            dialog.show()
        }

        btn_pagar.setOnClickListener {
           if(horaSelect!=null){
               var pedidoNuevo = CreatePedido(
                   comentario = comentarios.text.toString(),
                   horaRecogida = horaSelect!!
               )
               carritoViewModel.pagarPedido(pedidoNuevo).observe(this, Observer {
                   if(it!=null){
                       //TODO intent al detalle del pedido
                   }
               })
           }else{
               Toast.makeText(this, "Debe elegir primero una hora", Toast.LENGTH_LONG).show()
           }
        }
    }

    private fun verHorasDisponibles() {
        var idBar = SharedPreferencesManager().getSomeStringValue(Constantes.BAR_PEDIDO)
        if (idBar != null) {
            carritoViewModel.horasRecogida(idBar!!).observe(this, Observer {
                if (it != null) {
                    horasList = it
                    adapterHoras = ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_expandable_list_item_1,
                        horasList
                    )
                }
            })
        }
    }
}
