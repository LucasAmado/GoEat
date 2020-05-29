package com.lucasamado.goeatapp.api

import com.lucasamado.goeatapp.models.bar.BarDetailDto
import com.lucasamado.goeatapp.models.bar.BarDto
import com.lucasamado.goeatapp.models.pedido.LineaPedidoDto
import com.lucasamado.goeatapp.models.plato.Plato
import com.lucasamado.goeatapp.models.plato.PlatoDto
import com.lucasamado.goeatapp.models.user.LoginRequest
import com.lucasamado.goeatapp.models.user.LoginResponse
import com.lucasamado.goeatapp.models.user.SignupRequest
import com.lucasamado.goeatapp.models.user.SignupResponse
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalTime

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

    //Platos

    @GET("platos/tipos/{id}")
    fun getTiposPlatosByBar(@Path("id") id: String): Call<List<String>>

    @GET("platos/tipo/{tipo}/bar/{id}")
    fun getPlatosByBarAndTipo(@Path("tipo") tipo: String, @Path("id") id: String): Call<List<Plato>>

    @GET("platos/{id}")
    fun getPlato(@Path("id") id: String): Call<PlatoDto>


    //Carrito

    @POST("pedidos/actualizar-carrito/{cantidad}/{id}")
    fun actualizarCarrito(@Path("cantidad") cantidad:Int, @Path("id") id: String): Call<LineaPedidoDto>

    @DELETE("pedidos/borrar-plato/{id}")
    fun borrarPlato(@Path("id") id: String): Call<Boolean>

    @GET("pedidos/calcular/total-carrito")
    fun consultarTamanyoCarrito(): Call<Double>

    @GET("pedidos/ver-carrito")
    fun getCarrito(): Call<List<LineaPedidoDto>>

    @GET("bares/consultar/horarios-recogida/{id}")
    fun consultarHorariosRecogidaBar(@Path("id") id: String): Call<List<String>>
}