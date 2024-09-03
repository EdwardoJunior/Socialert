package com.eduardo.socialert.data.model.request

data class UserRequest(
    val email : String,
    val password : String,
    val name : String,
    val lastname : String,
    val phoneNumber : String,
    val curp : String,

)