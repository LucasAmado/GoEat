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
        //TODO cambiar val token = SharedPreferencesManager().getSharedPreferences().getString(Constantes.TOKEN, "")
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYzRjZDljNy0zOTJhLTQyMmYtOTdiZS03NDEwNTE4MDllNTIiLCJleHAiOjE1OTExMjEwODQsImlhdCI6MTU5MDI1NzA4NCwidXNlcm5hbWUiOiJ1c2VyIiwicm9sZXMiOiJVU0VSIn0.C988Z-m5WnnoxQMNO3fJBHAoSxUzAmiXjPcuVnBCdsQv-sdKKkAdWds50uS9ksVaZVgWAzETIZ0g_TvKVDlf7A"

        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer "+token)
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}