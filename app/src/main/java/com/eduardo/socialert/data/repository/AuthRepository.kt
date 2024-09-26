package com.eduardo.socialert.data.repository

import com.eduardo.socialert.data.network.ApiService
import com.eduardo.socialert.data.model.request.LoginRequest
import com.eduardo.socialert.data.model.response.LoginResponse
import com.eduardo.socialert.data.model.response.LogoutResponse
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {
    suspend fun loginUser(email : String, password : String) : Response<LoginResponse>{
        val request = LoginRequest(email, password)
        return apiService.loginUser(request)
    }

    suspend fun logoutUser(token : String) : Response<LogoutResponse>{
        return apiService.logoutUser("Bearer $token")
    }
}