package com.eduardo.socialert.data.network

import com.eduardo.socialert.data.network.request.UserRequest
import com.eduardo.socialert.data.network.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("citizen/store")
    suspend fun registerUser(
        @Body user : UserRequest
    ) : Response<RegisterResponse>
}