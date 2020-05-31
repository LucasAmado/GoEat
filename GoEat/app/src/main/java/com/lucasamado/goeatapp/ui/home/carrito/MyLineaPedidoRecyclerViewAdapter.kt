package com.lucasamado.goeatapp.ui.home.carrito

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
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDto
import com.lucasamado.goeatapp.ui.home.platos.DetallePlatoActivity
import kotlinx.android.synthetic.main.fragment_linea_pedido.view.*
import java.text.DecimalFormat


class MyLineaPedidoRecyclerViewAdapter() : RecyclerView.Adapter<MyLineaPedidoRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var lpList: List<LineaPedidoDto> = ArrayList()
    var df = DecimalFormat("#.00")

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as LineaPedidoDto
            val idPlato = item.plato.id
            val intent = Intent(MyApp.instance, DetallePlatoActivity::class.java).apply {
                putExtra(Constantes.PLATO_ID, idPlato)
                putExtra(Constantes.LUGAR_CARRITO, true)
                putExtra(Constantes.CANTIDAD, item.cantidad)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_linea_pedido, parent, false)
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

    fun setData(lineasPedido: List<LineaPedidoDto>) {
        lpList = lineasPedido
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvNombre: TextView = mView.textViewNombre
        val tvPrecio: TextView = mView.textViewHora
        val tvCantidad: TextView = mView.textViewCantidad
        val ivFoto: ImageView = mView.imageViewFoto
    }
}
