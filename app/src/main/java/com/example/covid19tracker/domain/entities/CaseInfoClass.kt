package com.example.covid19tracker.domain.entities

import com.google.gson.annotations.SerializedName

data class CaseInfoClass(
    @SerializedName("objectIdFieldName") val objectId: String,
    @SerializedName("uniqueIdField") val uniqueId: UniqueId,
    @SerializedName("globalIdFieldName") val globalId: String?,
    @SerializedName("geometryType") val geometryType: String,
    @SerializedName("spatialReference") val spatialRef: SpatialReference,
    @SerializedName("fields") val fields: List<FieldModelClass>,
    @SerializedName("exceededTransferLimit") val transferLimit: Boolean,
    @SerializedName("features") val features: List<Feature>
)