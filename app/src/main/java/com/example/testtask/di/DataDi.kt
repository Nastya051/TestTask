package com.example.testtask.di

import com.example.data.client.httpClientAndroid
import com.example.data.repository_impl.ParamsRepositoryImpl
import com.example.data.services.ApiServiceParams
import com.example.data.services.ApiServiceParamsImpl
import com.example.domain.repository_interfaces.ParamsRepository
import org.koin.dsl.module
import io.ktor.client.HttpClient

val dataModule = module {
    single<ApiServiceParams>{
        ApiServiceParamsImpl(client = get())
    }
    single<ParamsRepository>{
        ParamsRepositoryImpl(apiService = get())
    }
    single {
        provideHttpClient()
    }
}

fun provideHttpClient(): HttpClient {
    return httpClientAndroid
}