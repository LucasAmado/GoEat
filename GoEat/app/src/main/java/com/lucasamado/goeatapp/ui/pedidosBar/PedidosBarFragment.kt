package com.lucasamado.goeatapp.ui.pedidosBar

import android.content.Context
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
import com.lucasamado.goeatapp.ui.pedidos.MyPedidoRecyclerViewAdapter
import com.lucasamado.goeatapp.viewmodels.PedidoViewModel
import kotlinx.android.synthetic.main.fragment_bar_list.*
import javax.inject.Inject

class PedidosBarFragment : Fragment() {
    @Inject
    lateinit var pedidoViewModel: PedidoViewModel

    private lateinit var pedidoAdapter: MyPedidosBarRecyclerViewAdapter

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos_bar_list, container, false)

        pedidoAdapter = MyPedidosBarRecyclerViewAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        // Set the adapter
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = pedidoAdapter

            pedidoViewModel.loadPedidosBarHoy()
            pedidoViewModel.pedidosMiBarHoy.observe(viewLifecycleOwner, Observer {response ->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        pedidoAdapter.setData(response.data)
                    }

                    is Resource.Loading -> { showProgressBar()}

                    is Resource.Error ->{
                        hideProgressBar()
                        Toast.makeText(activity,"Error ${response.message}", Toast.LENGTH_LONG).show()
                    }
                }
            })

        }
        return view
    }

    private fun hideProgressBar() {
        animation_loading.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        animation_loading.visibility = View.VISIBLE
    }
}