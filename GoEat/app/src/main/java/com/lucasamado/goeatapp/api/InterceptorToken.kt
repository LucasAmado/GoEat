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
        val token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkODU5MTMxMC1mY2QzLTRjMDUtODFjMi1mODMyOTVmZjRlMTQiLCJleHAiOjE1OTE0MDIyMDksImlhdCI6MTU5MDUzODIwOSwidXNlcm5hbWUiOiJ1c2VyIiwicm9sZXMiOiJVU0VSIn0.0mDJ1WOGrgUIpz9x7ebgxLgNJuH40O0cSAY9g4GDqfMwxnIctXv-9DcJpORc4OwE5Lcxwmik17fKR3Wl9nc3mg"

        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer "+token)
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}