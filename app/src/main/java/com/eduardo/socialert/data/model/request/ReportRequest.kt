package com.eduardo.socialert.data.model.request

data class ReportRequest(
    val type_id : Int,
    val description : String?,
    val file : String?,
    val latitude : Double,
    val longitude : Double
)
