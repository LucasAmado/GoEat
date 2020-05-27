package com.lucasamado.goeatapp.ui.home.platos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.lucasamado.goeatapp.R
import com.lucasamado.goeatapp.ui.home.carrito.CarritoActivity

class ListaPlatosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_platos)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.carritoIcon) {
            val carrito = Intent(this, CarritoActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(carrito)
            finish()
        }else if (id == R.id.perfilIcon) {
            //TODO crear perfil activity
            /*val perfil = Intent(MyApp.instance, PerfilActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(perfil)
            finish()*/
        }
        return super.onOptionsItemSelected(item)
    }
}
