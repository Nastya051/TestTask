package com.example.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UiParams(
    @SerialName("activities")
    val activities: List<Activity>
)

@Serializable
data class Activity(
    @SerialName("activity")
    val activity: String,
    @SerialName("layout")
    val layout: Layout
)

@Serializable
data class Layout(
    @SerialName("header")
    val header: String,
    @SerialName("form")
    val form: Form
)

@Serializable
data class Form(
    @SerialName("text")
    val text: List<Text>,
    @SerialName("buttons")
    val buttons: List<Button>
)

@Serializable
data class Button(
    @SerialName("type")
    val type: String,
    @SerialName("caption")
    val caption: String,
    @SerialName("formAction")
    val formAction: String
)

@Serializable
data class Text(
    @SerialName("type")
    val type: String,
    @SerialName("caption")
    val caption: String,
    @SerialName("attribute")
    val attribute: String,
    @SerialName("required")
    val required: Boolean,
    @SerialName("suggestions")
    val suggestions: List<String>? = null
)