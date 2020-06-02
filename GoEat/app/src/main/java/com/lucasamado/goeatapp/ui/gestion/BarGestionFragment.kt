package com.lucasamado.goeatapp.ui.gestion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.viewmodels.BarViewModel
import kotlinx.android.synthetic.main.fragment_bar_gestion_list.*
import javax.inject.Inject


class BarGestionFragment : Fragment() {
    @Inject
    lateinit var barViewModel: BarViewModel
    private var columnCount = 1

    private lateinit var barAdapter: MyBarGestionRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bar_gestion_list, container, false)

        barAdapter = MyBarGestionRecyclerViewAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)

        // Set the adapter
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = barAdapter
        }

        barViewModel.getMyBar()
        barViewModel.miBar.observe(viewLifecycleOwner, Observer {resp ->
            when(resp){
                is Resource.Success -> {
                    hideProgressBar()
                    barAdapter.setData(listOf(resp.data))
                    btnEditar.visibility = VISIBLE

                    btnEditar.setOnClickListener{
                        val editar = Intent(MyApp.instance, EditarBarActivity::class.java).apply{
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(editar)
                        activity?.finish()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                    btnEditar.visibility = INVISIBLE
                }

                is Resource.Error ->{
                    hideProgressBar()
                    btnEditar.visibility = INVISIBLE
                    Toast.makeText(activity,"Error: ${resp.message}", Toast.LENGTH_LONG).show()
                }
            }
        })

        return view
    }

    private fun hideProgressBar() {
        animation_loading.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        animation_loading.visibility = View.VISIBLE
    }
}

