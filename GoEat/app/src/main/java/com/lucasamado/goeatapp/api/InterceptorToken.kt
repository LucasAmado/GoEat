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
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1NjVhYmZhYS1hMmE0LTRjYjktYmM3Mi04ZDlkNzVmMGQ1YmQiLCJleHAiOjE1OTE2MTk1MTEsImlhdCI6MTU5MDc1NTUxMSwidXNlcm5hbWUiOiJ1c2VyIiwicm9sZXMiOiJVU0VSIn0.hPqeAFOo9Wuw9yCqh5FOTPEfMe-NpfJm7uiP3_cQgAQCPDzJv9aPchVXqsWUq5QFyEyvdwIDXy-f_vCla-zSOQ"

        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer "+token)
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}