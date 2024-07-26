package com.example.domain.usecases.impl

import com.example.domain.models.Result
import com.example.domain.repository_interfaces.ParamsRepository
import com.example.domain.usecases.interfaces.GetUiParamsUseCase
import kotlinx.coroutines.flow.Flow

class GetUiParamsUseCaseImpl(private val paramsRepository: ParamsRepository) : GetUiParamsUseCase {
    override fun execute(): Flow<Result> {
        return paramsRepository.getUiParams()
    }
}