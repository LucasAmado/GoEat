package com.lucasamado.goeatapp.ui.home.bares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import coil.api.load
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.ui.home.mapa.MapaActivity
import com.lucasamado.goeatapp.ui.home.platos.ListaPlatosActivity
import com.lucasamado.goeatapp.viewmodels.BarDetailViewModel
import javax.inject.Inject

class DetalleBarActivity : AppCompatActivity() {

    @Inject
    lateinit var barDetailViewModel: BarDetailViewModel

    lateinit var image: ImageView
    lateinit var nombre: TextView
    lateinit var tipoComida: TextView
    lateinit var horarios: TextView
    lateinit var lvTipoPlatos: ListView
    lateinit var btn_informacion: Button
    lateinit var btn_carrito: Button
    var tiposList: MutableList<String> = ArrayList()
    lateinit var adapterTipos: ArrayAdapter<String>
    // TODO comprobar linea de pedido
    // lateinit var idLineaPedido: LineaPedido? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_bar)

        (applicationContext as MyApp).appComponent.inject(this)

        var idBar = intent.extras?.getString(Constantes.BAR_ID).toString()

        image = findViewById(R.id.imageViewFoto)
        nombre = findViewById(R.id.textViewNombre)
        horarios = findViewById(R.id.textViewHorario)
        tipoComida = findViewById(R.id.textViewTipoComida)
        btn_informacion = findViewById(R.id.buttonInformacion)
        btn_carrito = findViewById(R.id.buttonCarrito)
        lvTipoPlatos = findViewById(R.id.lvTipoPlatos)


        //TODO pensar en como esconder y mostrar botón
        //crear linea vacía aquí
        // al añadir platos traer el id de la linea e igualarlo
        // si el id del no es nuelo ni vacío -> mostrar boton
        btn_carrito.visibility = View.INVISIBLE

        loadBarDetail(idBar)
        loadTiposPlatos(idBar)

        btn_informacion.setOnClickListener {
            val mapa = Intent(this, MapaActivity::class.java).apply {
                putExtra(Constantes.BAR_ID, idBar)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(mapa)
            finish()
        }

        lvTipoPlatos.setOnItemClickListener { parent, view, position, id ->
            var tipoSelect = tiposList.get(position)
            val intent = Intent(this, ListaPlatosActivity::class.java).apply {
                putExtra(Constantes.BAR_ID, idBar)
                putExtra(Constantes.TIPO_PLATO, tipoSelect)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    private fun loadBarDetail(idBar: String) {
        barDetailViewModel.getDetailBar(idBar).observe(this, Observer {
            if(it != null){
                image.load(it.foto){
                    crossfade(true)
                    placeholder(R.drawable.ic_food)
                }
                nombre.text = it.nombre
                tipoComida.text = it.tipoComida
                horarios.text = it.horaApertura+" - "+it.horaCierre
            }
        })
    }

    private fun loadTiposPlatos(idBar: String) {
       barDetailViewModel.getTipos(idBar).observe(this, Observer {
           if(it!=null){
               tiposList.addAll(it)
               adapterTipos = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, tiposList)
               lvTipoPlatos.adapter = adapterTipos
           }
       })
    }
}
