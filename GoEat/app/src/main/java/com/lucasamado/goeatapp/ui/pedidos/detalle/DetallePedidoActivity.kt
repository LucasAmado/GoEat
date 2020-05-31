package com.lucasamado.goeatapp.ui.pedidos.detalle

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.api.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lucasamado.goeatapp.MainActivity
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.common.SharedPreferencesManager
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
    lateinit var tvComentarioTitle: TextView
    lateinit var tvTotal: TextView
    lateinit var btn_fav: FloatingActionButton
    lateinit var btn_estado: Button
    lateinit var tvEstado: TextView
    lateinit var ivEstado: ImageView
    var idPedido: String? = null
    var df = DecimalFormat("#.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)

        (applicationContext as MyApp).appComponent.inject(this)


        idPedido = intent.extras?.getString(Constantes.PEDIDO_ID).toString()
        var role = SharedPreferencesManager().getSomeStringValue(Constantes.USER_ROLES)

        tvNombre = findViewById(R.id.textViewNombre)
        tvTipoComida = findViewById(R.id.textViewTipoComida)
        tvFecha = findViewById(R.id.textViewFecha)
        tvHora = findViewById(R.id.textViewHora)
        tvComentario = findViewById(R.id.textViewComentario)
        tvComentarioTitle = findViewById(R.id.textView4)
        tvTotal = findViewById(R.id.textViewTotal)
        btn_fav = findViewById(R.id.floatingFav)
        btn_estado = findViewById(R.id.button_Estado)
        tvEstado = findViewById(R.id.textViewEstado)
        ivEstado = findViewById(R.id.imageViewEstado)

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
                    tvEstado.text = it.estado
                    var estado = when {
                        it.estado.equals("SOLICITADO") -> {
                            R.drawable.ic_solicitado
                        }
                        it.estado.equals("COCINA") -> {
                            R.drawable.ic_cocina
                        }
                        it.estado.equals("PREPARADO") -> {
                            R.drawable.ic_preparado
                        }
                        else -> {
                            R.drawable.ic_entregado
                        }
                    }
                    ivEstado.load(estado) {
                        crossfade(true)
                    }

                    if(it.comentario.isEmpty()){
                        tvComentario.visibility = View.GONE
                        tvComentarioTitle.visibility = View.GONE
                    }else{
                        tvComentario.text = it.comentario
                    }
                    if(role.equals("USER") || tvEstado.text.equals("ENTREGADO")){
                        btn_estado.visibility = INVISIBLE
                        if(it.favorito){
                            btn_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav))
                        }
                    }else{
                        btn_fav.visibility = INVISIBLE
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

                    is Resource.Loading -> { }

                    is Resource.Error -> {
                        Toast.makeText(this,"Error, ${response.message}", Toast.LENGTH_LONG).show()
                    }
                }
            })

        }

        btn_estado.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setIcon(R.drawable.ic_warning)
            builder.setTitle("¿Está seguro de querer cambiar el estado?")
            builder.setMessage("Una vez hecho no se podrá deshacer")
            builder.setPositiveButton(R.string.confirmar) { dialogInterface, i ->
                detallePedidoViewModel.changeEstado(idPedido!!)
                detallePedidoViewModel.cambioEstado.observe(this, Observer {response ->
                    when(response) {
                        is Resource.Success ->  {
                            var it = response.data!!
                            tvEstado.text = it
                            var estado = when {
                                it.equals("SOLICITADO") -> {
                                    R.drawable.ic_solicitado
                                }
                                it.equals("COCINA") -> {
                                    R.drawable.ic_cocina
                                }
                                it.equals("PREPARADO") -> {
                                    R.drawable.ic_preparado
                                }
                                else -> {
                                    R.drawable.ic_entregado
                                }
                            }
                            ivEstado.load(estado) {
                                crossfade(true)
                            }

                            if(it.equals("ENTREGADO")){
                                btn_estado.visibility = INVISIBLE
                            }
                        }

                        is Resource.Loading -> { }

                        is Resource.Error -> {
                            Toast.makeText(this,"Error, ${response.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
            builder.setNegativeButton(R.string.cancel, null)
            builder.show()
        }
    }
}
