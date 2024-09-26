package com.eduardo.socialert.data.repository.reports

import com.eduardo.socialert.data.model.response.ReportResponse
import com.eduardo.socialert.data.model.response.ReportsTypeResponse
import com.eduardo.socialert.data.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart

class ReportRepository(private val apiService: ApiService) {

    suspend fun getAllReports(token: String): Response<ReportResponse> {
        return apiService.getAllReports("Bearer $token")
    }

    suspend fun getAllReportsType(token: String): Response<ReportsTypeResponse> {
        return apiService.getAllReportsType("Bearer $token")
    }

    suspend fun registerReport(
        token: String,
        type_id: RequestBody,
        description: RequestBody?,
        file: MultipartBody.Part?,
        latitude: RequestBody?,
        longitude: RequestBody?,
        alert: RequestBody?
    ): Response<ReportResponse> {
        return apiService.registerReport(
            "Bearer $token",
            type_id,
            description,
            file,
            latitude,
            longitude,
            alert
        )
    }
}