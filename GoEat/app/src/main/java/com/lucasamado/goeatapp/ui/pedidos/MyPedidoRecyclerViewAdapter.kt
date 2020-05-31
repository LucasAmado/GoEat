package com.lucasamado.goeatapp.ui.pedidos

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import com.lucasamado.goeatapp.ui.pedidos.detalle.DetallePedidoActivity

import kotlinx.android.synthetic.main.fragment_pedido.view.*
import kotlinx.android.synthetic.main.fragment_pedido.view.imageViewFoto
import kotlinx.android.synthetic.main.fragment_pedido.view.textViewNombre
import java.text.DecimalFormat


class MyPedidoRecyclerViewAdapter() : RecyclerView.Adapter<MyPedidoRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var pedidosList: List<PedidoDto> = ArrayList()
    var df = DecimalFormat("#.00")

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as PedidoDto
            val detalle = Intent(MyApp.instance, DetallePedidoActivity::class.java).apply {
                putExtra(Constantes.PEDIDO_ID, item.id)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(detalle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_pedido, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pedidosList[position]
        holder.tvNombre.text = item.bar.nombre
        holder.tvPrecio.text = "${df.format(item.totalPedido)} €"
        holder.tvFecha.text = reverseOrderOfWords(item.fechaPedido)

        holder.ivFoto.load(item.bar.foto) {
            crossfade(true)
            placeholder(R.drawable.ic_food)
            transformations(CircleCropTransformation())
        }

        if(item.favorito){
            holder.ivFav.load(R.drawable.ic_fav) {
                crossfade(true)
            }
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = pedidosList.size

    fun setData(pedidos: List<PedidoDto>?) {
        pedidosList = pedidos!!
        notifyDataSetChanged()
    }

    fun reverseOrderOfWords(s: String) = s.split("-").reversed().joinToString("/")

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val ivFoto: ImageView = mView.imageViewFoto
        val ivFav: ImageView = mView.imageViewFav
        val tvNombre: TextView = mView.textViewNombre
        val tvPrecio: TextView = mView.textViewHora
        val tvFecha: TextView = mView.textViewFecha
    }
}
