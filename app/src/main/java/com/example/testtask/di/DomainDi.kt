package com.example.testtask.di

import com.example.domain.usecases.impl.GetEmployeeInfoUseCaseImpl
import com.example.domain.usecases.interfaces.GetUiParamsUseCase
import com.example.domain.usecases.impl.GetUiParamsUseCaseImpl
import com.example.domain.usecases.interfaces.GetEmployeeInfoUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetUiParamsUseCase>{
        GetUiParamsUseCaseImpl(paramsRepository = get())
    }
    factory<GetEmployeeInfoUseCase>{
        GetEmployeeInfoUseCaseImpl(paramsRepository = get())
    }
}