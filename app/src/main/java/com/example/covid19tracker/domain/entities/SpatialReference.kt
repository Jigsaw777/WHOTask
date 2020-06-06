package com.example.covid19tracker.domain.entities

import com.google.gson.annotations.SerializedName

data class SpatialReference(
    @SerializedName("wkid") val id: Int,
    @SerializedName("latestWkid") val latestId: Int
)