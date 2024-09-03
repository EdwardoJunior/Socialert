package com.eduardo.socialert.data.model.response

import com.eduardo.socialert.data.model.UserDetails

data class LoginResponse(
    val message : String,
    val access_token : String?,
    val token_type : String?,
    val user : UserDetails?
)
