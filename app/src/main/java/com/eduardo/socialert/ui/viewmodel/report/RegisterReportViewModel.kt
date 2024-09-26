package com.eduardo.socialert.ui.viewmodel.report

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduardo.socialert.data.model.ReportDetails
import com.eduardo.socialert.data.network.RetrofitClient
import com.eduardo.socialert.data.repository.reports.ReportRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.http.Multipart
import java.io.File

class RegisterReportViewModel : ViewModel() {
    private val repository: ReportRepository = ReportRepository(RetrofitClient.apiRegister)

    private val _reportsState = mutableStateOf<List<ReportDetails>>(emptyList())
    val reportsState: State<List<ReportDetails>> = _reportsState

    var message = mutableStateOf("")

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    val isLoading = mutableStateOf(false)

    fun registerReport(
        context: Context,
        type_id: Int,
        description: String? = "",
        file: Uri?,
        latitude: Double?,
        longitude: Double?,
        alert : Int
    ) {
        viewModelScope.launch {
            isLoading.value = true
            val sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
            val token = sharedPref.getString("auth_token", null)

            token?.let {
                try {
                    _errorMessage.value = ""
                    message.value = ""
                    val typeIdPart =
                        type_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val descriptionPart =
                        description?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val latitudePart =
                        latitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val longitudePart =
                        longitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val alertPart = alert.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                    val filePart = file?.let { uri -> createFilePart(uri, context) }

                    val response = repository.registerReport(
                        it,
                        typeIdPart,
                        descriptionPart,
                        filePart,
                        latitudePart,
                        longitudePart,
                        alertPart
                    )
                    if (response.isSuccessful) {
                        _reportsState.value = response.body()?.reports ?: emptyList()
                        message.value = response.body()?.message ?: ""
                        println(message.value)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val errorMsg = extractErrorMessage(errorBody)
                        _errorMessage.value = "Error: $errorMsg"
                        println(_errorMessage.value)
                    }

                } catch (e: Exception) {
                    _errorMessage.value = "Error en el registro : ${e.message}"
                } finally {
                    isLoading.value = false
                }
            }
        }
    }

    private fun createFilePart(uri: Uri, context: Context): MultipartBody.Part? {
        val contentResolver = context.contentResolver
        val file = File(uri.path ?: "")

        val requestFile = contentResolver.openInputStream(uri)?.use {
            val byteArray = it.readBytes()
            byteArray.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        }

        return requestFile?.let { MultipartBody.Part.createFormData("file", file.name, it) }
    }

    private fun extractErrorMessage(errorBody: String?): String? {
        return try {
            errorBody?.let {
                val jsonObject = JSONObject(it)
                jsonObject.getString("message")
            }
        } catch (e: Exception) {
            "Error al procesar la respuesta del servidor"
        }
    }
}
