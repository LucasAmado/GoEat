package com.lucasamado.goeatapp

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.SharedPreferencesManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_bares, R.id.navigation_pedidos, R.id.navigation_perfil
            //R.id.navigation_gestion
        //TODO descomentar
            ))
        //getCurrentUser(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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
