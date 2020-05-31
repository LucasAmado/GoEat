package com.lucasamado.goeatapp.api

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import androidx.navigation.Navigator
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.MyApp
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named("url")
    fun provideBaseUrl() = Constantes.API_BASE_URL

    @Singleton
    @Provides
    fun createClient(@Named("url") baseUrl: String): GoEatService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).addInterceptor(InterceptorToken()).build()
            )
            .build()
            .create(GoEatService::class.java)
    }

    @Singleton
    @Provides
    fun createClientUser(@Named("url") baseUrl: String): GoEatUser {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .build()
            .create(GoEatUser::class.java)
    }
}