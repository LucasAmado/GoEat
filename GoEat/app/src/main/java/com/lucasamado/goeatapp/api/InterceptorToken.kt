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
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4NzE4ZTZiOC05MGUyLTRkNzUtOGZhNC00NjY3MDhjNTU3Y2IiLCJleHAiOjE1OTExNDQwNDgsImlhdCI6MTU5MDI4MDA0OCwidXNlcm5hbWUiOiJnb2lrbyIsInJvbGVzIjoiQURNSU4ifQ.fNPHdXolYc-iNJ_z_bn4ta1-Ca2GEDwW7U1qlAHf8ph1Ja6X77RQQ72iuMKHAc7Lm0rni3aH267N2C06i3l1Aw"


        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer "+token)
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}