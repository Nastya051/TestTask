package com.example.domain.usecases.impl

import com.example.domain.models.Result
import com.example.domain.repository_interfaces.ParamsRepository
import com.example.domain.usecases.interfaces.GetEmployeeInfoUseCase
import kotlinx.coroutines.flow.Flow

class GetEmployeeInfoUseCaseImpl(private val paramsRepository: ParamsRepository) :
    GetEmployeeInfoUseCase {
    override fun execute(requestTrail: String): Flow<Result> {
        return paramsRepository.getEmployeeInfo(requestTrail = requestTrail)
    }
}