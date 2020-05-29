package com.lucasamado.goeatapp.api

import com.lucasamado.goeatapp.models.bar.BarDetailDto
import com.lucasamado.goeatapp.models.bar.BarDto
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDetalle
import com.lucasamado.goeatapp.models.pedido.CreatePedido
import com.lucasamado.goeatapp.models.lineasPedido.LineaPedidoDto
import com.lucasamado.goeatapp.models.pedido.PedidoDetalleDto
import com.lucasamado.goeatapp.models.pedido.PedidoDto
import com.lucasamado.goeatapp.models.plato.Plato
import com.lucasamado.goeatapp.models.plato.PlatoDto
import com.lucasamado.goeatapp.models.user.LoginRequest
import com.lucasamado.goeatapp.models.user.LoginResponse
import com.lucasamado.goeatapp.models.user.SignupRequest
import com.lucasamado.goeatapp.models.user.SignupResponse
import retrofit2.Call
import retrofit2.http.*

interface GoEatService {

    //usuarios

    @POST("/signup")
    fun createUser(@Body newUser: SignupRequest): Call<SignupResponse>

    @POST("auth/login")
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    //Bares

    @GET("bares/")
    fun getBaresList(): Call<List<BarDto>>

    @GET("bares/{id}")
    fun getBarById(@Path("id") id: String): Call<BarDetailDto>

    @GET("bares/consultar/horarios-recogida/{id}")
    fun consultarHorariosRecogidaBar(@Path("id") id: String): Call<List<String>>

    //Platos

    @GET("platos/tipos/{id}")
    fun getTiposPlatosByBar(@Path("id") id: String): Call<List<String>>

    @GET("platos/tipo/{tipo}/bar/{id}")
    fun getPlatosByBarAndTipo(@Path("tipo") tipo: String, @Path("id") id: String): Call<List<Plato>>

    @GET("platos/{id}")
    fun getPlato(@Path("id") id: String): Call<PlatoDto>


    //Pedidos

    @POST("pedidos/actualizar-carrito/{cantidad}/{id}")
    fun actualizarCarrito(@Path("cantidad") cantidad:Int, @Path("id") id: String): Call<LineaPedidoDto>

    @DELETE("pedidos/borrar-plato/{id}")
    fun borrarPlato(@Path("id") id: String): Call<Boolean>

    @GET("pedidos/calcular/total-carrito")
    fun calcularPrcioTotal(): Call<Double>

    @GET("pedidos/ver-carrito")
    fun getCarrito(): Call<List<LineaPedidoDto>>

    @POST("pedidos/pagar")
    fun pagar(@Body createPedido: CreatePedido): Call<PedidoDto>

    @GET("pedidos/ver/mis-pedidos")
    fun misPedidos(): Call<List<PedidoDto>>

    @GET("pedidos/lineas/{id}")
    fun lineasPedidoByPedidoId(@Path("id") id: String): Call<List<LineaPedidoDetalle>>

    @GET("pedidos/{id}")
    fun getPedidoDetalle(@Path("id") id: String): Call<PedidoDetalleDto>

    @PUT("pedidos/{id}")
    fun editPedidoBoolean(@Path("id") id: String): Call<Boolean>
}