package com.eduardo.socialert.data.network

import com.eduardo.socialert.data.model.request.LoginRequest
import com.eduardo.socialert.data.model.request.ReportRequest
import com.eduardo.socialert.data.model.request.UserRequest
import com.eduardo.socialert.data.model.response.LoginResponse
import com.eduardo.socialert.data.model.response.LogoutResponse
import com.eduardo.socialert.data.model.response.RegisterResponse
import com.eduardo.socialert.data.model.response.ReportResponse
import com.eduardo.socialert.data.model.response.ReportsTypeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("store-citizen")
    suspend fun registerUser(
        @Body user: UserRequest
    ): Response<RegisterResponse>

    @POST("login-citizen")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("logout-citizen")
    suspend fun logoutUser(@Header("Authorization") authHeader: String): Response<LogoutResponse>

    //Reports
    @GET("index-reports")
    suspend fun getAllReports(@Header("Authorization") authHeader: String): Response<ReportResponse>

    @GET("create-report")
    suspend fun getAllReportsType(@Header("Authorization") authHeader: String): Response<ReportsTypeResponse>

    @Multipart
    @POST("store-report")
    suspend fun registerReport(
        @Header("Authorization") authHeader: String,
        @Part("type_id") type_id: RequestBody,
        @Part("description") description: RequestBody?,
        @Part file: MultipartBody.Part?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?,
        @Part("alert") alert: RequestBody?
    ): Response<ReportResponse>
}
