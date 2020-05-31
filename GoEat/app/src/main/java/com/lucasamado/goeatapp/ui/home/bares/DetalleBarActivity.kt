package com.lucasamado.goeatapp.ui.home.bares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.lifecycle.Observer
import coil.api.load
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.ui.home.carrito.CarritoActivity
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
    var tiposList: MutableList<String> = ArrayList()
    lateinit var adapterTipos: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_bar)

        (applicationContext as MyApp).appComponent.inject(this)

        var idBar = intent.extras?.getString(Constantes.BAR_ID).toString()

        image = findViewById(R.id.imageViewFoto)
        nombre = findViewById(R.id.textViewNombre)
        horarios = findViewById(R.id.textViewHora)
        tipoComida = findViewById(R.id.textViewTipoComida)
        btn_informacion = findViewById(R.id.buttonInformacion)
        lvTipoPlatos = findViewById(R.id.lvTipoPlatos)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.carritoIcon) {
            val carrito = Intent(this, CarritoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(carrito)
            finish()
        } else if (id == R.id.perfilIcon) {
            //TODO crear perfil activity
            /*val perfil = Intent(MyApp.instance, PerfilActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(perfil)
            finish()*/
        }
        return super.onOptionsItemSelected(item)
    }


    private fun loadBarDetail(idBar: String) {
        barDetailViewModel.getDetailBar(idBar)
        barDetailViewModel.barSelect.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    var barDetail = response.data
                    image.load(barDetail?.foto) {
                        crossfade(true)
                        placeholder(R.drawable.ic_food)
                    }
                    nombre.text = barDetail?.nombre
                    tipoComida.text = barDetail?.tipoComida
                    horarios.text = barDetail?.horaApertura + " - " + barDetail?.horaCierre
                }

                is Resource.Loading -> { }

                is Resource.Error -> {
                    Toast.makeText(this, "Error, ${response.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun loadTiposPlatos(idBar: String) {
        barDetailViewModel.getTipos(idBar)
        barDetailViewModel.tiposPlato.observe(this, Observer {response ->
            when (response) {
                is Resource.Success -> {
                    var it = response.data!!
                    tiposList.addAll(it)
                    adapterTipos = ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_expandable_list_item_1,
                        tiposList
                    )
                    lvTipoPlatos.adapter = adapterTipos
                }

                is Resource.Loading -> { }

                is Resource.Error -> {
                    Toast.makeText(MyApp.instance, "Error, ${response.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }
}
