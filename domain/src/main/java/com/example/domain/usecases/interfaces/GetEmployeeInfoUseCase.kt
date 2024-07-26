package com.example.domain.usecases.interfaces

import com.example.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface GetEmployeeInfoUseCase {
    fun execute(requestTrail: String): Flow<Result>
}