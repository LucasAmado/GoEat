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
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMzAyM2JlNS04YmU5LTRjNWUtODBhMy0zOWUzMjBhMjUxY2EiLCJleHAiOjE1OTE3Mjc0MzMsImlhdCI6MTU5MDg2MzQzMywidXNlcm5hbWUiOiJnb2lrbyIsInJvbGVzIjoiQURNSU4ifQ.BGvNvo3aAPc6xJGbwlKZnf0HfFkma-y1OVMXj_BfpXEBB4wME566ljL1xeTsS8Tst_Jx7pFb_qAy4pfmUtzzTg"

        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer "+token)
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}