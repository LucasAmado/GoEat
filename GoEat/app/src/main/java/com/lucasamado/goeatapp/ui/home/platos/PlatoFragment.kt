package com.lucasamado.goeatapp.ui.home.platos

import android.os.Bundle
import android.util.Log
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
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.plato.Plato
import com.lucasamado.goeatapp.viewmodels.PlatoViewModel
import javax.inject.Inject


class PlatoFragment : Fragment() {

    @Inject
    lateinit var platoViewModel: PlatoViewModel

    private lateinit var platoAdapter: MyPlatoRecyclerViewAdapter
    var idBar: String? = null
    var tipoPlato: String? = null

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plato_list, container, false)

        tipoPlato = activity?.intent?.extras?.getString(Constantes.TIPO_PLATO)
        idBar = activity?.intent?.extras?.getString(Constantes.BAR_ID)

        platoAdapter =
            MyPlatoRecyclerViewAdapter()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = platoAdapter
            }
        }


        platoViewModel.getListaPlatosByTipo(tipoPlato!!, idBar!!)
        platoViewModel.platosList.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    platoAdapter.setData(response.data)
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
