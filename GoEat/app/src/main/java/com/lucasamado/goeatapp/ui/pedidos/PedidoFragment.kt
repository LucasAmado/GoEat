package com.lucasamado.goeatapp.ui.pedidos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import com.lucasamado.goeatapp.viewmodels.PedidoViewModel
import javax.inject.Inject

class PedidoFragment : Fragment() {
    @Inject lateinit var pedidoViewModel: PedidoViewModel

    private lateinit var pedidoAdapter: MyPedidoRecyclerViewAdapter
    private var pedidoList: List<PedidoDto> = ArrayList()

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedido_list, container, false)

        pedidoAdapter = MyPedidoRecyclerViewAdapter()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = pedidoAdapter
            }
            pedidoViewModel.loadMisPedidos().observe(this, Observer {
                if(it!=null){
                    pedidoList = it
                    pedidoAdapter.setData(pedidoList)
                }
            })
        }
        return view
    }
}
