package com.lucasamado.goeatapp.ui.perfil

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import coil.api.load
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.viewmodels.PerfilViewModel
import kotlinx.android.synthetic.main.activity_perfil.*
import javax.inject.Inject


class PerfilActivity : AppCompatActivity() {
    @Inject
    lateinit var perfilViewModel: PerfilViewModel
    lateinit var tvNombreCompleto: TextView
    lateinit var tvEmail: TextView
    lateinit var tvNickName: TextView
    lateinit var tvFechaCreacion: TextView
    lateinit var tvFechaModificacion: TextView
    lateinit var ivFoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        (applicationContext as MyApp).appComponent.inject(this)

        tvNombreCompleto = findViewById(R.id.textViewNombreCompleto)
        tvNickName = findViewById(R.id.textViewNickName)
        tvEmail = findViewById(R.id.textViewEmail)
        tvFechaCreacion = findViewById(R.id.textViewFechaCreacion)
        tvFechaModificacion = findViewById(R.id.textViewFechaModificacion)
        ivFoto = findViewById(R.id.imageViewFoto)

        loadMe()

        btnEditar.setOnClickListener { view ->
            val dialog: DialogFragment = EditarPerfilDialogFragment()
            dialog.show(supportFragmentManager, "EditarPerfilDialogFragment")
        }
    }

    fun reverseOrderOfWords(s: String) = s.split("-").reversed().joinToString("/")

    private fun loadMe() {
        perfilViewModel.getMe()
        perfilViewModel.me.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    var it = response.data!!
                    tvNombreCompleto.text = it.nombreCompleto
                    tvNickName.text = it.nickName
                    tvEmail.text = it.email
                    tvFechaCreacion.text = reverseOrderOfWords(it.fechaCreacion)
                    if (it.fechaCambio == null) {
                        tvFechaModificacion.text = "Nunca"
                    } else {
                        tvFechaModificacion.text = reverseOrderOfWords(it.fechaCambio)
                    }

                    if (it.avatar.isEmpty()) {
                        ivFoto.load(R.drawable.ic_image_user) {
                            crossfade(true)
                        }
                    } else {
                        ivFoto.load(it.avatar) {
                            crossfade(true)
                            placeholder(R.drawable.ic_image_user)
                        }
                    }
                }

                is Resource.Loading -> { }

                is Resource.Error -> {
                    Toast.makeText(this, "Error: ${response.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}
