package com.example.data.services

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface ApiServiceParams {
    suspend fun getUiParams(): HttpResponse
    suspend fun getEmployeeInfo(requestTrail: String): HttpResponse
}

class ApiServiceParamsImpl(private val client: HttpClient) : ApiServiceParams {
    private val PARAMS =
        "vth2k/7f41b5d6d3b78838d35a9b504d21f2f0/raw/3e7f17e2cea043ad5d4179da7b884163c34f51d1/test.json"
    private val INFO =
        "vth2k/7f41b5d6d3b78838d35a9b504d21f2f0/raw/3e7f17e2cea043ad5d4179da7b884163c34f51d1"

    override suspend fun getUiParams(): HttpResponse {
        return client.get(PARAMS) {
            contentType(ContentType.Text.Html)
        }
    }

    override suspend fun getEmployeeInfo(requestTrail: String): HttpResponse {
        return client.get(INFO + requestTrail) {
            contentType(ContentType.Text.Html)
        }
    }
}