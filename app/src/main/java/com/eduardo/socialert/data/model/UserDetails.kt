package com.eduardo.socialert.data.model

data class UserDetails(
    val user_id : Int,
    val citizen_id : Int,
    val name : String,
    val lastname : String,
    val phoneNumber : String,
    val curp : String,
    val email : String
)
