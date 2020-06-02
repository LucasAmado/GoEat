package com.lucasamado.goeatapp.ui.gestion

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.lucasamado.goeatapp.MainActivity
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.bar.EditarBar
import com.lucasamado.goeatapp.viewmodels.EditarBarViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class EditarBarActivity : AppCompatActivity() {
    @Inject
    lateinit var editarBarViewModel: EditarBarViewModel

    lateinit var etNombre: EditText
    lateinit var etTipoComida: EditText
    lateinit var etTiempoPedido: EditText
    lateinit var tvHoraApertura: TextView
    lateinit var tvHoraCierre: TextView
    lateinit var btnSave: Button
    lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_bar)

        (applicationContext as MyApp).appComponent.inject(this)

        etNombre = findViewById(R.id.editTextNombre)
        etTipoComida = findViewById(R.id.editTextTipoComida)
        etTiempoPedido = findViewById(R.id.editTextTiempoPedido)
        tvHoraApertura = findViewById(R.id.textViewHoraApertura)
        tvHoraCierre = findViewById(R.id.textViewHoraCierre)
        btnSave = findViewById(R.id.buttonSave)
        btnCancel = findViewById(R.id.buttonCancel)

        loadBar()

        tvHoraApertura.setOnClickListener {
            val getDate: Calendar = Calendar.getInstance()
            val timePickerDialog =
                TimePickerDialog(
                    this,
                    OnTimeSetListener { view, hourOfDay, minute ->
                        getDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        getDate.set(Calendar.MINUTE, minute)
                        val timeformat = SimpleDateFormat("HH:mm")
                        var formatedDate = timeformat.format(getDate.time)
                        tvHoraApertura.text = formatedDate
                    }, getDate.get(Calendar.HOUR_OF_DAY), getDate.get(Calendar.MINUTE), false
                )

            timePickerDialog.show()
        }

        tvHoraCierre.setOnClickListener {
            val getDate: Calendar = Calendar.getInstance()
            val timePickerDialog =
                TimePickerDialog(
                    this,
                    OnTimeSetListener { view, hourOfDay, minute ->
                        getDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        getDate.set(Calendar.MINUTE, minute)
                        val timeformat = SimpleDateFormat("HH:mm")
                        var formatedDate = timeformat.format(getDate.time)
                        tvHoraCierre.text = formatedDate
                    }, getDate.get(Calendar.HOUR_OF_DAY), getDate.get(Calendar.MINUTE), false
                )

            timePickerDialog.show()
        }

        btnCancel.setOnClickListener {
            val main = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(main)
            finish()
        }

        btnSave.setOnClickListener {
            editarBarViewModel.editarBar(
                EditarBar(
                    horaApertura = tvHoraApertura.text.toString(),
                    horaCierre = tvHoraCierre.text.toString(),
                    nombre = etNombre.text.toString(),
                    tiempoPedido = etTiempoPedido.text.toString(),
                    tipoComida = etTipoComida.text.toString()
                )
            )
            editarBarViewModel.miBar.observe(this, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        Toast.makeText(this, "Bar editado correctamente", Toast.LENGTH_LONG).show()
                        val main = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(main)
                        finish()
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                        Toast.makeText(this, "Error, ${response.message}", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    private fun loadBar() {
        editarBarViewModel.getMyBar()
        editarBarViewModel.miBar.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    var barDetail = response.data
                    etNombre.setText(barDetail?.nombre)
                    etTipoComida.setText(barDetail?.tipoComida)
                    etTiempoPedido.setText(barDetail?.tiempoPedido!!.toString())
                    tvHoraApertura.text = barDetail?.horaApertura
                    tvHoraCierre.text = barDetail?.horaCierre
                }

                is Resource.Loading -> {
                }

                is Resource.Error -> {
                    Toast.makeText(this, "Error, ${response.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
