package com.example.testtask.presentation.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.EmployeeInfo
import com.example.domain.models.UiParams
import com.example.testtask.R
import com.example.testtask.presentation.enums.TextType
import com.example.testtask.presentation.ui.custom_views.AutoCompleteTextView
import com.example.testtask.presentation.ui.custom_views.HeaderRow
import com.example.testtask.viewmodels.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = getViewModel()
    val uiParameters by viewModel.uiParams.collectAsState()
    val employeeInfo by viewModel.employeeInfo.collectAsState()
    val numScreen by viewModel.numberResponse.collectAsState()
    val context = LocalContext.current
    val showError by viewModel.showError.collectAsState()
    val errorText by viewModel.errorText.collectAsState()

    Scaffold(topBar = {
        when (numScreen) {
            1 -> HeaderRow()
            2 -> HeaderRow(
                isBackButton = true,
                onClickBackButton = { viewModel.updateNumberResponse(1) })
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (numScreen) {
                1 -> FirstResponse(uiParams = uiParameters, viewModel = viewModel)
                2 -> SecondResponse(employeeInfo = employeeInfo)
            }
        }
    }

    LaunchedEffect(showError) {
        if (showError) {
            Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
            viewModel.updateShowError(false)
        }
    }
}

@Composable
fun FirstResponse(uiParams: UiParams, viewModel: MainViewModel) {
    val activityData = uiParams.activities[0]
    val textValue by viewModel.textValue.collectAsState()
    val autoTextValue by viewModel.autoTextValue.collectAsState()
    val showHint by viewModel.showHint.collectAsState()
    val hints = mutableListOf<String>()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(top = 30.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = activityData.layout.header,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        for (text in activityData.layout.form.text) {
            when (text.type) {
                TextType.PlainText.value -> {
                    TextField(
                        value = textValue,
                        onValueChange = { newText -> viewModel.updateTextValue(newText) },
                        label = { Text(text = text.caption) },
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }

                TextType.AutoCompleteText.value -> {
                    text.suggestions?.let {
                        for (item in text.suggestions!!) {
                            if (item.contains(autoTextValue)) {
                                hints.add(item)
                            }
                        }
                        AutoCompleteTextView(
                            modifier = Modifier.padding(bottom = 24.dp),
                            enterText = autoTextValue,
                            placeholder = text.caption,
                            onEnterTextChanged = { text -> viewModel.updateAutoTextValue(text) },
                            hintsList = hints,
                            onItemClick = { item -> viewModel.updateAutoTextValue(item) },
                            onClearClick = { viewModel.updateAutoTextValue("") }
                        )
                    }
                }
            }
        }
        for (button in activityData.layout.form.buttons) {
            Button(onClick = {
                if (textValue.isNotEmpty() && autoTextValue.isNotEmpty()) {
                    viewModel.updateShowHint(false)
                    viewModel.getEmployeeInfo(button.formAction)
                } else
                    viewModel.updateShowHint(true)
            }) {
                Text(text = button.caption)
            }
        }
        AnimatedVisibility(visible = showHint) {
            Text(text = stringResource(id = R.string.FillInAllFields), color = Color.Red)
        }
        LaunchedEffect(textValue, autoTextValue) {
            if (textValue.isNotEmpty() && autoTextValue.isNotEmpty())
                viewModel.updateShowHint(false)
        }
    }
}

@Composable
fun SecondResponse(employeeInfo: EmployeeInfo) {
    val user = employeeInfo.data.user
    val error = employeeInfo.error
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(top = 30.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.fullName, user.fullName),
                maxLines = 2
            )
            Text(text = stringResource(id = R.string.position, user.position))
            Text(text = stringResource(id = R.string.workedOutHours, user.workedOutHours))
            Text(text = stringResource(id = R.string.workHoursInMonth, user.workHoursInMonth))
            if (error.isError) {
                Text(text = stringResource(id = R.string.error))
                Text(text = stringResource(id = R.string.errorDescription, error.description))
            }
        }
    }
}