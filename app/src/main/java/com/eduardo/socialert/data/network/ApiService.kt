package com.eduardo.socialert.data.network

import com.eduardo.socialert.data.model.request.LoginRequest
import com.eduardo.socialert.data.model.request.UserRequest
import com.eduardo.socialert.data.model.response.LoginResponse
import com.eduardo.socialert.data.model.response.LogoutResponse
import com.eduardo.socialert.data.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RegisterApiService {
    @POST("store-citizen")
    suspend fun registerUser(
        @Body user : UserRequest
    ) : Response<RegisterResponse>

    @POST("login-citizen")
    suspend fun loginUser(@Body loginRequest : LoginRequest) : Response<LoginResponse>

    @POST("logout-citizen")
    suspend fun logoutUser(@Header("Authorization") authHeader: String) : Response<LogoutResponse>
}
