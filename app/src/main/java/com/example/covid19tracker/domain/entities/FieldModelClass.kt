package com.example.covid19tracker.domain.entities

import com.google.gson.annotations.SerializedName

data class FieldModelClass(
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("alias") val alias: String,
    @SerializedName("sqlType") val sqlType: String,
    @SerializedName("domain") val domain: String?,
    @SerializedName("defaultValue") val defaultValue: String?
)