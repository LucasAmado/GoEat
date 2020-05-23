package com.lucasamado.goeatapp.ui.bares

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.models.Bar
import kotlinx.android.synthetic.main.fragment_bar.view.*


class MyBarRecyclerViewAdapter() : RecyclerView.Adapter<MyBarRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var baresList: List<Bar> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
           val item = v.tag as Bar
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_bar, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = baresList[position]
        holder.tvNombre.text = item.nombre

        holder.ivFoto.load(item.foto) {
            crossfade(true)
            placeholder(R.drawable.ic_food)
            transformations(CircleCropTransformation())
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = baresList.size

    fun setData(popularMovies: List<Bar>?) {
        baresList = popularMovies!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val ivFoto: ImageView = mView.imageViewFoto
        val tvNombre: TextView = mView.textViewNombre
    }
}
