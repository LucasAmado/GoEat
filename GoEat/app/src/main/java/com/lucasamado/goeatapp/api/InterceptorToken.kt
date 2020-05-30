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
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NTc5ZWZiOC0zZWE5LTRhYTgtYTY5Zi1kY2ZkZThjY2Y1MTkiLCJleHAiOjE1OTE3MjE0NjcsImlhdCI6MTU5MDg1NzQ2NywidXNlcm5hbWUiOiJ1c2VyIiwicm9sZXMiOiJVU0VSIn0.--HsTJYT4EF6ld6uqdhT79XwAt1YWvHm6ETfK3FbKttVvsQQ6MNcI674yuRvno7omnKjOWJOmlr8xoeHnPwwqQ"

        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer "+token)
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}