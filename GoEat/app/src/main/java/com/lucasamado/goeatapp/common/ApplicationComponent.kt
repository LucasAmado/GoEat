package com.lucasamado.goeatapp.common

import com.lucasamado.goeatapp.api.NetworkModule
import com.lucasamado.goeatapp.ui.LoginActivity
import com.lucasamado.goeatapp.ui.SignupActivity
import com.lucasamado.goeatapp.ui.gestion.BarGestionFragment
import com.lucasamado.goeatapp.ui.gestion.EditarBarActivity
import com.lucasamado.goeatapp.ui.home.bares.BarFragment
import com.lucasamado.goeatapp.ui.home.bares.DetalleBarActivity
import com.lucasamado.goeatapp.ui.home.carrito.CarritoActivity
import com.lucasamado.goeatapp.ui.home.carrito.LineaPedidoFragment
import com.lucasamado.goeatapp.ui.home.mapa.MapaActivity
import com.lucasamado.goeatapp.ui.home.platos.DetallePlatoActivity
import com.lucasamado.goeatapp.ui.home.platos.ListaPlatosActivity
import com.lucasamado.goeatapp.ui.home.platos.PlatoFragment
import com.lucasamado.goeatapp.ui.pedidos.PedidoFragment
import com.lucasamado.goeatapp.ui.pedidos.detalle.DetallePedidoActivity
import com.lucasamado.goeatapp.ui.pedidos.detalle.LineaPedidoDetalleFragment
import com.lucasamado.goeatapp.ui.pedidosBar.PedidosBarFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(signupActivity: SignupActivity)
    fun inject(barFragment: BarFragment)
    fun inject(detalleBarActivity: DetalleBarActivity)
    fun inject(mapaActivity: MapaActivity)
    fun inject(platoFragment: PlatoFragment)
    fun inject(detallePlatoActivity: DetallePlatoActivity)
    fun inject(lineaPedidoFragment: LineaPedidoFragment)
    fun inject(carritoActivity: CarritoActivity)
    fun inject(pedidoFragment: PedidoFragment)
    fun inject(lineaPedidoDetalleFragment: LineaPedidoDetalleFragment)
    fun inject(detallePedidoActivity: DetallePedidoActivity)
    fun inject(pedidosBarFragment: PedidosBarFragment)
    fun inject(barGestionFragment: BarGestionFragment)
    fun inject(editarBarActivity: EditarBarActivity)
}