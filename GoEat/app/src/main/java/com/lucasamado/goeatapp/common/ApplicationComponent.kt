package com.lucasamado.goeatapp.common

import com.lucasamado.goeatapp.api.NetworkModule
import com.lucasamado.goeatapp.ui.LoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component ( modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(loginActivity: LoginActivity)
}