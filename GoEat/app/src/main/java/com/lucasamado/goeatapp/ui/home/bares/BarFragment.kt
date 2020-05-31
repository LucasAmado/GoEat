package com.lucasamado.goeatapp.ui.home.bares

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.bar.BarDetailDto
import com.lucasamado.goeatapp.models.bar.BarDto
import com.lucasamado.goeatapp.viewmodels.BarViewModel
import kotlinx.android.synthetic.main.fragment_bar_list.*
import javax.inject.Inject


class BarFragment : Fragment() {
    @Inject lateinit var barViewModel: BarViewModel

    private lateinit var barAdapter: MyBarRecyclerViewAdapter

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bar_list, container, false)

        barAdapter = MyBarRecyclerViewAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)

        // Set the adapter
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = barAdapter
        }

        barViewModel.listaBares.observe(viewLifecycleOwner, Observer {resp ->
            when(resp){
                is Resource.Success -> {
                    hideProgressBar()
                    barAdapter.setData(resp.data)
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Error ->{
                    hideProgressBar()
                    Toast.makeText(activity,"Error, ${resp.message}", Toast.LENGTH_LONG).show()
                }
            }
        })

        return view
    }

    private fun hideProgressBar() {
        animation_loading.visibility = INVISIBLE
    }

    private fun showProgressBar() {
        animation_loading.visibility = VISIBLE
    }
}
