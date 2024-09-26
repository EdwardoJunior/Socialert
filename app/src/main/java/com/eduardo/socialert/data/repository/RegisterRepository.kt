package com.eduardo.socialert.data.repository

import com.eduardo.socialert.data.network.ApiService
import com.eduardo.socialert.data.model.request.UserRequest
import com.eduardo.socialert.data.model.response.RegisterResponse

class RegisterRepository (
    private val registerApiService: ApiService
){
    suspend fun registerUser(userRequest: UserRequest) : RegisterResponse {
        val response = registerApiService.registerUser(userRequest)
        if(response.isSuccessful){
            return response.body() ?: throw Exception("Error al obtener la respuesta")
        }else{
            throw Exception("Error en la solicitud ${response.message()}")
        }
    }
}