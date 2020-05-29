package com.lucasamado.goeatapp.ui.home.platos

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.api.load
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.viewmodels.PlatoDetailViewModel
import java.text.DecimalFormat
import javax.inject.Inject

import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.SharedPreferencesManager
import com.lucasamado.goeatapp.ui.home.bares.DetalleBarActivity
import com.lucasamado.goeatapp.ui.home.carrito.CarritoActivity


class DetallePlatoActivity : AppCompatActivity() {
    @Inject
    lateinit var platoDetailViewModel: PlatoDetailViewModel

    lateinit var imagen: ImageView
    lateinit var nombre: TextView
    lateinit var descripcion: TextView
    lateinit var cantidad: TextView
    lateinit var btn_carrito: Button
    lateinit var add: ImageView
    lateinit var less: ImageView
    var precioTotal: String = ""
    var num: Int = 1
    var precioU: Double = 0.00
    lateinit var mensajeCarrito: String
    var df = DecimalFormat("#.00")
    lateinit var idBar: String
    lateinit var tipoPlato: String
    var carrito: Boolean? = false
    var cant: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_plato)

        (applicationContext as MyApp).appComponent.inject(this)

        imagen = findViewById(R.id.imageViewFoto)
        nombre = findViewById(R.id.textViewNombre)
        descripcion = findViewById(R.id.textViewDescripcion)
        cantidad = findViewById(R.id.textViewCantidad)
        add = findViewById(R.id.imageViewAdd)
        less = findViewById(R.id.imageViewLess)
        btn_carrito = findViewById(R.id.buttonPagar)

        var idPlato = intent.extras?.getString(Constantes.PLATO_ID).toString()
        carrito = intent.extras?.getBoolean(Constantes.LUGAR_CARRITO)
        cant = intent.extras?.getInt(Constantes.CANTIDAD)

        if (cant != null && cant!! >= 1) {
            num = cant as Int
        }

        loadPlato(idPlato)

        add.setOnClickListener {
            num++
            actualizarDatos()
        }

        less.setOnClickListener {
            if (carrito == false && num >= 2) num--
            else if (carrito == true && num >= 1) num--
            actualizarDatos()
        }


        btn_carrito.setOnClickListener {
            if (num >= 1) {
                platoDetailViewModel.actualizarCarrito(num, idPlato).observe(this, Observer {
                    if (it != null) {
                        val intent = Intent(this, DetalleBarActivity::class.java).apply {
                            putExtra(Constantes.BAR_ID, idBar)
                            //putExtra(Constantes.TIPO_PLATO, tipoPlato)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        SharedPreferencesManager().setSomeStringValue(Constantes.BAR_PEDIDO, it.plato.bar.id)
                        startActivity(intent)
                        finish()
                    }
                })

            } else {
                platoDetailViewModel.deletePlato(idPlato).observe(this, Observer {
                    if (it != null) {
                        val intent = Intent(this, CarritoActivity::class.java).apply {
                            putExtra(Constantes.BAR_ID, idBar)
                            putExtra(Constantes.TIPO_PLATO, tipoPlato)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                        finish()
                    }
                })
            }
        }
    }

    /**
     * cargar los datos del platoDto
     */
    private fun loadPlato(idPlato: String) {
        platoDetailViewModel.getPlato(idPlato).observe(this, Observer {
            if (it != null) {
                idBar = it.bar.id
                tipoPlato = it.tipo

                imagen.load(it.foto) {
                    crossfade(true)
                    placeholder(R.drawable.ic_food)
                }
                nombre.text = it.nombre
                descripcion.text = it.descripcion
                precioTotal = it.precioU.toString()
                precioU = it.precioU

                actualizarDatos()
            }
        })
    }


    private fun actualizarDatos() {
        if (num < 1 && carrito == true) {
            mensajeCarrito = "BORRAR DEL CARRITO"
            btn_carrito.text = mensajeCarrito
            btn_carrito.setBackgroundColor(Color.parseColor("#cf1e1b"))
        } else {
            precioTotal = (df.format(num * precioU)).toString()
            mensajeCarrito = "AÑADIR $num AL CARRITO"
            btn_carrito.text = mensajeCarrito + "\t\t\t\t\t\t" + precioTotal + "€"
            btn_carrito.setBackgroundColor(Color.parseColor("#FF424242"))
        }
        cantidad.text = num.toString()
    }
}
