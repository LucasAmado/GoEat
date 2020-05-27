package com.lucasamado.goeatapp.ui.home.platos

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

import androidx.appcompat.app.AlertDialog;
import com.lucasamado.goeatapp.R


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
        btn_carrito = findViewById(R.id.buttonCarrito)

        var idPlato = intent.extras?.getString(Constantes.PLATO_ID).toString()

        loadPlato(idPlato)

        add.setOnClickListener {
            num++
            actualizarDatos()
        }

        less.setOnClickListener {
            if (num >= 2) num--
            actualizarDatos()
        }


        btn_carrito.setOnClickListener {
            if (num >= 1) {
                platoDetailViewModel.actualizarCarrito(num, idPlato).observe(this, Observer {
                    if (it != null) {
                        val intent = Intent(this, ListaPlatosActivity::class.java).apply {
                            putExtra(Constantes.BAR_ID, idBar)
                            putExtra(Constantes.TIPO_PLATO, tipoPlato)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                        finish()
                    }
                })

            } else {
                Log.e("bar", "borrar plato")
                platoDetailViewModel.deletePlato(idPlato).observe(this, Observer {
                    //TODO crear activity del carrito y descomentar
                    /*val intent = Intent(this, CarritoActivity::class.java).apply {
                        putExtra(Constantes.BAR_ID, idBar)
                        putExtra(Constantes.TIPO_PLATO, tipoPlato)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }*/
                    startActivity(intent)
                    finish()
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
        cantidad.text = num.toString()
        precioTotal = (df.format(num * precioU)).toString()
        mensajeCarrito = "AÑADIR $num AL CARRITO"
        btn_carrito.text = mensajeCarrito + "\t\t\t\t\t\t" + precioTotal + "€"
    }
}
