package com.example.covid19tracker.domain.entities

import com.google.gson.annotations.SerializedName

data class UniqueId(
    @SerializedName("name") val name: String,
    @SerializedName("isSystemMaintained") val isSystemMaintained: Boolean
)