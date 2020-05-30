package com.lucasamado.goeatapp.ui.home.carrito

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDto
import com.lucasamado.goeatapp.viewmodels.LineaPedidoViewModel

import javax.inject.Inject


class LineaPedidoFragment : Fragment() {
    @Inject lateinit var lineaPedidoViewModel: LineaPedidoViewModel

    private lateinit var lineaPedidoAdapter: MyLineaPedidoRecyclerViewAdapter

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_linea_pedido_list, container, false)

        lineaPedidoAdapter = MyLineaPedidoRecyclerViewAdapter()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = lineaPedidoAdapter
            }
        }

        lineaPedidoViewModel.verCarrito()
        lineaPedidoViewModel.carrito.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    lineaPedidoAdapter.setData(response.data!!)
                }

                is Resource.Loading -> {
                }

                is Resource.Error ->{
                    Toast.makeText(activity,"Error, ${response.message}", Toast.LENGTH_LONG).show()
                }
            }
        })

        return view
    }
}
