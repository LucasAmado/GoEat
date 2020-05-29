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
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTNjYjc3ZC05OTQyLTQ0MzEtOTdlOC05YjkxOWM5YzIzYTciLCJleHAiOjE1OTE2NDk1MjEsImlhdCI6MTU5MDc4NTUyMSwidXNlcm5hbWUiOiJ1c2VyIiwicm9sZXMiOiJVU0VSIn0.OOL5rrWwQxChqYURBpy7KzopWlzzLqp_LKAowroApUw1Sc6BKzsEl4Yb90l3ioLjYf383XNLBBFmtswaHLo6Eg"

        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer "+token)
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}