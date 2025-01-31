package com.lucasamado.goeatapp.ui.home.platos

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
import com.lucasamado.goeatapp.models.plato.Plato

import kotlinx.android.synthetic.main.fragment_plato.view.*

class MyPlatoRecyclerViewAdapter() : RecyclerView.Adapter<MyPlatoRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var platosList: List<Plato> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Plato
            val idPlato = item.id

            val intent = Intent(MyApp.instance, DetallePlatoActivity::class.java).apply {
                putExtra(Constantes.PLATO_ID, idPlato)
                putExtra(Constantes.LUGAR_CARRITO, false)
                putExtra(Constantes.CANTIDAD, 0)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_plato, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = platosList[position]
        holder.tvNombre.text = item.nombre
        holder.tvPrecio.text = item.precioU.toString()+" €"
        holder.ivFoto.load(item.foto){
            crossfade(true)
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_food)
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = platosList.size

    fun setData(platos: List<Plato>?) {
        platosList = platos!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvNombre: TextView = mView.textViewNombre
        val tvPrecio: TextView = mView.textViewHora
        val ivFoto:ImageView = mView.imageViewFoto
    }
}
