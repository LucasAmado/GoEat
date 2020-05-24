package com.lucasamado.goeatapp.api

import android.content.SharedPreferences
import com.lucasamado.goeatapp.common.Constantes
import com.lucasamado.goeatapp.common.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class InterceptorToken @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val request: Request
        //val token = SharedPreferencesManager().getSomeStringValue(Constantes.TOKEN)
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzNTI5MzUyZi0xYzJhLTQ4NTAtYTM5YS02NjViYjU0MjFjNGMiLCJleHAiOjE1OTEyMTEzNzQsImlhdCI6MTU5MDM0NzM3NCwidXNlcm5hbWUiOiJnb2lrbyIsInJvbGVzIjoiQURNSU4ifQ.rCvHgN0CGA3dV-zLundt6A_7X50pn7uf3754OiCaWDu2IP8abNsATVNht2Ynvq5C8kYaeUgqk-qUIFP_8uhR-Q"

        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer "+token)
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}