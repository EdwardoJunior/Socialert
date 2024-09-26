package com.eduardo.socialert.data.model.request

data class UserRequest(
    val email : String,
    val password : String,
    val name : String,
    val lastname : String,
    val phone : String,
    val curp : String,
    val gender : String

)