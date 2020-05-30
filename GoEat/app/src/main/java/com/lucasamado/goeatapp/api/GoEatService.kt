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
import retrofit2.Response
import retrofit2.http.*

interface GoEatService {

    //usuarios

    @POST("/signup")
    suspend fun createUser(@Body newUser: SignupRequest): Response<SignupResponse>

    @POST("auth/login")
    suspend fun doLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>

    //Bares

    @GET("bares/")
    suspend fun getBaresList(): Response<List<BarDto>>

    @GET("bares/{id}")
    suspend fun getBarById(@Path("id") id: String): Response<BarDetailDto>

    @GET("bares/consultar/horarios-recogida/{id}")
    suspend fun consultarHorariosRecogidaBar(@Path("id") id: String): Response<List<String>>

    //Platos

    @GET("platos/tipos/{id}")
    suspend fun getTiposPlatosByBar(@Path("id") id: String): Response<List<String>>

    @GET("platos/tipo/{tipo}/bar/{id}")
    suspend fun getPlatosByBarAndTipo(@Path("tipo") tipo: String, @Path("id") id: String): Response<List<Plato>>

    @GET("platos/{id}")
    suspend fun getPlato(@Path("id") id: String): Response<PlatoDto>


    //Pedidos

    @POST("pedidos/actualizar-carrito/{cantidad}/{id}")
    suspend fun actualizarCarrito(@Path("cantidad") cantidad:Int, @Path("id") id: String): Response<LineaPedidoDto>

    @DELETE("pedidos/borrar-plato/{id}")
    fun borrarPlato(@Path("id") id: String): Call<Boolean>

    @GET("pedidos/calcular/total-carrito")
    suspend fun calcularPrcioTotal(): Response<Double>

    @GET("pedidos/ver-carrito")
    suspend fun getCarrito(): Response<List<LineaPedidoDto>>

    @POST("pedidos/pagar")
    suspend fun pagar(@Body createPedido: CreatePedido): Response<PedidoDto>

    @GET("pedidos/ver/mis-pedidos")
    suspend fun misPedidos(): Response<List<PedidoDto>>

    @GET("pedidos/lineas/{id}")
    suspend fun lineasPedidoByPedidoId(@Path("id") id: String): Response<List<LineaPedidoDetalle>>

    @GET("pedidos/{id}")
    suspend fun getPedidoDetalle(@Path("id") id: String): Response<PedidoDetalleDto>

    @PUT("pedidos/{id}")
    suspend fun editPedidoBoolean(@Path("id") id: String): Response<Boolean>
}