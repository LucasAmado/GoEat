package com.lucasamado.goeatapp.ui.home.bares

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.viewmodels.BarViewModel
import kotlinx.android.synthetic.main.fragment_bar_list.*
import javax.inject.Inject


class BarFragment : Fragment() {
    @Inject lateinit var barViewModel: BarViewModel

    private lateinit var barAdapter: MyBarRecyclerViewAdapter

    private var columnCount = 1

    private val tiposList: MutableList<String> = ArrayList()
    lateinit var adapterTipos: ArrayAdapter<String>
    lateinit var tipoSelect: String
    lateinit var reload: MenuItem

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bar_fragment, menu)
        reload = menu.findItem(R.id.action_reload)
        reload.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_tipos_comida -> {
                val builder = AlertDialog.Builder(context)s
                val title = TextView(context)
                title.text = "Tipos de comida"
                title.setPadding(20, 30, 20, 30)
                title.textSize = 20F
                title.setTextColor(resources.getColor(R.color.colorAccent))
                title.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
                builder.setCustomTitle(title)

                val lvTipos = ListView(context)
                lvTipos.adapter = adapterTipos

                val dialog = builder
                    .setView(lvTipos)
                    .create()

                lvTipos.setOnItemClickListener { parent, view, position, id ->
                    tipoSelect = tiposList[position]
                    barViewModel.getBaresByTipo(tipoSelect)
                    reload.isVisible = true
                    dialog.dismiss()
                }

                dialog.show()
            }
            R.id.action_reload -> {
                barViewModel.getBares()
                reload.isVisible = false
            }
        }
        return super.onOptionsItemSelected(item)
    }

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

        loadTiposComida()

        barViewModel.getBares()
        barViewModel.listaBares.observe(viewLifecycleOwner, Observer { resp ->
            when (resp) {
                is Resource.Success -> {
                    hideProgressBar()
                    barAdapter.setData(resp.data)
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(activity, "Error, ${resp.message}", Toast.LENGTH_LONG).show()
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

    private fun loadTiposComida() {
        barViewModel.getTiposComida()
        barViewModel.tiposComida.observe(viewLifecycleOwner, Observer {resp ->
            when (resp) {
                is Resource.Success -> {
                    tiposList.addAll(resp.data!!)
                    adapterTipos = ArrayAdapter<String>(
                        MyApp.instance,
                        android.R.layout.simple_expandable_list_item_1,
                        tiposList
                    )
                }

                is Resource.Loading -> { }

                is Resource.Error -> {
                    Toast.makeText(activity, "Error: ${resp.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
