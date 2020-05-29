package com.lucasamado.goeatapp.ui.pedidos.detalle

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDetalle
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDto
import kotlinx.android.synthetic.main.fragment_linea_pedido.view.*
import java.text.DecimalFormat

class MyLineaPedidoDetalleRecyclerViewAdapter() : RecyclerView.Adapter<MyLineaPedidoDetalleRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var lpList: List<LineaPedidoDetalle> = ArrayList()
    var df = DecimalFormat("#.00")

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as LineaPedidoDetalle
            //TODO intent al detalle del plato? (pasando false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_linea_pedido_detalle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lpList[position]
        holder.tvNombre.text = item.plato.nombre
        holder.tvCantidad.text = "Cantidad: ${item.cantidad}"
        holder.tvPrecio.text = df.format(item.totalLinea)+"€"

        holder.ivFoto.load(item.plato.foto) {
            crossfade(true)
            placeholder(R.drawable.ic_food)
            transformations(CircleCropTransformation())
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = lpList.size

    fun setData(lineasPedido: List<LineaPedidoDetalle>) {
        lpList = lineasPedido
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvNombre: TextView = mView.textViewNombre
        val tvPrecio: TextView = mView.textViewPrecio
        val tvCantidad: TextView = mView.textViewCantidad
        val ivFoto: ImageView = mView.imageViewFoto
    }
}
