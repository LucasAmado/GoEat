package com.lucasamado.goeatapp.ui.pedidos.detalle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.viewmodels.PedidoDetalleViewModel
import java.text.DecimalFormat
import javax.inject.Inject

class DetallePedidoActivity : AppCompatActivity() {
    @Inject lateinit var detallePedidoViewModel: PedidoDetalleViewModel

    lateinit var tvNombre: TextView
    lateinit var tvTipoComida: TextView
    lateinit var tvFecha: TextView
    lateinit var tvHora: TextView
    lateinit var tvComentario: TextView
    lateinit var tvComentarioTittle: TextView
    lateinit var tvTotal: TextView
    lateinit var btn_fav: FloatingActionButton
    var idPedido: String? = null
    var df = DecimalFormat("#.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)

        (applicationContext as MyApp).appComponent.inject(this)

        idPedido = intent.extras?.getString(Constantes.PEDIDO_ID).toString()

        tvNombre = findViewById(R.id.textViewNombre)
        tvTipoComida = findViewById(R.id.textViewTipoComida)
        tvFecha = findViewById(R.id.textViewFecha)
        tvHora = findViewById(R.id.textViewHora)
        tvComentario = findViewById(R.id.textViewComentario)
        tvComentarioTittle = findViewById(R.id.textView4)
        tvTotal = findViewById(R.id.textViewTotal)
        btn_fav = findViewById(R.id.floatingFav)

        fun reverseOrderOfWords(s: String) = s.split("-").reversed().joinToString("/")

        detallePedidoViewModel.getPedido(idPedido!!)
        detallePedidoViewModel.pedidoSelect.observe(this, Observer {response ->
            when(response) {
                is Resource.Success ->  {
                    var it = response.data!!
                    tvNombre.text = it.bar.nombre
                    tvTipoComida.text = it.bar.tipoComida
                    tvFecha.text = reverseOrderOfWords(it.fechaPedido)
                    tvHora.text = it.horaRecogida
                    tvTotal.text = "TOTAL: ${df.format(it.totalPedido)}€"

                    if(it.comentario.isEmpty()){
                        tvComentario.visibility = View.GONE
                        tvComentarioTittle.visibility = View.GONE
                    }else{
                        tvComentario.text = it.comentario
                    }

                    if(it.favorito){
                        btn_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav))
                    }
                }

                is Resource.Loading -> { }

                is Resource.Error -> {
                    Toast.makeText(this,"Error, ${response.message}", Toast.LENGTH_LONG).show()
                }
            }
        })

        btn_fav.setOnClickListener {
            detallePedidoViewModel.changePedidoFav(idPedido!!)
            detallePedidoViewModel.favBoolean.observe(this, Observer {response ->
                when(response) {
                    is Resource.Success ->  {
                        var it = response.data
                        if(it==true){
                            btn_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav))
                        }else{
                            btn_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_nofav))
                        }
                    }

                    is Resource.Loading -> {
                        //CARGANDO
                    }

                    is Resource.Error -> {
                        Toast.makeText(this,"Error, ${response.message}", Toast.LENGTH_LONG).show()
                    }
                }
            })

        }
    }
}
