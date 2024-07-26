package com.example.data.repository_impl

import com.example.data.services.ApiServiceParams
import com.example.domain.models.EmployeeInfo
import com.example.domain.models.Result
import com.example.domain.models.UiParams
import com.example.domain.repository_interfaces.ParamsRepository
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException

class ParamsRepositoryImpl(private val apiService: ApiServiceParams) : ParamsRepository {
    override fun getUiParams(): Flow<Result> {
        return flow {
            val response = apiService.getUiParams()
            try {
                when (response.status.value) {
                    200 -> {
                        try {
                            emit(Result.Success(value = response.body<UiParams>()))
                        } catch (e: SerializationException) {
                            emit(Result.Error(value = e.message!!))
                        }
                    }

                    in 300..399 -> emit(Result.Error(value = "Redirect Response Exception"))
                    in 400..499 -> emit(Result.Error(value = "Client Request Exception"))
                    in 500..599 -> emit(Result.Error(value = "Server Response Exception"))

                    else -> emit(Result.Error(value = "Response Exception"))
                }
            } catch (e: Exception) {
                emit(Result.Error(value = "Json Convert Exception"))
            } catch (cause: Throwable) {
                emit(Result.Error(value = "Throwable"))
            }
        }
    }

    override fun getEmployeeInfo(requestTrail: String): Flow<Result> {
        return flow {
            val response = apiService.getEmployeeInfo(requestTrail = requestTrail)
            try {
                when (response.status.value) {
                    200 -> {
                        try {
                            emit(Result.Success(value = response.body<EmployeeInfo>()))
                        } catch (e: SerializationException) {
                            emit(Result.Error(value = e.message!!))
                        }
                    }

                    in 300..399 -> emit(Result.Error(value = "Redirect Response Exception"))
                    in 400..499 -> emit(Result.Error(value = "Client Request Exception"))
                    in 500..599 -> emit(Result.Error(value = "Server Response Exception"))

                    else -> emit(Result.Error(value = "Response Exception"))
                }
            } catch (e: Exception) {
                emit(Result.Error(value = "Json Convert Exception"))
            } catch (cause: Throwable) {
                emit(Result.Error(value = "Throwable"))
            }
        }
    }
}