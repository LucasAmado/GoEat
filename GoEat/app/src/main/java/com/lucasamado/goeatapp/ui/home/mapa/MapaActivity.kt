package com.lucasamado.goeatapp.ui.home.mapa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.viewmodels.BarDetailViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {
    @Inject
    lateinit var barDetailViewModel: BarDetailViewModel
    private lateinit var mMap: GoogleMap
    lateinit var nombre: String
    var latitud = 0.0
    var longitud = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        (applicationContext as MyApp).appComponent.inject(this)

        var idBar = intent.extras?.getString(Constantes.BAR_ID).toString()

        loadBar(idBar)
    }

    private fun loadBar(idBar: String) {
        barDetailViewModel.getDetailBar(idBar)
        barDetailViewModel.barSelect.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    var barDetail = response.data!!
                    latitud = barDetail.latitud
                    longitud = barDetail.longitud
                    nombre = barDetail.nombre

                    val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this)
                }

                is Resource.Loading -> {
                }

                is Resource.Error -> {
                    Toast.makeText(MyApp.instance, "Error, ${response.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        val bar = LatLng(latitud, longitud)
        mMap.addMarker(MarkerOptions().position(bar).title(nombre))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bar))
    }
}
