package com.example.covid19tracker.domain.entities

import com.google.gson.annotations.SerializedName

/** Data class Attribute has been put under the same file with a common name.
 * The reason is this file encapsulates the similar properties/key-values */

data class Feature(
    @SerializedName("attributes") val attributes: Attribute
)

data class Attribute(
    @SerializedName("CumCase") val totalConfirmedCases: Long,
    @SerializedName("CumDeath") val totalDeathCases: Long,
    @SerializedName("OBJECTID") val objectId: Int
)