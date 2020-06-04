package com.lucasamado.goeatapp.ui.gestion

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.models.bar.BarDto

import kotlinx.android.synthetic.main.fragment_bar_gestion.view.*
import kotlinx.android.synthetic.main.fragment_bar_gestion.view.imageViewFoto
import kotlinx.android.synthetic.main.fragment_bar_gestion.view.textViewNombre

class MyBarGestionRecyclerViewAdapter() : RecyclerView.Adapter<MyBarGestionRecyclerViewAdapter.ViewHolder>() {

    private var baresList: List<BarDto> = ArrayList()

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as BarDto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_bar_gestion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = baresList[position]
        holder.tvNombre.text = item.nombre
        holder.tvHorarios.text = "${item.horaApertura} - ${item.horaCierre}"
        holder.tvTipoComida.text = item.tipoComida
        holder.tvTiempoPreparacion.text = "${item.tiempoPedido} min"

        holder.ivFoto.load(item.foto) {
            crossfade(true)
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = baresList.size

    fun setData(bares: List<BarDto?>) {
        baresList = bares as List<BarDto>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val ivFoto: ImageView = mView.imageViewFoto
        val tvNombre: TextView = mView.textViewNombre
        val tvTipoComida: TextView = mView.textViewNickName
        val tvHorarios: TextView = mView.textViewEmail
        val tvTiempoPreparacion: TextView = mView.textViewTiempoPreparacion
    }
}
