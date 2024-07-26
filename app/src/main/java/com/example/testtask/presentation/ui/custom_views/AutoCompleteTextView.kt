package com.example.testtask.presentation.ui.custom_views

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextView(
    modifier: Modifier,
    enterText: String,
    placeholder: String,
    onEnterTextChanged: (String) -> Unit,
    hintsList: List<String>,
    onItemClick: (String) -> Unit,
    onClearClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    val currentValue = enterText.ifEmpty { selectedOptionText }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(value = currentValue, onValueChange = {
            onEnterTextChanged(it)
            expanded = hintsList.isNotEmpty()
        },
            modifier = modifier.menuAnchor(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear",
                    modifier = Modifier.clickable {
                        onClearClick()
                        selectedOptionText = ""
                    }
                )
            },
            label = { Text(text = placeholder) })

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            hintsList.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = selectionOption
                        )
                    },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onItemClick(selectedOptionText)
                    }
                )
            }
        }
    }
}