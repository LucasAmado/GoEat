package com.lucasamado.goeatapp.ui.pedidos

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import com.lucasamado.goeatapp.viewmodels.PedidoViewModel
import kotlinx.android.synthetic.main.fragment_bar_list.*
import kotlinx.coroutines.delay
import javax.inject.Inject

class PedidoFragment : Fragment() {
    @Inject lateinit var pedidoViewModel: PedidoViewModel

    private lateinit var pedidoAdapter: MyPedidoRecyclerViewAdapter

    private var columnCount = 1

    lateinit var reload: MenuItem
    lateinit var fav: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_mis_pedidos_fragment, menu)
        reload = menu.findItem(R.id.action_reload)
        fav = menu.findItem(R.id.action_fav)
        reload.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fav -> {
                pedidoViewModel.loadMisPedidosFav()
                reload.isVisible = true
                fav.isVisible = false
            }
            R.id.action_reload -> {
                pedidoViewModel.loadMisPedidos()
                reload.isVisible = false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedido_list, container, false)

        pedidoAdapter = MyPedidoRecyclerViewAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        // Set the adapter
            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = pedidoAdapter

            pedidoViewModel.loadMisPedidos()
            pedidoViewModel.misPedidos.observe(viewLifecycleOwner, Observer {response ->
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
