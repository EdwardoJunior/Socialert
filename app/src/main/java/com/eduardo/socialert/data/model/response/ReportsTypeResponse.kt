package com.eduardo.socialert.data.model.response

import com.eduardo.socialert.data.model.ReportsTypeDetails

data class ReportsTypeResponse(
    val status : Int,
    val message : String,
    val reports : List<ReportsTypeDetails>
)
