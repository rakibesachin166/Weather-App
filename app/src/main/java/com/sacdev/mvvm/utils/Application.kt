package com.sacdev.mvvm.utils

import android.app.Application
import com.sacdev.mvvm.api.RetrofitClient
import com.sacdev.mvvm.repository.WeatherRepository
import com.sacdev.mvvm.viewModels.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class Application : Application() {
    init {
        System.loadLibrary("native-lib")
    }
    private external fun getApiKey(): String

    private val appModule = module {
        single { RetrofitClient.instance }
        single { WeatherRepository(get(), getApiKey()) }
        single { SharedPrefManager(androidContext()) }

        viewModel { WeatherViewModel(get() ) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(appModule)
        }
    }
}