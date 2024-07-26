package com.example.testtask.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Data
import com.example.domain.models.EmployeeInfo
import com.example.domain.models.Error
import com.example.domain.models.Result
import com.example.domain.models.UiParams
import com.example.domain.models.User
import com.example.domain.usecases.interfaces.GetEmployeeInfoUseCase
import com.example.domain.usecases.interfaces.GetUiParamsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUiParamsUseCase: GetUiParamsUseCase,
    private val getEmployeeInfoUseCase: GetEmployeeInfoUseCase
) : ViewModel() {

    init {
        getUiParams()
    }

    private val _textValue = MutableStateFlow("")
    val textValue: StateFlow<String> = _textValue.asStateFlow()
    fun updateTextValue(value: String) {
        _textValue.update { value }
    }

    private val _showHint = MutableStateFlow(false)
    val showHint: StateFlow<Boolean> = _showHint.asStateFlow()
    fun updateShowHint(value: Boolean) {
        _showHint.update { value }
    }

    private val _autoTextValue = MutableStateFlow("")
    val autoTextValue: StateFlow<String> = _autoTextValue.asStateFlow()
    fun updateAutoTextValue(value: String) {
        _autoTextValue.update { value }
    }

    private var _numberResponse = MutableStateFlow(0)
    val numberResponse: StateFlow<Int> = _numberResponse.asStateFlow()
    fun updateNumberResponse(value: Int) {
        _numberResponse.update { value }
    }

    private var _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError.asStateFlow()
    fun updateShowError(value: Boolean) {
        _showError.update { value }
    }

    private var _errorText = MutableStateFlow("")
    val errorText: StateFlow<String> = _errorText.asStateFlow()

    private val _uiParams = MutableStateFlow(UiParams(activities = listOf()))
    val uiParams: StateFlow<UiParams> = _uiParams.asStateFlow()

    private val _employeeInfo = MutableStateFlow(
        EmployeeInfo(
            data = Data(
                user = User(
                    fullName = "",
                    position = "",
                    workHoursInMonth = 0,
                    workedOutHours = 0
                )
            ),
            error = Error(description = "", isError = false)
        )
    )
    val employeeInfo: StateFlow<EmployeeInfo> = _employeeInfo.asStateFlow()

    fun getUiParams() {
        val res = getUiParamsUseCase.execute()
        viewModelScope.launch {
            res.collect {
                when (it) {
                    is Result.Success<*> -> {
                        val saveIt = it.copy()
                        val answer = saveIt.value as UiParams
                        _uiParams.value = answer
                        _numberResponse.update { 1 }
                    }

                    is Result.Error -> {
                        val saveIt = it.copy()
                        val answer = saveIt.value
                        _errorText.value = answer
                        _showError.value = true
                    }
                }
            }
        }
    }

    fun getEmployeeInfo(requestTrail: String) {
        val res = getEmployeeInfoUseCase.execute(requestTrail = requestTrail)
        viewModelScope.launch {
            res.collect {
                when (it) {
                    is Result.Success<*> -> {
                        val saveIt = it.copy()
                        val answer = saveIt.value as EmployeeInfo
                        _employeeInfo.value = answer
                        _numberResponse.update { 2 }
                    }

                    is Result.Error -> {
                        val saveIt = it.copy()
                        val answer = saveIt.value
                        _errorText.value = answer
                        _showError.value = true
                    }
                }
            }
        }
    }
}