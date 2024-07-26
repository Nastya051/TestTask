package com.example.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeInfo(
    @SerialName("data")
    val data: Data,
    @SerialName("error")
    val error: Error
)

@Serializable
data class Data(
    @SerialName("user")
    val user: User
)

@Serializable
data class User(
    @SerialName("fullName")
    val fullName: String,
    @SerialName("position")
    val position: String,
    @SerialName("workHoursInMonth")
    val workHoursInMonth: Long,
    @SerialName("workedOutHours")
    val workedOutHours: Long
)

@Serializable
data class Error(
    @SerialName("description")
    val description: String,
    @SerialName("isError")
    val isError: Boolean
)
