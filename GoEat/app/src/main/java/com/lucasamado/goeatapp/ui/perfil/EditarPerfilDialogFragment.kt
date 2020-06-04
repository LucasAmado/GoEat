package com.lucasamado.goeatapp.ui.perfil

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.common.MyApp
import com.lucasamado.goeatapp.common.Resource
import com.lucasamado.goeatapp.models.user.EditarUser
import com.lucasamado.goeatapp.viewmodels.PerfilViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class EditarPerfilDialogFragment: DialogFragment() {
    @Inject lateinit var perfilViewModel: PerfilViewModel

    lateinit var v: View
    lateinit var etNombreCompleto: EditText
    lateinit var etNickname: EditText
    lateinit var etEmail: EditText
    lateinit var matcher: Matcher
    val pattern = Pattern.compile(
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        (activity?.applicationContext as MyApp).appComponent.inject(this)

        loadMe()

        var builder = AlertDialog.Builder(context)
        builder.setTitle("Editar perfil")

        builder.setCancelable(true)

        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_editar_perfil, null);
        builder.setView(v)

        etNombreCompleto = v.findViewById(R.id.editTextNombreCompleto)
        etNickname = v.findViewById(R.id.editTextUsername)
        etEmail = v.findViewById(R.id.editTextEmail)

        with(etEmail) {
            onFocusChangeListener = android.view.View.OnFocusChangeListener { v, hasFocus ->
                matcher = pattern.matcher(text.toString())
                if (text.toString().isEmpty()) {
                    error = "Debe escribir un email"
                } else if (!matcher.find()) {
                    error = "Email no válido"
                }
            }
        }


        builder.setPositiveButton(R.string.confirmar, DialogInterface.OnClickListener { dialog, which ->
            matcher = pattern.matcher(etEmail.text)
            if(!matcher.find()){
                Toast.makeText(MyApp.instance, "El email no es válido", Toast.LENGTH_LONG).show()
            }
            if(etNickname.text.isEmpty() || etEmail.text.isEmpty()){
                Toast.makeText(MyApp.instance, "No puede haber ningún campo vacío", Toast.LENGTH_LONG).show()
            }else{
                perfilViewModel.editarMiPerfil(
                    EditarUser(
                        nombreCompleto = etNombreCompleto.text.toString(),
                        email = etEmail.text.toString(),
                        nickName = etNickname.text.toString()
                    )
                )
                dialog.dismiss()
            }
        })

        builder.setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        return builder.create()
    }

    private fun loadMe() {
        perfilViewModel.getMe()
        perfilViewModel.me.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    var it = response.data!!
                    etNickname.setText(it.nickName)
                    etEmail.setText(it.email)
                    etNombreCompleto.setText(it.nombreCompleto)
                }

                is Resource.Loading -> { }

                is Resource.Error -> {
                    Toast.makeText(MyApp.instance, "Error: ${response.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}