package com.lucasamado.goeatapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lucasamado.goeatapp.ui.home.carrito.CarritoActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_bares, R.id.navigation_pedidos, R.id.navigation_gestion))
        //TODO descomentar
        //getCurrentUser(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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

    //TODO descomentar
    /*fun getCurrentUser(navController: NavController){
        var roles = SharedPreferencesManager().getSomeStringValue(Constantes.USER_ROLES)

        var navNormal: BottomNavigationView = findViewById(R.id.nav_view)
        var navAdmin: BottomNavigationView = findViewById(R.id.nav_admin)

        if(roles.equals("ADMIN")){
            navAdmin.setVisibility(View.VISIBLE)
            navNormal.setVisibility(View.INVISIBLE)
            setupWithNavController(navAdmin, navController)
        }else{
            navNormal.setVisibility(View.VISIBLE)
            navAdmin.setVisibility(View.INVISIBLE)
            setupWithNavController(navNormal, navController)
        }
    }*/
}
