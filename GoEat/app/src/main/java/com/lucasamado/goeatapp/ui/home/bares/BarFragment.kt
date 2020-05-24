package com.lucasamado.goeatapp.ui.home.bares

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.models.bar.Bar
import com.lucasamado.goeatapp.viewmodels.BarViewModel
import javax.inject.Inject


class BarFragment : Fragment() {
    @Inject lateinit var barViewModel: BarViewModel

    private lateinit var barAdapter: MyBarRecyclerViewAdapter
    private var barList: List<Bar> = ArrayList()

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

        barAdapter =
            MyBarRecyclerViewAdapter()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = barAdapter
            }
        }


        barViewModel.getBares().observe(viewLifecycleOwner, Observer {
            barList = it
            barAdapter.setData(barList)
        })

        return view
    }
}
