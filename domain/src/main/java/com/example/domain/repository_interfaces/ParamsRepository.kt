package com.example.domain.repository_interfaces

import com.example.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface ParamsRepository {

    fun getUiParams(): Flow<Result>

    fun getEmployeeInfo(requestTrail: String): Flow<Result>
}