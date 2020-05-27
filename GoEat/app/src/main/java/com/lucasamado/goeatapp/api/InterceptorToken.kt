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
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4OTdjZTYzMy1kNWNhLTQ3OGMtYjJkZS1hNTRjMjIzNDVmY2MiLCJleHAiOjE1OTE0ODA2OTAsImlhdCI6MTU5MDYxNjY5MCwidXNlcm5hbWUiOiJ1c2VyIiwicm9sZXMiOiJVU0VSIn0.quot9x7InCaEP_m-IwsBitSBeCk-bnNnz2PDoyb-yy7y6SYDnB5TYFvafLu941slh5L_FYaQg3hM7zKqJEP2QQ"

        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer "+token)
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}