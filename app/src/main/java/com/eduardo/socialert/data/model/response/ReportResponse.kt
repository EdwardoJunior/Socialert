package com.eduardo.socialert.data.model.response

import com.eduardo.socialert.data.model.ReportDetails

data class ReportResponse(
    val status : Int,
    val message : String,
    val reports : List<ReportDetails>
)
