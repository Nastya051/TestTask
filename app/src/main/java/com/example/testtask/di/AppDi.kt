package com.example.testtask.di

import com.example.testtask.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModel<MainViewModel> {
        MainViewModel(getUiParamsUseCase = get(), getEmployeeInfoUseCase = get())
    }
}