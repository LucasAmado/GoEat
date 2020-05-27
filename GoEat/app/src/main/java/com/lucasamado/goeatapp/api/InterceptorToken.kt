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
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzYTg1OGJlZi04Y2VkLTRjYzItOGY2OS01YTdkYzRhZmYyMjgiLCJleHAiOjE1OTE0NzQ1NTMsImlhdCI6MTU5MDYxMDU1MywidXNlcm5hbWUiOiJ1c2VyIiwicm9sZXMiOiJVU0VSIn0.ePWiyGEU33LpVKJM_ma56V8vrQBoX231q8EqU38ksnfvAJCxb8-ZnwK43OP90bDG7Dzwj9QVPnIFSkqqvbOISg"

        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer "+token)
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}